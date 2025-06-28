package com.stockapp.repository;

import com.stockapp.entity.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {
    
    List<Fournisseur> findByNomContainingIgnoreCase(String nom);
    
    Optional<Fournisseur> findByEmail(String email);
    
    List<Fournisseur> findByTelephone(String telephone);
}