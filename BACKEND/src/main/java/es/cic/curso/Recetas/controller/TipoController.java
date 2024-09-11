package es.cic.curso.Recetas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import es.cic.curso.Recetas.model.Tipo;
import es.cic.curso.Recetas.repository.TipoRepository;
import es.cic.curso.Recetas.service.TipoService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/tipos")
@Validated
public class TipoController {
 
    @Autowired
    private TipoRepository tipoRepository;

    @Autowired
    private TipoService tipoService;

    @GetMapping
    public List<Tipo> getAllTipos() {
        return tipoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tipo> getTipoById(@PathVariable Long id) {
        Optional<Tipo> tipo = tipoRepository.findById(id);
        return tipo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tipo> createTipo(@Valid @RequestBody Tipo tipo) {
        try {
            Tipo createdTipo = tipoService.save(tipo);
            return ResponseEntity.ok(createdTipo);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Tipo> updateTipo(@PathVariable Long id, @Valid @RequestBody Tipo tipo) {
        if (!tipoRepository.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        try {
            tipo.setId(id);
            Tipo updatedTipo = tipoService.save(tipo);
            return ResponseEntity.ok(updatedTipo);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipo(@PathVariable Long id) {
        if (!tipoRepository.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        tipoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}