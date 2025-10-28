package com.example.demo.repositories;

import com.example.demo.entities.DemandeConge;
import com.example.demo.entities.TypeConge;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DemandeCongeRepository extends PagingAndSortingRepository<DemandeConge,Long>, JpaRepository<DemandeConge,Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM DemandeConge d " +
            "WHERE d.employeTypeCongePK.employeId = :empId " +
            "AND d.employeTypeCongePK.typeCongeId = :typeId " +
            "AND d.employeTypeCongePK.dateDebut = :dateDebut")
    void deleteByCompositeKey(@Param("empId") Long empId,
                              @Param("typeId") Long typeId,
                              @Param("dateDebut") Date dateDebut);

    @Query("SELECT d FROM DemandeConge d WHERE d.employeTypeCongePK.employeId = :empId " +
            "AND d.employeTypeCongePK.typeCongeId = :typeId " +
            "AND d.employeTypeCongePK.dateDebut = :dateDebut")
    Optional<DemandeConge> findByCompositeKey(@Param("empId") Long empId,
                                              @Param("typeId") Long typeId,
                                              @Param("dateDebut") Date dateDebut);

    @Query(value = """
            SELECT e.departement AS departement,
                       SUM(DATEDIFF(d.date_fin, d.date_debut) ) AS total_jours
                FROM demande_conge d
                JOIN employe e ON d.employe = e.id
                WHERE d.statut = 'ACCEPTEE'
                GROUP BY e.departement
    """, nativeQuery = true)
    List<Object[]> getJoursParDepartement();

    @Query(value = """
            SELECT d.statut, COUNT(*) 
            FROM demande_conge d
            GROUP BY d.statut
            """, nativeQuery = true)
    List<Object[]> getStatistiquesParStatut();

    @Query("""
    SELECT d FROM DemandeConge d
    WHERE (:departement IS NULL OR d.employe.departement = :departement)
      AND (:typeCongeId IS NULL OR d.typeConge.id = :typeCongeId)
      AND (:dateDebut IS NULL OR d.employeTypeCongePK.dateDebut = :dateDebut)
      AND (:dateFin IS NULL OR d.dateFin = :dateFin)
      AND (:statut IS NULL OR d.statut = :statut)
""")
    Page<DemandeConge> filterDemandes(
            @Param("departement") String departement,
            @Param("typeCongeId") Long typeCongeId,
            @Param("dateDebut") Date dateDebut,
            @Param("dateFin") Date dateFin,
            @Param("statut") String statut,
            Pageable pageable
    );

    long countByStatut(String statut);



    @Query(value = """
            SELECT d.date_debut, d.date_fin, d.motif, d.statut,
                           e.id AS emp_id, e.nom, e.departement, e.date_embauche,
                           t.id AS type_id, t.libelle, t.quota_annuel
                    FROM demande_conge d
                    JOIN employe e ON d.employe = e.id
                    JOIN type_conge t ON d.typeconge = t.id
        """, nativeQuery = true)
    List<Object[]> getAllDemandeCongeDetails();


    @Query(value = """
            SELECT MONTH(d.date_debut) AS mois, COUNT(*) AS total
            FROM demande_conge d
            GROUP BY MONTH(d.date_debut)
            ORDER BY mois
            """, nativeQuery = true)
    List<Object[]> countDemandesParMois();
}
