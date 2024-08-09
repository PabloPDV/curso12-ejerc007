package es.cic.curso.ejerc007.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cic.curso.ejerc007.model.Coche;
import es.cic.curso.ejerc007.service.CocheService;

@RestController
@RequestMapping("/coches")
public class CocheController {

    @Autowired
    private CocheService cocheService;

    @GetMapping
    public List<Coche> getAllCoches() {
        return cocheService.findAll();
    }

    @GetMapping("/{id}")
    public Coche getCocheById(@PathVariable Long id) {
        return cocheService.findById(id).orElse(null);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Coche> createCoche(@RequestBody Coche coche) {
        Coche savedCoche = cocheService.save(coche);
        return ResponseEntity.ok(savedCoche);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Coche> updateCoche(@PathVariable Long id, @RequestBody Coche coche) {
        coche.setId(id);
        Coche updatedCoche = cocheService.save(coche);
        return ResponseEntity.ok(updatedCoche);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteCoche(@PathVariable Long id) {
        cocheService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}