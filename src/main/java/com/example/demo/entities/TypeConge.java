package com.example.demo.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class TypeConge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;
    private int quotaAnnuel;

    @OneToMany(mappedBy = "typeConge",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private List<DemandeConge> demandes;

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public int getQuotaAnnuel() { return quotaAnnuel; }
    public void setQuotaAnnuel(int quotaAnnuel) { this.quotaAnnuel = quotaAnnuel; }

    public List<DemandeConge> getDemandes() { return demandes; }
    public void setDemandes(List<DemandeConge> demandes) { this.demandes = demandes; }
}
