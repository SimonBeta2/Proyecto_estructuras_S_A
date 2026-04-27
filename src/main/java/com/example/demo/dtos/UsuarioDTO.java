package com.example.demo.dtos;

import java.util.List;

import com.example.demo.models.DireccionModel;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UsuarioDTO {
    private Integer id;
    private String email;
    private String name;
    private String phone;
    private String pictureUrl;
    private List<DireccionModel> address;
    
    public UsuarioDTO(Integer id, String email, String name, String phone, String pictureUrl, List<DireccionModel> address) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.phone = phone;
    this.pictureUrl = pictureUrl;
    this.address = address;
}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public List<DireccionModel> getAddress() {
        return address;
    }

    public void setAddress(List<DireccionModel> address) {
        this.address = address;
    }
}