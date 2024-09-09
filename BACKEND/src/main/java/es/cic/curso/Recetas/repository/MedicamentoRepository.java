package es.cic.curso.Recetas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.cic.curso.Recetas.model.Medicamento;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {
}