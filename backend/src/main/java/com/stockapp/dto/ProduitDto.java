package com.stockapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProduitDto {
    private Long id;
    
    @NotBlank
    private String nom;
    
    private String description;
    
    @NotNull
    @PositiveOrZero
    private Integer quantiteStock;
    
    @NotNull
    private BigDecimal prixUnitaire;
    
    private BigDecimal prixVente;
    
    @PositiveOrZero
    private Integer seuilMinimum;
    
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;

    // Constructors
    public ProduitDto() {}

    public ProduitDto(Long id, String nom, String description, Integer quantiteStock, 
                      BigDecimal prixUnitaire, BigDecimal prixVente, Integer seuilMinimum,
                      LocalDateTime dateCreation, LocalDateTime dateModification) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.quantiteStock = quantiteStock;
        this.prixUnitaire = prixUnitaire;
        this.prixVente = prixVente;
        this.seuilMinimum = seuilMinimum;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getQuantiteStock() { return quantiteStock; }
    public void setQuantiteStock(Integer quantiteStock) { this.quantiteStock = quantiteStock; }

    public BigDecimal getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(BigDecimal prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public BigDecimal getPrixVente() { return prixVente; }
    public void setPrixVente(BigDecimal prixVente) { this.prixVente = prixVente; }

    public Integer getSeuilMinimum() { return seuilMinimum; }
    public void setSeuilMinimum(Integer seuilMinimum) { this.seuilMinimum = seuilMinimum; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public LocalDateTime getDateModification() { return dateModification; }
    public void setDateModification(LocalDateTime dateModification) { this.dateModification = dateModification; }
}