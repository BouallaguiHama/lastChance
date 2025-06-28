package com.stockapp.service;

import com.stockapp.dto.AchatDto;
import com.stockapp.entity.Achat;
import com.stockapp.entity.BonDAchat;
import com.stockapp.entity.Client;
import com.stockapp.entity.Fournisseur;
import com.stockapp.entity.Produit;
import com.stockapp.mapper.AchatMapper;
import com.stockapp.repository.AchatRepository;
import com.stockapp.repository.ClientRepository;
import com.stockapp.repository.FournisseurRepository;
import com.stockapp.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AchatService {

    @Autowired
    private AchatRepository achatRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Autowired
    private AchatMapper achatMapper;

    @Autowired
    private ProduitService produitService;

    public List<AchatDto> getAllAchats() {
        return achatRepository.findAll()
                .stream()
                .map(achatMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<AchatDto> getAchatById(Long id) {
        return achatRepository.findById(id)
                .map(achatMapper::toDto);
    }

    public AchatDto createAchat(AchatDto achatDto) {
        // Vérifier que le produit et le fournisseur existent
        Optional<Produit> produit = produitRepository.findById(achatDto.getProduitId());
        Optional<Fournisseur> fournisseur = fournisseurRepository.findById(achatDto.getFournisseurId());

        if (produit.isEmpty()) {
            throw new RuntimeException("Produit non trouvé avec l'ID: " + achatDto.getProduitId());
        }
        if (fournisseur.isEmpty()) {
            throw new RuntimeException("Fournisseur non trouvé avec l'ID: " + achatDto.getFournisseurId());
        }

        Achat achat = achatMapper.toEntity(achatDto);
        achat.setProduit(produit.get());
        achat.setFournisseur(fournisseur.get());

        // Créer le bon d'achat
        BonDAchat bonDAchat = new BonDAchat(achat);
        achat.setBonDAchat(bonDAchat);

        Achat savedAchat = achatRepository.save(achat);

        // Mettre à jour le stock
        produitService.updateStock(achatDto.getProduitId(), achatDto.getQuantite(), true);

        return achatMapper.toDto(savedAchat);
    }

    public List<AchatDto> getAchatsByProduit(Long produitId) {
        return achatRepository.findByProduitId(produitId)
                .stream()
                .map(achatMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<AchatDto> getAchatsByFournisseur(Long fournisseurId) {
        return achatRepository.findByFournisseurId(fournisseurId)
                .stream()
                .map(achatMapper::toDto)
                .collect(Collectors.toList());
    }
}