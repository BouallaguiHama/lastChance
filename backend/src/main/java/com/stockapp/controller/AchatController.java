package com.stockapp.controller;

import com.stockapp.dto.AchatDto;
import com.stockapp.service.AchatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/achats")
@CrossOrigin(origins = "*")
public class AchatController {

    @Autowired
    private AchatService achatService;

    @GetMapping
    public ResponseEntity<List<AchatDto>> getAllAchats() {
        List<AchatDto> achats = achatService.getAllAchats();
        return ResponseEntity.ok(achats);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AchatDto> getAchatById(@PathVariable Long id) {
        return achatService.getAchatById(id)
                .map(achat -> ResponseEntity.ok(achat))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AchatDto> createAchat(@Valid @RequestBody AchatDto achatDto) {
        try {
            AchatDto createdAchat = achatService.createAchat(achatDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAchat);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/produit/{produitId}")
    public ResponseEntity<List<AchatDto>> getAchatsByProduit(@PathVariable Long produitId) {
        List<AchatDto> achats = achatService.getAchatsByProduit(produitId);
        return ResponseEntity.ok(achats);
    }

    @GetMapping("/fournisseur/{fournisseurId}")
    public ResponseEntity<List<AchatDto>> getAchatsByFournisseur(@PathVariable Long fournisseurId) {
        List<AchatDto> achats = achatService.getAchatsByFournisseur(fournisseurId);
        return ResponseEntity.ok(achats);
    }
}