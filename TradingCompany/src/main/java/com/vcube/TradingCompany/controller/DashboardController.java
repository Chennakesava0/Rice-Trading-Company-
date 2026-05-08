package com.vcube.TradingCompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.vcube.TradingCompany.service.RiceStockService;
import com.vcube.TradingCompany.service.SaleService;

@Controller
public class DashboardController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private RiceStockService riceStockService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("totalPaid", saleService.getTotalPaidAmount());
        model.addAttribute("pendingAmount", saleService.getPendingAmount());
        model.addAttribute("totalBags", saleService.getTotalBagsSold());
        model.addAttribute("stockList", riceStockService.getAllStock());
        // ✅ TOTAL STOCK
        model.addAttribute("totalStock",
                riceStockService.getTotalBags());
        return "dashboard";
    }
}