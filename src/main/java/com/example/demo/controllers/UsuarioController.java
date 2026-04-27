package com.example.demo.controllers;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.UsuarioModel;
import com.example.demo.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "https://domu-services.vercel.app")
public class UsuarioController{
    @Autowired
    UsuarioService usuarioService;

    // Listar
    @GetMapping()
    public ArrayList<UsuarioModel> obtenerUsuarios(){
        return usuarioService.obtenerUsuarios();
    }

    @GetMapping("/{id}")
    public UsuarioModel obtenerUsuarioPorId(@PathVariable Integer id){
        return this.usuarioService.obtenerPorId(id);
    }
    // Crear
    @PostMapping()
    public UsuarioModel crearUsuario(@Valid @RequestBody UsuarioModel usuario){
        return this.usuarioService.crearUsuario(usuario);
    }

    // Actualizar
    @PutMapping("/{id}")
    public UsuarioModel actualizarUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioModel usuario) {
        return usuarioService.actualizarUsuario(id, usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPorId(@PathVariable Integer id){
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }

}