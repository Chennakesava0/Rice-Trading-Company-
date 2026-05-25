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

        // ✅ TOTAL PAID AMOUNT (safe null handling)
        Double totalPaid = saleService.getTotalPaidAmount();
        model.addAttribute("totalPaid", totalPaid != null ? totalPaid : 0.0);

        // ✅ PENDING AMOUNT (safe null handling)
        Double pendingAmount = saleService.getPendingAmount();
        model.addAttribute("pendingAmount", pendingAmount != null ? pendingAmount : 0.0);

        // ✅ TOTAL BAGS SOLD
        Integer totalBagsSold = saleService.getTotalBagsSold();
        model.addAttribute("totalBags", totalBagsSold != null ? totalBagsSold : 0);

        // ✅ STOCK LIST
        model.addAttribute("stockList", riceStockService.getAllStock());

        // ✅ TOTAL STOCK BAGS
        model.addAttribute("totalStock", riceStockService.getTotalBags());

        return "dashboard";
    }
}