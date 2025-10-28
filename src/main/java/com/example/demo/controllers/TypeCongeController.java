package com.example.demo.controllers;

import com.example.demo.entities.TypeConge;
import com.example.demo.repositories.TypeCongeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeCongeController {
    @Autowired
    private TypeCongeRepository typeCongeRepository;

    @GetMapping({"/typeConge"})
    public String showTypeConges(Model model) {
        model.addAttribute("typeConge", new TypeConge());
        model.addAttribute("typeConges", typeCongeRepository.findAll());
        model.addAttribute("currentPage","typeConge");
        return "type-conge";
    }




    @PostMapping("/addTypeConge")
    public String addTypeConge(@Valid TypeConge typeConge, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("typeConge", typeConge);
            model.addAttribute("typeConges", typeCongeRepository.findAll());
            return "index";
        }
        typeCongeRepository.save(typeConge);
        return "redirect:/typeConge";
    }


    @GetMapping("/addTypeConge")
    public String showAddForm(TypeConge typeConge) {
        return "add-typeConge";
    }





    @GetMapping("/editTypeConge/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        TypeConge typeConge = typeCongeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid typeConge Id:" + id));

        model.addAttribute("typeConge", typeConge);

        model.addAttribute("typeConges", typeCongeRepository.findAll());
        model.addAttribute("currentPage","typeConge");
        return "update-type-conge";
    }

    @PostMapping("/updateTypeConge/{id}")
    public String updateTypeConge(@PathVariable("id") long id, @Valid TypeConge typeConge, BindingResult result, Model model) {
        if (result.hasErrors()) {
            typeConge.setId(id);
            model.addAttribute("typeConges", typeCongeRepository.findAll());
            return "update-typeConge"; // or return "index";
        }
        typeCongeRepository.save(typeConge);
        return "redirect:/typeConge";
    }

    @GetMapping("/deleteTypeConge/{id}")
    public String deleteTypeConge(@PathVariable("id") long id) {
        TypeConge typeConge = typeCongeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid typeConge Id:" + id));
        typeCongeRepository.delete(typeConge);
        return "redirect:/typeConge";
    }


    @GetMapping("/typeCongeFilter")
    public String showTypeConges(@RequestParam(value = "search", required = false) String search, Model model) {
        model.addAttribute("typeConge", new TypeConge());
        model.addAttribute("currentPage", "typeConge");

        Iterable<TypeConge> filteredTypeConges;

        if (search != null && !search.trim().isEmpty()) {
            filteredTypeConges = typeCongeRepository.findByLibelleContainingIgnoreCase(search);
        } else {
            filteredTypeConges = typeCongeRepository.findAll();
        }

        model.addAttribute("typeConges", filteredTypeConges);

        return "type-conge";
    }
}
