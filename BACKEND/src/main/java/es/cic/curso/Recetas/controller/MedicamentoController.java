package es.cic.curso.Recetas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import es.cic.curso.Recetas.dto.MedicamentoDTO;
import es.cic.curso.Recetas.model.Medicamento;
import es.cic.curso.Recetas.service.MedicamentoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/medicamentos")
@Validated
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    @GetMapping
    public List<MedicamentoDTO> getAllMedicamentos() {
        return medicamentoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicamento> getMedicamentoById(@PathVariable Long id) {
        Optional<Medicamento> medicamento = medicamentoService.findById(id);
        return medicamento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Medicamento> createMedicamento(@Valid @RequestBody Medicamento medicamento) {
        try {
            Medicamento nuevoMedicamento = medicamentoService.save(medicamento);
            return new ResponseEntity<>(nuevoMedicamento, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicamento> updateMedicamento(@PathVariable Long id, @Valid @RequestBody Medicamento medicamento) {
        Optional<Medicamento> medicamentoExistente = medicamentoService.findById(id);
        if (!medicamentoExistente.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        medicamento.setId(id);
        try {
            Medicamento updatedMedicamento = medicamentoService.save(medicamento);
            return ResponseEntity.ok(updatedMedicamento);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicamento(@PathVariable Long id) {
        if (!medicamentoService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        medicamentoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}