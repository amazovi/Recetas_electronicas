package es.cic.curso.Recetas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.*;
import org.springframework.web.bind.annotation.*;

import es.cic.curso.Recetas.model.Medicamento;
import es.cic.curso.Recetas.model.Tipo;
import es.cic.curso.Recetas.repository.TipoRepository;
import es.cic.curso.Recetas.service.MedicamentoService;
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

    @Autowired
    private MedicamentoService medicamentoService;

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
    public ResponseEntity<String> deleteTipo(@PathVariable Long id) {
        // Buscar medicamentos asociados a este tipo
        List<Medicamento> medicamentosAsociados = medicamentoService.findByTipoId(id);
        
        if (!medicamentosAsociados.isEmpty()) {
            // Actualizar los medicamentos asociados, estableciendo su tipo_id a NULL
            for (Medicamento medicamento : medicamentosAsociados) {
                medicamento.setTipo(null);  // Asignar null al tipo (esto pone el tipo_id en NULL)
                medicamentoService.save(medicamento);  // Guardar los cambios
            }
        }
        
        // Eliminar el tipo ahora que los medicamentos no tienen el tipo asociado
        tipoService.deleteById(id);
        
        return ResponseEntity.noContent().build();
    }

}