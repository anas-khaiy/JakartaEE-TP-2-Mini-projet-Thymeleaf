package com.example.demo.controllers;

import com.example.demo.entities.DemandeConge;
import com.example.demo.entities.Employe;
import com.example.demo.repositories.DemandeCongeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.Collections;

@Controller
public class ChartsController {

    @Autowired
    private DemandeCongeRepository demandeCongeRepository;

    @GetMapping("/charts")
    public String showCharts(Model model) {

        List<Object[]> results = demandeCongeRepository.getJoursParDepartement();
        List<String> departements = new ArrayList<>();
        List<Integer> jours = new ArrayList<>();
        for (Object[] row : results) {
            departements.add((String) row[0]);
            jours.add(((Number) row[1]).intValue());
        }

        long enAttente = demandeCongeRepository.countByStatut("EN_ATTENTE");
        long acceptee  = demandeCongeRepository.countByStatut("ACCEPTEE");
        long refusee   = demandeCongeRepository.countByStatut("REFUSEE");
        long annulee   = demandeCongeRepository.countByStatut("ANNULEE");

        List<Object[]> allDemandes = demandeCongeRepository.getAllDemandeCongeDetails();

        List<String> typeLabels = new ArrayList<>();
        List<Integer> typeValues = new ArrayList<>();
        Map<String, Integer> typeCountMap = new HashMap<>();

        for (Object[] row : allDemandes) {
            String typeLibelle = (String) row[9];
            typeCountMap.put(typeLibelle, typeCountMap.getOrDefault(typeLibelle, 0) + 1);
        }

        typeCountMap.forEach((key, value) -> {
            typeLabels.add(key);
            typeValues.add(value);
        });



        List<Object[]> moisResults = demandeCongeRepository.countDemandesParMois();
        List<String> moisLabels = List.of("Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");
        List<Integer> moisValues = new ArrayList<>(Collections.nCopies(12, 0)); // initialiser à 0

        for (Object[] row : moisResults) {
            int moisIndex = ((Number) row[0]).intValue() - 1; // mois 1=Jan
            int total = ((Number) row[1]).intValue();
            moisValues.set(moisIndex, total);
        }

        model.addAttribute("moisLabels", moisLabels);
        model.addAttribute("moisValues", moisValues);



        model.addAttribute("departements", departements);
        model.addAttribute("jours", jours);
        model.addAttribute("enAttente", enAttente);
        model.addAttribute("acceptee", acceptee);
        model.addAttribute("refusee", refusee);
        model.addAttribute("annulee", annulee);
        model.addAttribute("typeCongeLabels", typeLabels);
        model.addAttribute("typeCongeValues", typeValues);
        model.addAttribute("currentPage", "charts");

        return "charts";
    }
}