package com.stockapp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bons_achat")
public class BonDAchat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numero;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "achat_id", nullable = false)
    private Achat achat;

    @Column(updatable = false)
    private LocalDateTime dateGeneration = LocalDateTime.now();

    // Constructors
    public BonDAchat() {}

    public BonDAchat(Achat achat) {
        this.achat = achat;
        this.numero = generateNumero();
    }

    private String generateNumero() {
        return "BA-" + System.currentTimeMillis();
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

    public Achat getAchat() { return achat; }
    public void setAchat(Achat achat) { this.achat = achat; }

    public LocalDateTime getDateGeneration() { return dateGeneration; }
    public void setDateGeneration(LocalDateTime dateGeneration) { this.dateGeneration = dateGeneration; }
}