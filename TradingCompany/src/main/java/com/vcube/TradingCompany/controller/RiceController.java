package com.vcube.TradingCompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vcube.TradingCompany.model.Rice;
import com.vcube.TradingCompany.service.RiceService;

@Controller
@RequestMapping("/rice")
public class RiceController {

    @Autowired
    private RiceService riceService;

    // 📋 View rice
    @GetMapping("/list")
    public String listRice(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        Page<Rice> ricePage = riceService.getRicePaginated(page, size);

        model.addAttribute("ricePage", ricePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", ricePage.getTotalPages());

        return "rice-list";
    }

    // ➕ Add rice page
    @GetMapping("/add")
    public String addRicePage(Model model) {
        model.addAttribute("rice", new Rice());
        return "add-rice";
    }

    // 💾 Save rice
    @PostMapping("/save")
    public String saveRice(@ModelAttribute Rice rice) {
        riceService.addRice(rice);
        return "redirect:/rice/list";
    }
}