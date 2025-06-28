package com.stockapp.controller;

import com.stockapp.dto.ProduitDto;
import com.stockapp.service.ProduitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
@CrossOrigin(origins = "*")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @GetMapping
    public ResponseEntity<List<ProduitDto>> getAllProduits() {
        List<ProduitDto> produits = produitService.getAllProduits();
        return ResponseEntity.ok(produits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduitDto> getProduitById(@PathVariable Long id) {
        return produitService.getProduitById(id)
                .map(produit -> ResponseEntity.ok(produit))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProduitDto> createProduit(@Valid @RequestBody ProduitDto produitDto) {
        ProduitDto createdProduit = produitService.createProduit(produitDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduit);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProduitDto> updateProduit(@PathVariable Long id, 
                                                    @Valid @RequestBody ProduitDto produitDto) {
        return produitService.updateProduit(id, produitDto)
                .map(produit -> ResponseEntity.ok(produit))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        if (produitService.deleteProduit(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProduitDto>> searchProduits(@RequestParam String nom) {
        List<ProduitDto> produits = produitService.searchProduits(nom);
        return ResponseEntity.ok(produits);
    }

    @GetMapping("/stock-faible")
    public ResponseEntity<List<ProduitDto>> getProduitsAvecStockFaible() {
        List<ProduitDto> produits = produitService.getProduitsAvecStockFaible();
        return ResponseEntity.ok(produits);
    }
}