package es.cic.curso.ejerc007.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import es.cic.curso.ejerc007.model.Coche;

@Service
public class CocheService {
    private List<Coche> coches = new ArrayList<>();
    private Long nextId = 1L;

    public List<Coche> findAll() {
        return coches;
    }

    public Optional<Coche> findById(Long id) {
        return coches.stream().filter(coche -> coche.getId().equals(id)).findFirst();
    }

    public Coche save(Coche coche) {
        coche.setId(nextId++);
        coches.add(coche);
        return coche;
    }

    public Optional<Coche> update(Long id, Coche coche) {
        return findById(id).map(existingCoche -> {
            existingCoche.setMarca(coche.getMarca());
            existingCoche.setModelo(coche.getModelo());
            existingCoche.setAño(coche.getAño());
            return existingCoche;
        });
    }

    public boolean deleteById(Long id) {
        return coches.removeIf(coche -> coche.getId().equals(id));
    }
}