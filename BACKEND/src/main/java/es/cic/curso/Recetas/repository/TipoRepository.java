package es.cic.curso.Recetas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import es.cic.curso.Recetas.model.Tipo;

public interface TipoRepository extends JpaRepository<Tipo, Long> {
}