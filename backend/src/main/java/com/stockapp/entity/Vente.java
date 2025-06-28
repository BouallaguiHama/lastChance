package com.stockapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ventes")
public class Vente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

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
    private LocalDateTime dateVente = LocalDateTime.now();

    @OneToOne(mappedBy = "vente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Facture facture;

    @OneToOne(mappedBy = "vente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private BonDeLivraison bonDeLivraison;

    // Constructors
    public Vente() {}

    public Vente(Produit produit, Client client, Integer quantite, BigDecimal prixUnitaire) {
        this.produit = produit;
        this.client = client;
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

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }

    public BigDecimal getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(BigDecimal prixUnitaire) { this.prixUnitaire = prixUnitaire; }

    public BigDecimal getMontantTotal() { return montantTotal; }
    public void setMontantTotal(BigDecimal montantTotal) { this.montantTotal = montantTotal; }

    public LocalDateTime getDateVente() { return dateVente; }
    public void setDateVente(LocalDateTime dateVente) { this.dateVente = dateVente; }

    public Facture getFacture() { return facture; }
    public void setFacture(Facture facture) { this.facture = facture; }

    public BonDeLivraison getBonDeLivraison() { return bonDeLivraison; }
    public void setBonDeLivraison(BonDeLivraison bonDeLivraison) { this.bonDeLivraison = bonDeLivraison; }
}