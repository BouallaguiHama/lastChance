package com.stockapp.controller;

import com.stockapp.dto.FournisseurDto;
import com.stockapp.entity.Fournisseur;
import com.stockapp.mapper.FournisseurMapper;
import com.stockapp.repository.FournisseurRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/fournisseurs")
@CrossOrigin(origins = "*")
public class FournisseurController {

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private FournisseurMapper fournisseurMapper;

    @GetMapping
    public ResponseEntity<List<FournisseurDto>> getAllFournisseurs() {
        List<FournisseurDto> fournisseurs = fournisseurRepository.findAll()
                .stream()
                .map(fournisseurMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(fournisseurs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FournisseurDto> getFournisseurById(@PathVariable Long id) {
        return fournisseurRepository.findById(id)
                .map(fournisseur -> ResponseEntity.ok(fournisseurMapper.toDto(fournisseur)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FournisseurDto> createFournisseur(@Valid @RequestBody FournisseurDto fournisseurDto) {
        Fournisseur fournisseur = fournisseurMapper.toEntity(fournisseurDto);
        Fournisseur savedFournisseur = fournisseurRepository.save(fournisseur);
        return ResponseEntity.status(HttpStatus.CREATED).body(fournisseurMapper.toDto(savedFournisseur));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FournisseurDto> updateFournisseur(@PathVariable Long id, 
                                                            @Valid @RequestBody FournisseurDto fournisseurDto) {
        return fournisseurRepository.findById(id)
                .map(existingFournisseur -> {
                    fournisseurDto.setId(id);
                    Fournisseur updatedFournisseur = fournisseurMapper.toEntity(fournisseurDto);
                    updatedFournisseur.setDateCreation(existingFournisseur.getDateCreation());
                    Fournisseur savedFournisseur = fournisseurRepository.save(updatedFournisseur);
                    return ResponseEntity.ok(fournisseurMapper.toDto(savedFournisseur));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFournisseur(@PathVariable Long id) {
        if (fournisseurRepository.existsById(id)) {
            fournisseurRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<FournisseurDto>> searchFournisseurs(@RequestParam String nom) {
        List<FournisseurDto> fournisseurs = fournisseurRepository.findByNomContainingIgnoreCase(nom)
                .stream()
                .map(fournisseurMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(fournisseurs);
    }
}