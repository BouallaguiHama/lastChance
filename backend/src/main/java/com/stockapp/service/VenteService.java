package com.stockapp.service;

import com.stockapp.dto.VenteDto;
import com.stockapp.entity.*;
import com.stockapp.mapper.VenteMapper;
import com.stockapp.repository.ClientRepository;
import com.stockapp.repository.ProduitRepository;
import com.stockapp.repository.VenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class VenteService {

    @Autowired
    private VenteRepository venteRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private VenteMapper venteMapper;

    @Autowired
    private ProduitService produitService;

    public List<VenteDto> getAllVentes() {
        return venteRepository.findAll()
                .stream()
                .map(venteMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<VenteDto> getVenteById(Long id) {
        return venteRepository.findById(id)
                .map(venteMapper::toDto);
    }

    public VenteDto createVente(VenteDto venteDto) {
        // Vérifier que le produit et le client existent
        Optional<Produit> produit = produitRepository.findById(venteDto.getProduitId());
        Optional<Client> client = clientRepository.findById(venteDto.getClientId());

        if (produit.isEmpty()) {
            throw new RuntimeException("Produit non trouvé avec l'ID: " + venteDto.getProduitId());
        }
        if (client.isEmpty()) {
            throw new RuntimeException("Client non trouvé avec l'ID: " + venteDto.getClientId());
        }

        // Vérifier le stock disponible
        Produit produitEntity = produit.get();
        if (produitEntity.getQuantiteStock() < venteDto.getQuantite()) {
            throw new RuntimeException("Stock insuffisant. Stock disponible: " + produitEntity.getQuantiteStock());
        }

        Vente vente = venteMapper.toEntity(venteDto);
        vente.setProduit(produitEntity);
        vente.setClient(client.get());

        // Créer la facture et le bon de livraison
        Facture facture = new Facture(vente);
        BonDeLivraison bonDeLivraison = new BonDeLivraison(vente);
        
        vente.setFacture(facture);
        vente.setBonDeLivraison(bonDeLivraison);

        Vente savedVente = venteRepository.save(vente);

        // Mettre à jour le stock
        produitService.updateStock(venteDto.getProduitId(), venteDto.getQuantite(), false);

        return venteMapper.toDto(savedVente);
    }

    public List<VenteDto> getVentesByProduit(Long produitId) {
        return venteRepository.findByProduitId(produitId)
                .stream()
                .map(venteMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<VenteDto> getVentesByClient(Long clientId) {
        return venteRepository.findByClientId(clientId)
                .stream()
                .map(venteMapper::toDto)
                .collect(Collectors.toList());
    }
}