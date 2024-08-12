package es.cic.curso.ejerc007.repository;

import es.cic.curso.ejerc007.model.Coche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CocheRepository extends JpaRepository<Coche, Long> {
}