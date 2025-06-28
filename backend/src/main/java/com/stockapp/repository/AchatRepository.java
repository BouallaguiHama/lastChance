package com.stockapp.repository;

import com.stockapp.entity.Achat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AchatRepository extends JpaRepository<Achat, Long> {
    
    List<Achat> findByProduitId(Long produitId);
    
    List<Achat> findByFournisseurId(Long fournisseurId);
    
    @Query("SELECT a FROM Achat a WHERE a.dateAchat BETWEEN :dateDebut AND :dateFin")
    List<Achat> findByDateAchatBetween(@Param("dateDebut") LocalDateTime dateDebut, 
                                       @Param("dateFin") LocalDateTime dateFin);
}