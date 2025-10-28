package com.example.demo.controllers;

import com.example.demo.entities.Employe;
import com.example.demo.repositories.EmployeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeController {

    @Autowired
    private EmployeRepository employeRepository;

    @GetMapping({"/employes", "/"})
    public String showEmployes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        
        model.addAttribute("employe", new Employe());

        Pageable pageable = PageRequest.of(page, size);
        Page<Employe> employeePage = employeRepository.findAll(pageable);
        
        model.addAttribute("employes", employeePage.getContent());
        model.addAttribute("currentPage", "dashboard");
        model.addAttribute("totalPages", employeePage.getTotalPages());
        model.addAttribute("currentPageNumber", page);
        model.addAttribute("totalElements", employeePage.getTotalElements());
        
        return "index";
    }




    @PostMapping("/addEmploye")
    public String addEmploye(@Valid Employe employe, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("employe", employe);
            model.addAttribute("employes", employeRepository.findAll());
            return "index";
        }
        employeRepository.save(employe);
        return "redirect:/employes";
    }
    @GetMapping("/addEmploye")
    public String showAddForm(Employe employe) {
        return "add-employe";
    }





    @GetMapping("/editEmploye/{id}")
    public String showUpdateForm(
            @PathVariable("id") long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employe Id:" + id));

        model.addAttribute("employe", employe);

        Pageable pageable = PageRequest.of(page, size);
        Page<Employe> employeePage = employeRepository.findAll(pageable);
        
        model.addAttribute("employes", employeePage.getContent());
        model.addAttribute("currentPage", "dashboard");
        model.addAttribute("totalPages", employeePage.getTotalPages());
        model.addAttribute("currentPageNumber", page);
        model.addAttribute("totalElements", employeePage.getTotalElements());
        
        return "update-employe";
    }

    @PostMapping("/updateEmploye/{id}")
    public String updateEmploye(@PathVariable("id") long id, @Valid Employe employe, BindingResult result, Model model) {
        if (result.hasErrors()) {
            employe.setId(id);
            model.addAttribute("employes", employeRepository.findAll());
            return "update-employe";
        }
        employeRepository.save(employe);
        return "redirect:/employes";
    }

    // Delete employee
    @GetMapping("/deleteEmploye/{id}")
    public String deleteEmploye(@PathVariable("id") long id) {
        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employe Id:" + id));
        employeRepository.delete(employe);
        return "redirect:/employes";
    }



    @GetMapping("/filterEmployes")
    public String filterEmployes(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String dateFilter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Employe> filteredPage;

        if ((search == null || search.isEmpty()) && (dateFilter == null || dateFilter.isEmpty())) {
            filteredPage = employeRepository.findAll(pageable);
        } else if (search != null && !search.isEmpty() && (dateFilter == null || dateFilter.isEmpty())) {
            filteredPage = employeRepository.findByNomContainingIgnoreCase(search, pageable);
        } else if ((search == null || search.isEmpty()) && (dateFilter != null && !dateFilter.isEmpty())) {
            filteredPage = employeRepository.findByDateEmbauche(java.sql.Date.valueOf(dateFilter), pageable);
        } else {
            filteredPage = employeRepository.findByNomContainingIgnoreCaseAndDateEmbauche(
                    search, java.sql.Date.valueOf(dateFilter), pageable);
        }

        model.addAttribute("employes", filteredPage.getContent());
        model.addAttribute("employe", new Employe());
        model.addAttribute("currentPage", "dashboard");
        model.addAttribute("totalPages", filteredPage.getTotalPages());
        model.addAttribute("currentPageNumber", page);
        model.addAttribute("totalElements", filteredPage.getTotalElements());
        model.addAttribute("search", search);
        model.addAttribute("dateFilter", dateFilter);

        return "index";
    }


    @GetMapping("/filterEmployesUpdate")
    public String filterEmployesUpdate(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String dateFilter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Employe> filteredPage;

        if ((search == null || search.isEmpty()) && (dateFilter == null || dateFilter.isEmpty())) {
            filteredPage = employeRepository.findAll(pageable);
        } else if (search != null && !search.isEmpty() && (dateFilter == null || dateFilter.isEmpty())) {
            filteredPage = employeRepository.findByNomContainingIgnoreCase(search, pageable);
        } else if ((search == null || search.isEmpty()) && (dateFilter != null && !dateFilter.isEmpty())) {
            filteredPage = employeRepository.findByDateEmbauche(java.sql.Date.valueOf(dateFilter), pageable);
        } else {
            filteredPage = employeRepository.findByNomContainingIgnoreCaseAndDateEmbauche(
                    search, java.sql.Date.valueOf(dateFilter), pageable);
        }

        model.addAttribute("employes", filteredPage.getContent());
        model.addAttribute("employe", new Employe());
        model.addAttribute("currentPage", "dashboard");
        model.addAttribute("totalPages", filteredPage.getTotalPages());
        model.addAttribute("currentPageNumber", page);
        model.addAttribute("totalElements", filteredPage.getTotalElements());
        model.addAttribute("search", search);
        model.addAttribute("dateFilter", dateFilter);

        return "update-employe";
    }
}
