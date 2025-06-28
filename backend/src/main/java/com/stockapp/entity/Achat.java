package com.stockapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "achats")
public class Achat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fournisseur_id", nullable = false)
    private Fournisseur fournisseur;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Integer quantite;

    @NotNull
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal prixUnitaire;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montantTotal;

    @Column(updatable = false)
    private LocalDateTime dateAchat = LocalDateTime.now();

    @OneToOne(mappedBy = "achat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BonDAchat bonDAchat;

    // Constructors
    public Achat() {}

    public Achat(Produit produit, Fournisseur fournisseur, Integer quantite, BigDecimal prixUnitaire) {
        this.produit = produit;
        this.fournisseur = fournisseur;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.montantTotal = prixUnitaire.multiply(BigDecimal.valueOf(quantite));
    }

    @PrePersist
    @PreUpdate
    public void calculateTotal() {
        if (prixUnitaire != null && quantite != null) {
            this.montantTotal = prixUnitaire.multiply(BigDecimal.valueOf(quantite));
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }

    public Fournisseur getFournisseur() { return fournisseur; }
    public void setFournisseur(Fournisseur fournisseur) { this.fournisseur = fournisseur; }

    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }

    public BigDecimal getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(BigDecimal prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public BigDecimal getMontantTotal() { return montantTotal; }
    public void setMontantTotal(BigDecimal montantTotal) { this.montantTotal = montantTotal; }

    public LocalDateTime getDateAchat() { return dateAchat; }
    public void setDateAchat(LocalDateTime dateAchat) { this.dateAchat = dateAchat; }

    public BonDAchat getBonDAchat() { return bonDAchat; }
    public void setBonDAchat(BonDAchat bonDAchat) { this.bonDAchat = bonDAchat; }
}