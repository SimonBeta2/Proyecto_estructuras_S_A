package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.OfertaModel;

@Repository
public interface OfertaRepository extends JpaRepository<OfertaModel, Integer> {
    
    // El "método mágico": Spring generará: SELECT * FROM ofertas WHERE servicio_id = ?
    List<OfertaModel> findByServicioId(Integer servicioId);
    
    // Extra: Si quisieras buscar todas las ofertas de un usuario específico
    List<OfertaModel> findByUsuarioId(Integer usuarioId);
    
}
