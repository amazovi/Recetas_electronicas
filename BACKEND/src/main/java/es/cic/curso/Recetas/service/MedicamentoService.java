package es.cic.curso.Recetas.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.cic.curso.Recetas.dto.MedicamentoDTO;
import es.cic.curso.Recetas.model.Medicamento;
import es.cic.curso.Recetas.repository.MedicamentoRepository;

@Service
public class MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    public List<Medicamento> findByTipoId(Long tipoId) {
        return medicamentoRepository.findByTipoId(tipoId);
    }

    public List<MedicamentoDTO> findAll() {
        return medicamentoRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    private MedicamentoDTO convertToDTO(Medicamento medicamento) {
        MedicamentoDTO dto = new MedicamentoDTO();
        dto.setId(medicamento.getId()); 
        dto.setNombre(medicamento.getNombre());
        dto.setDescripcion(medicamento.getDescripcion());
        dto.setDosisRecomendada(medicamento.getDosisRecomendada());
        dto.setPrecio(medicamento.getPrecio());
        dto.setTipoNombre(medicamento.getTipo() != null ? medicamento.getTipo().getNombre() : "Sin tipo");
        return dto;
    }

    public Optional<Medicamento> findById(Long id) {
        return medicamentoRepository.findById(id);
    }

    public Medicamento save(Medicamento medicamento) {
        return medicamentoRepository.save(medicamento);
    }

    public void deleteById(Long id) {
        medicamentoRepository.deleteById(id);
    }
}