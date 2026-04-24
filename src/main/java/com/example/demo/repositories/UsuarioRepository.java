package com.example.demo.repositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.UsuarioModel;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioModel, Integer>{
    public Optional<UsuarioModel> findByEmail(String email);
    public Optional<UsuarioModel> findByGoogleId(String googleId);
}
