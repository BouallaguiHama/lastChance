package com.stockapp.repository;

import com.stockapp.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    
    List<Produit> findByNomContainingIgnoreCase(String nom);
    
    @Query("SELECT p FROM Produit p WHERE p.quantiteStock <= p.seuilMinimum")
    List<Produit> findProduitsAvecStockFaible();
    
    @Query("SELECT p FROM Produit p WHERE p.quantiteStock = 0")
    List<Produit> findProduitsEnRupture();
}