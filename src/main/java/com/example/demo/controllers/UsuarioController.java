package com.example.demo.controllers;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.UsuarioModel;
import com.example.demo.services.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController{
    @Autowired
    UsuarioService usuarioService;

    // Listar
    @GetMapping()
    public ArrayList<UsuarioModel> obtenerUsuarios(){
        return usuarioService.obtenerUsuarios();
    }
    // Crear
    @PostMapping()
    public UsuarioModel crearUsuario(@RequestBody UsuarioModel usuario){
        return this.usuarioService.crearUsuario(usuario);
    }

    // Actualizar
    @PutMapping("/{id}")
    public UsuarioModel actualizarUsuario(@PathVariable Integer id,
                                          @RequestBody UsuarioModel usuario) {
        return usuarioService.actualizarUsuario(id, usuario);
    }
}