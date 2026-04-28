package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.OfertaModel;
import com.example.demo.services.OfertaService;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaController {

    @Autowired
    private OfertaService ofertaService;

    // PUBLICO: Ver todas las ofertas
    @GetMapping
    public List<OfertaModel> listar() {
        return ofertaService.listarTodas();
    }

    // PUBLICO: Ver ofertas por categoría (Tu método mágico)
    @GetMapping("/servicio/{servicioId}")
    public List<OfertaModel> buscarPorServicio(@PathVariable Integer servicioId) {
        return ofertaService.buscarPorServicio(servicioId);
    }

    // PRIVADO (Requiere Token): Crear una oferta
    @PostMapping
    public OfertaModel crear(@RequestBody OfertaModel oferta) {
        return ofertaService.guardarOferta(oferta);
    }

    // --- 2. ACTUALIZAR OFERTA (PUT /ofertas/{id}) ---
    @PutMapping("/{id}")
    public ResponseEntity<OfertaModel> actualizarOferta(@PathVariable Integer id, @RequestBody OfertaModel oferta) {
        try {
            OfertaModel ofertaActualizada = ofertaService.actualizarOferta(id, oferta);
            return ResponseEntity.ok(ofertaActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Devuelve un error 404 si no encuentra la oferta
        }
    }

    // --- 3. ELIMINAR OFERTA (DELETE /ofertas/{id}) ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOferta(@PathVariable Integer id) {
        try {
            ofertaService.eliminarOferta(id);
            return ResponseEntity.noContent().build(); // Devuelve un 204 No Content indicando éxito al borrar
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<OfertaModel>> obtenerOfertasPorUsuario(@PathVariable Integer userId) {
        List<OfertaModel> ofertas = ofertaService.listarOfertasPorUsuario(userId);
        return ResponseEntity.ok(ofertas);
    }
}