package com.stockapp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "factures")
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numero;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vente_id", nullable = false)
    private Vente vente;

    @Column(updatable = false)
    private LocalDateTime dateGeneration = LocalDateTime.now();

    // Constructors
    public Facture() {}

    public Facture(Vente vente) {
        this.vente = vente;
        this.numero = generateNumero();
    }

    private String generateNumero() {
        return "FACT-" + System.currentTimeMillis();
    }

    @PrePersist
    public void prePersist() {
        if (numero == null) {
            numero = generateNumero();
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public Vente getVente() { return vente; }
    public void setVente(Vente vente) { this.vente = vente; }

    public LocalDateTime getDateGeneration() { return dateGeneration; }
    public void setDateGeneration(LocalDateTime dateGeneration) { this.dateGeneration = dateGeneration; }
}