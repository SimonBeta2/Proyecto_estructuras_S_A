package com.example.demo.controllers;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.dtos.LoginRequest;
import com.example.demo.dtos.LoginResponse;
import com.example.demo.models.UsuarioModel;
import com.example.demo.seguridad.JwtService;
import com.example.demo.services.AuthService;
import com.example.demo.services.UsuarioService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody UsuarioModel usuario){

        UsuarioModel user = usuarioService.buscarPorEmail(usuario.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if(!passwordEncoder.matches(usuario.getPassword(), user.getPassword())){
            throw new RuntimeException("Contraseña incorrecta");
        }

        String token = jwtService.generarToken(user.getEmail());

        return Map.of("token", token);
    }
}