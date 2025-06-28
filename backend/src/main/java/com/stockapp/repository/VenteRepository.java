package com.stockapp.repository;

import com.stockapp.entity.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VenteRepository extends JpaRepository<Vente, Long> {
    
    List<Vente> findByProduitId(Long produitId);
    
    List<Vente> findByClientId(Long clientId);
    
    @Query("SELECT v FROM Vente v WHERE v.dateVente BETWEEN :dateDebut AND :dateFin")
    List<Vente> findByDateVenteBetween(@Param("dateDebut") LocalDateTime dateDebut, 
                                       @Param("dateFin") LocalDateTime dateFin);
}