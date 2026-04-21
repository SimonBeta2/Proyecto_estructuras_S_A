package com.example.demo.controllers;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping( path = "/{id}")
    public Optional<UsuarioModel> obtenerUsuarioPorId(@PathVariable("id")Integer id){
        return this.usuarioService.obtenerPorId(id);
    }
    // Crear
    @PostMapping()
    public UsuarioModel crearUsuario(@RequestBody UsuarioModel usuario){
        return this.usuarioService.crearUsuario(usuario);
    }

    // Actualizar
    @PutMapping("/{id}")
    public UsuarioModel actualizarUsuario(@PathVariable Integer id, @RequestBody UsuarioModel usuario) {
        return usuarioService.actualizarUsuario(id, usuario);
    }

    @DeleteMapping(path = "/{id}")
    public String eliminarPorId(@PathVariable("id")Integer id){
        boolean delOk = this.usuarioService.eliminarUsuario(id);
        if(delOk){
            return "Se eliminó con exito el usuario con id" + id;
        }else{
            return "No se pudo eliminar al usuario con id" + id;
        }
    }

}