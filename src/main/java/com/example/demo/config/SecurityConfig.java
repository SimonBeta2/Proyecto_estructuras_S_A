package com.example.demo.config;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.example.demo.seguridad.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final OAuth2AuthenticationSuccessHandler successHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    public SecurityConfig(OAuth2AuthenticationSuccessHandler successHandler,
                          JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.successHandler = successHandler;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // 1. EL FILTRO CORS GLOBAL
    // Al ser un @Bean de tipo CorsFilter, Spring lo ejecuta ANTES de la seguridad

    @Bean
    public FilterRegistrationBean<CorsFilter> customCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
    
        config.setAllowCredentials(true);
    // Usamos split por si tienes varios, pero Railway solo tiene uno
        config.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept", "Origin", "X-Requested-With"));
        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);
    
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        // ESTA LÍNEA ES LA CLAVE: Pone el filtro por delante de Spring Security
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE); 
        return bean;
}


    // 2. LA CADENA DE SEGURIDAD
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // ELIMINAMOS .cors() DE AQUÍ PARA QUE NO CHOQUE CON EL FILTRO DE ARRIBA
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                
                // Permitir siempre el Preflight de CORS
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // Temporal para prueba: Permitir PUT directo
                .requestMatchers(HttpMethod.PUT, "/usuario/**").permitAll()

                // Rutas públicas
                .requestMatchers("/api/servicios/**", "/api/productos/**", "/api/ofertas/**").permitAll()
                .requestMatchers("/oauth2/**", "/login/**", "/api/auth/**").permitAll()
                
                // Todo lo demás privado
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                .successHandler(successHandler)
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}