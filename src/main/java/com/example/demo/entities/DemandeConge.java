package com.example.demo.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Entity
public class DemandeConge {

    @EmbeddedId
    private EmployeTypeCongePK employeTypeCongePK;



    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateFin;
    private String statut;
    private String motif;

    @MapsId("employeId")
    @ManyToOne
    @JoinColumn(name = "employe")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employe employe;

    @MapsId("typeCongeId")
    @ManyToOne
    @JoinColumn(name = "typeconge")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TypeConge typeConge;


    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getMotif() { return motif; }
    public void setMotif(String motif) { this.motif = motif; }

    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) { this.employe = employe; }

    public TypeConge getTypeConge() { return typeConge; }
    public void setTypeConge(TypeConge typeConge) { this.typeConge = typeConge; }

    public EmployeTypeCongePK getEmployeTypeCongePK() {
        return employeTypeCongePK;
    }

    public void setEmployeTypeCongePK(EmployeTypeCongePK employeTypeCongePK) {
        this.employeTypeCongePK = employeTypeCongePK;
    }
}
