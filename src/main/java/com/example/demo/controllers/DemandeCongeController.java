package com.example.demo.controllers;


import com.example.demo.entities.DemandeConge;
import com.example.demo.entities.EmployeTypeCongePK;
import com.example.demo.repositories.DemandeCongeRepository;
import com.example.demo.repositories.EmployeRepository;
import com.example.demo.repositories.TypeCongeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class DemandeCongeController {

    @Autowired
    private DemandeCongeRepository demandeCongeRepository;

    @Autowired
    private EmployeRepository employeRepository;
    @Autowired
    private TypeCongeRepository typeCongeRepository;

    @GetMapping({"/demandeConge", "/demandes"})
    public String showDemandes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {


        model.addAttribute("demandeConge", new DemandeConge());

        model.addAttribute("employes", employeRepository.findAll());
        model.addAttribute("typeConges", typeCongeRepository.findAll());

        Pageable pageable = PageRequest.of(page, size);
        Page<DemandeConge> demandePage = demandeCongeRepository.findAll(pageable);
        
        model.addAttribute("demandes", demandePage.getContent());
        model.addAttribute("currentPage", "demandeConge");
        model.addAttribute("totalPages", demandePage.getTotalPages());
        model.addAttribute("currentPageNumber", page);
        model.addAttribute("totalElements", demandePage.getTotalElements());

        return "demande-conge";
    }




    @PostMapping("/addDemandeConge")
    public String addDemandeConge(@Valid DemandeConge demandeConge, BindingResult result, Model model) {

        Date dateDebut = demandeConge.getEmployeTypeCongePK().getDateDebut();
        Date dateFin = demandeConge.getDateFin();


        if (dateFin != null && dateDebut != null && !dateFin.after(dateDebut)) {
            result.reject("global", "La date de fin doit être supérieure à la date de début.");
        }

        if (result.hasErrors()) {
            model.addAttribute("employes", employeRepository.findAll());
            model.addAttribute("typeConges", typeCongeRepository.findAll());
            model.addAttribute("demandes", demandeCongeRepository.findAll());
            return "demande-conge";
        }

        Long empId = demandeConge.getEmployeTypeCongePK().getEmployeId();
        Long typeId = demandeConge.getEmployeTypeCongePK().getTypeCongeId();

        demandeConge.setEmploye(employeRepository.findById(empId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employe ID")));
        demandeConge.setTypeConge(typeCongeRepository.findById(typeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid typeConge ID")));

        demandeCongeRepository.save(demandeConge);
        return "redirect:/demandeConge";
    }


    @GetMapping("/addDemandeConge")
    public String showAddForm(DemandeConge demandeConge) {
        return "add-demandeConge";
    }





    @GetMapping("/editDemandeConge/{empId}/{typeId}/{dateDebut}")
    public String showUpdateForm(
            @PathVariable("empId") Long empId,
            @PathVariable("typeId") Long typeId,
            @PathVariable("dateDebut") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDebut,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model
    ) {
        DemandeConge demandeConge = demandeCongeRepository.findByCompositeKey(empId, typeId, dateDebut)
                .orElseThrow(() -> new IllegalArgumentException("Invalid demandeConge"));

        if (demandeConge.getEmployeTypeCongePK() == null) {
            demandeConge.setEmployeTypeCongePK(new EmployeTypeCongePK());
            demandeConge.getEmployeTypeCongePK().setEmployeId(empId);
            demandeConge.getEmployeTypeCongePK().setTypeCongeId(typeId);
            demandeConge.getEmployeTypeCongePK().setDateDebut(dateDebut);
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<DemandeConge> demandePage = demandeCongeRepository.findAll(pageable);

        model.addAttribute("demandeConge", demandeConge);
        model.addAttribute("employes", employeRepository.findAll());
        model.addAttribute("typeConges", typeCongeRepository.findAll());
        model.addAttribute("isEditMode", true);
        model.addAttribute("currentPage", "demandeConge");
        model.addAttribute("demandes", demandePage.getContent());
        model.addAttribute("totalPages", demandePage.getTotalPages());
        model.addAttribute("currentPageNumber", page);
        model.addAttribute("totalElements", demandePage.getTotalElements());
        
        return "update-demande-conge";
    }

    @PostMapping("/updateDemandeConge/{id}")
    public String updateDemandeConge(@PathVariable("id") long id, @Valid DemandeConge demandeConge, BindingResult result, Model model) {
        if (result.hasErrors()) {
           // demandeConge.se(id);
            model.addAttribute("demandeConges", demandeCongeRepository.findAll());
            return "update-demandeConge"; // or return "index";
        }
        demandeCongeRepository.save(demandeConge);
        return "redirect:/demandeConges";
    }

    @GetMapping("/deleteDemandeConge/{employeId}/{typeCongeId}/{dateDebut}")
    public String deleteDemandeConge(
            @PathVariable("employeId") Long employeId,
            @PathVariable("typeCongeId") Long typeCongeId,
            @PathVariable("dateDebut") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDebut
    ) {
        demandeCongeRepository.deleteByCompositeKey(employeId, typeCongeId, dateDebut);
        return "redirect:/demandeConge";
    }


    @GetMapping("/demandeCongeFilter")
    public String showDemandes(
            @RequestParam(value = "departement", required = false) String departement,
            @RequestParam(value = "typeCongeId", required = false) Long typeCongeId,
            @RequestParam(value = "dateDebut", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDebut,
            @RequestParam(value = "dateFin", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFin,
            @RequestParam(value = "statut", required = false) String statut,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model
    ) {
        List<DemandeConge> demandes = (List<DemandeConge>) demandeCongeRepository.findAll();

        if (departement != null && !departement.isBlank()) {
            demandes = demandes.stream()
                    .filter(d -> d.getEmploye() != null
                            && d.getEmploye().getDepartement() != null
                            && d.getEmploye().getDepartement().equalsIgnoreCase(departement))
                    .collect(Collectors.toList());
        }

        if (typeCongeId != null) {
            demandes = demandes.stream()
                    .filter(d -> d.getTypeConge() != null
                            && d.getTypeConge().getId().equals(typeCongeId))
                    .collect(Collectors.toList());
        }

        if (dateDebut != null) {
            demandes = demandes.stream()
                    .filter(d -> d.getEmployeTypeCongePK() != null
                            && d.getEmployeTypeCongePK().getDateDebut() != null
                            && d.getEmployeTypeCongePK().getDateDebut().equals(dateDebut))
                    .collect(Collectors.toList());
        }

        if (dateFin != null) {
            demandes = demandes.stream()
                    .filter(d -> d.getDateFin() != null
                            && d.getDateFin().equals(dateFin))
                    .collect(Collectors.toList());
        }

        if (statut != null && !statut.isBlank()) {
            demandes = demandes.stream()
                    .filter(d -> d.getStatut() != null
                            && d.getStatut().equalsIgnoreCase(statut))
                    .collect(Collectors.toList());
        }

        int totalElements = demandes.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        int start = page * size;
        int end = Math.min(start + size, totalElements);
        List<DemandeConge> paginatedDemandes = (start < totalElements) ? demandes.subList(start, end) : List.of();

        model.addAttribute("demandeConge", new DemandeConge());
        model.addAttribute("employes", employeRepository.findAll());
        model.addAttribute("typeConges", typeCongeRepository.findAll());
        model.addAttribute("demandes", paginatedDemandes);
        model.addAttribute("currentPage", "demandeConge");
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPageNumber", page);
        model.addAttribute("totalElements", totalElements);
        model.addAttribute("departement", departement);
        model.addAttribute("typeCongeId", typeCongeId);
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("dateFin", dateFin);
        model.addAttribute("statut", statut);

        return "demande-conge";
    }



}
