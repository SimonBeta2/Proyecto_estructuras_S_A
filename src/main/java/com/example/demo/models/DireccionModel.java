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

    @Column(name = "street", length = 50)
    private String street;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "label", length = 150)
    private String label;

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


    public DireccionModel(String street, String city, String label) {
        this.street = street;
        this.city = city;
        this.label = label;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getLabel() {
        return label;
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
