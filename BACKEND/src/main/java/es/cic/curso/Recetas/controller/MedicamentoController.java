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
import es.cic.curso.Recetas.model.Tipo;
import es.cic.curso.Recetas.service.MedicamentoService;
import es.cic.curso.Recetas.service.TipoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/medicamentos")
@Validated
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private TipoService tipoService;

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
    public ResponseEntity<Medicamento> createMedicamento(@Valid @RequestBody MedicamentoDTO medicamentoDTO) {
            try {
                Tipo tipo = tipoService.findById(medicamentoDTO.getTipoId()).orElse(null);
                Medicamento medicamento = new Medicamento();
                medicamento.setNombre(medicamentoDTO.getNombre());
                medicamento.setDescripcion(medicamentoDTO.getDescripcion());
                medicamento.setDosisRecomendada(medicamentoDTO.getDosisRecomendada());
                medicamento.setPrecio(medicamentoDTO.getPrecio());
                medicamento.setTipo(tipo);
                Medicamento nuevoMedicamento = medicamentoService.save(medicamento);
                return new ResponseEntity<>(nuevoMedicamento, HttpStatus.CREATED);
            } catch (Exception e) {
                e.printStackTrace(); // Esto puede ayudar a depurar problemas en el servidor
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicamento> updateMedicamento(@PathVariable Long id,
            @Valid @RequestBody MedicamentoDTO medicamentoDTO) {
        Optional<Medicamento> medicamentoExistente = medicamentoService.findById(id);
        if (!medicamentoExistente.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        try {
            Tipo tipo = tipoService.findById(medicamentoDTO.getTipoId()).orElse(null);
            Medicamento medicamento = medicamentoExistente.get();
            // Mapear otros campos de MedicamentoDTO a Medicamento
            medicamento.setTipo(tipo);
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