package com.example.demo.repositories;

import com.example.demo.entities.TypeConge;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TypeCongeRepository extends CrudRepository<TypeConge,Long> {
    List<TypeConge> findByLibelleContainingIgnoreCase(String libelle);
}

