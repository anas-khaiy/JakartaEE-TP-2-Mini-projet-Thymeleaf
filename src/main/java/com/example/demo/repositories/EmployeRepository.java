package com.example.demo.repositories;

import com.example.demo.entities.Employe;
import com.example.demo.entities.TypeConge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface EmployeRepository extends PagingAndSortingRepository<Employe,Long>, CrudRepository<Employe,Long> {
    List<Employe> findByNomContainingIgnoreCase(String nom);

    List<Employe> findByDateEmbauche(Date dateEmbauche);

    List<Employe> findByNomContainingIgnoreCaseAndDateEmbauche(String nom, Date dateEmbauche);

    Page<Employe> findByNomContainingIgnoreCase(String nom, Pageable pageable);

    Page<Employe> findByDateEmbauche(Date dateEmbauche, Pageable pageable);

    Page<Employe> findByNomContainingIgnoreCaseAndDateEmbauche(String nom, Date dateEmbauche, Pageable pageable);
}

