package com.stockapp.controller;

import com.stockapp.dto.VenteDto;
import com.stockapp.service.VenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventes")
@CrossOrigin(origins = "*")
public class VenteController {

    @Autowired
    private VenteService venteService;

    @GetMapping
    public ResponseEntity<List<VenteDto>> getAllVentes() {
        List<VenteDto> ventes = venteService.getAllVentes();
        return ResponseEntity.ok(ventes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenteDto> getVenteById(@PathVariable Long id) {
        return venteService.getVenteById(id)
                .map(vente -> ResponseEntity.ok(vente))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VenteDto> createVente(@Valid @RequestBody VenteDto venteDto) {
        try {
            VenteDto createdVente = venteService.createVente(venteDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdVente);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/produit/{produitId}")
    public ResponseEntity<List<VenteDto>> getVentesByProduit(@PathVariable Long produitId) {
        List<VenteDto> ventes = venteService.getVentesByProduit(produitId);
        return ResponseEntity.ok(ventes);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<VenteDto>> getVentesByClient(@PathVariable Long clientId) {
        List<VenteDto> ventes = venteService.getVentesByClient(clientId);
        return ResponseEntity.ok(ventes);
    }
}