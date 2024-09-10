package es.cic.curso.Recetas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Tipo createdTipo = tipoRepository.save(tipo);
        return ResponseEntity.ok(createdTipo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tipo> updateTipo(@PathVariable Long id, @Valid @RequestBody Tipo tipo) {
        if (!tipoRepository.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        tipo.setId(id);
        Tipo updatedTipo = tipoRepository.save(tipo);
        return ResponseEntity.ok(updatedTipo);
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