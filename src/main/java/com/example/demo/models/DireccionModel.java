package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "direcciones")
public class DireccionModel {

    @Column(length = 50)
    private String nombre;

    @Column(length = 50)
    private String ciudad;

    @Column(length = 150)
    private String comentarios;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @JsonIgnore // ¡Muy importante! (Te explico abajo por qué)
    private UsuarioModel usuario;

    public DireccionModel() {
    }


    public DireccionModel(String nombre, String ciudad, String comentarios) {
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.comentarios = comentarios;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getComentarios() {
        return comentarios;
    }

    public Integer getId() {
        return id;
    }

    public UsuarioModel getUsuario() {
        return usuario;
}

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
}
}
