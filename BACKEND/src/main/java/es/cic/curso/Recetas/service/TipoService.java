package es.cic.curso.Recetas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.cic.curso.Recetas.model.Tipo;
import es.cic.curso.Recetas.model.Medicamento;
import es.cic.curso.Recetas.repository.TipoRepository;
import es.cic.curso.Recetas.repository.MedicamentoRepository;

@Service
public class TipoService {

    @Autowired
    private TipoRepository tipoRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    public List<Tipo> findAll() {
        return tipoRepository.findAll();
    }

    public Optional<Tipo> findById(Long id) {
        return tipoRepository.findById(id);
    }

    public Tipo save(Tipo tipo) {
        return tipoRepository.save(tipo);
    }

    public void deleteById(Long id) {
        tipoRepository.deleteById(id);
    }

    public void deleteByNombre(String nombre) {
        Optional<Tipo> tipo = tipoRepository.findByNombre(nombre);
        if (tipo.isPresent()) {
            Long tipoId = tipo.get().getId();
            List<Medicamento> medicamentos = medicamentoRepository.findByTipoId(tipoId);
            for (Medicamento medicamento : medicamentos) {
                medicamento.setTipo(null);
                medicamentoRepository.save(medicamento);
            }
            tipoRepository.deleteById(tipoId);
        }
    }
}