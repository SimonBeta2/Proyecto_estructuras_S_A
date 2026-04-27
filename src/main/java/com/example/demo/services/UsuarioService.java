package com.example.demo.services;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.UsuarioDTO;
import com.example.demo.models.DireccionModel;
import com.example.demo.models.UsuarioModel;
import com.example.demo.repositories.UsuarioRepository;

@Service
public class UsuarioService{
    @Autowired
    UsuarioRepository usuarioRepository;
    
    public ArrayList<UsuarioModel> obtenerUsuarios(){
        return (ArrayList<UsuarioModel>) usuarioRepository.findAll();
    }

    public UsuarioModel crearUsuario(UsuarioModel usuario) {
        return usuarioRepository.save(usuario);
}
    
    public Optional<UsuarioModel> buscarPorEmail(String email){
        return usuarioRepository.findByEmail(email);
}
    public UsuarioModel actualizarUsuario(Integer id, UsuarioDTO datos) {
    // 1. Buscamos el usuario (hace 1 solo viaje a la base de datos)
    UsuarioModel usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    // 2. Actualizamos los datos básicos
    usuario.setNombre(datos.getName());
    usuario.setEmail(datos.getEmail());
    usuario.setTelefono(datos.getPhone());

    // 3. Actualizamos las direcciones (si es que el Frontend las envió)
    if (datos.getAddress() != null) {
        // Limpiamos la lista actual (esto activa el orphanRemoval para borrar las viejas)
        usuario.getDirecciones().clear();
        
        // Recorremos las direcciones que vienen en el JSON
        for (DireccionModel dir : datos.getAddress()) {
            // Es VITAL decirle a la dirección quién es su dueño
            dir.setUsuario(usuario); 
            // Y luego la metemos a la lista del usuario
            usuario.getDirecciones().add(dir);
        }
    }

    // 4. Guardamos. Hibernate se encarga de los UPDATE del usuario 
    // y de los INSERT/DELETE de las direcciones automáticamente.
    return usuarioRepository.save(usuario);
}

    public UsuarioModel obtenerPorId(Integer id) {
    return usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
}

    public void eliminarUsuario(Integer id){
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado");
    }
        usuarioRepository.deleteById(id);
    }

    public UsuarioModel findOrCreateUser(String email, String name, String googleId,String pictureUrl) {
        return usuarioRepository.findByEmail(email)
                .orElseGet(() -> {
                    UsuarioModel newUser = new UsuarioModel();
                    newUser.setEmail(email);
                    newUser.setNombre(name);
                    newUser.setGoogleId(googleId);
                    newUser.setPicture(pictureUrl);
                    return usuarioRepository.save(newUser);
                });
    }
}