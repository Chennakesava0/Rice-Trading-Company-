package com.vcube.TradingCompany.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vcube.TradingCompany.model.Rice;
import com.vcube.TradingCompany.service.RiceService;

@Controller
@RequestMapping("/rice")
public class RiceController {

    @Autowired
    private RiceService riceService;

    // 📋 View rice
    @GetMapping("/list")
    public String listRice(Model model) {
        List<Rice> riceList = riceService.getAllRice();
        model.addAttribute("riceList", riceList);
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