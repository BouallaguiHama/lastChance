package com.stockapp.service;

import com.stockapp.dto.ProduitDto;
import com.stockapp.entity.Produit;
import com.stockapp.mapper.ProduitMapper;
import com.stockapp.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private ProduitMapper produitMapper;

    public List<ProduitDto> getAllProduits() {
        return produitRepository.findAll()
                .stream()
                .map(produitMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ProduitDto> getProduitById(Long id) {
        return produitRepository.findById(id)
                .map(produitMapper::toDto);
    }

    public ProduitDto createProduit(ProduitDto produitDto) {
        Produit produit = produitMapper.toEntity(produitDto);
        Produit savedProduit = produitRepository.save(produit);
        return produitMapper.toDto(savedProduit);
    }

    public Optional<ProduitDto> updateProduit(Long id, ProduitDto produitDto) {
        return produitRepository.findById(id)
                .map(existingProduit -> {
                    produitDto.setId(id);
                    Produit updatedProduit = produitMapper.toEntity(produitDto);
                    updatedProduit.setDateCreation(existingProduit.getDateCreation());
                    return produitMapper.toDto(produitRepository.save(updatedProduit));
                });
    }

    public boolean deleteProduit(Long id) {
        if (produitRepository.existsById(id)) {
            produitRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<ProduitDto> searchProduits(String nom) {
        return produitRepository.findByNomContainingIgnoreCase(nom)
                .stream()
                .map(produitMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ProduitDto> getProduitsAvecStockFaible() {
        return produitRepository.findProduitsAvecStockFaible()
                .stream()
                .map(produitMapper::toDto)
                .collect(Collectors.toList());
    }

    public void updateStock(Long produitId, Integer quantite, boolean isAchat) {
        Optional<Produit> produitOpt = produitRepository.findById(produitId);
        if (produitOpt.isPresent()) {
            Produit produit = produitOpt.get();
            if (isAchat) {
                produit.setQuantiteStock(produit.getQuantiteStock() + quantite);
            } else {
                // Vente - vérifier qu'il y a assez de stock
                if (produit.getQuantiteStock() >= quantite) {
                    produit.setQuantiteStock(produit.getQuantiteStock() - quantite);
                } else {
                    throw new RuntimeException("Stock insuffisant pour le produit: " + produit.getNom());
                }
            }
            produitRepository.save(produit);
        }
    }
}