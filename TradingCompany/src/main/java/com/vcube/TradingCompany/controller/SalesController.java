package com.vcube.TradingCompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vcube.TradingCompany.model.Sale;
import com.vcube.TradingCompany.service.RiceStockService;
import com.vcube.TradingCompany.service.SaleService;

@Controller
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private SaleService saleService;

    @Autowired
    private RiceStockService riceStockService;

    // Sell Page
    @GetMapping("/sell")
    public String sellPage(Model model) {

        model.addAttribute("stockList",
                riceStockService.getAvailableStock());

        return "sell-rice";
    }

    // Process Sale
    @PostMapping("/sell")
    public String sellRice(@RequestParam Long stockId,
                           @RequestParam int bagsSold,
                           @RequestParam String paymentStatus,
                           @RequestParam String customerName,
                           Model model) {

        // ❌ VALIDATION AT CONTROLLER LEVEL (FAST RESPONSE)
        if (bagsSold <= 0) {
            model.addAttribute("message", "❌ Bags sold must be greater than 0");
            model.addAttribute("stockList", riceStockService.getAvailableStock());
            return "sell-rice";
        }

        // Call service
        String message = saleService.sellRice(
                stockId,
                customerName,
                bagsSold,
                paymentStatus
        );

        // ❌ IF ERROR FROM SERVICE
        if (!message.toLowerCase().contains("success")) {
            model.addAttribute("message", message);
            model.addAttribute("stockList", riceStockService.getAvailableStock());
            return "sell-rice";
        }

        // ✅ SUCCESS → REDIRECT TO SALES LIST
        return "redirect:/sales/list";
    }

    // View Sales
    @GetMapping("/list")
    public String salesList(@RequestParam(defaultValue = "0") int page,
                            Model model) {

        int size = 8;

        Page<Sale> salesPage =
                saleService.getAllSales(PageRequest.of(page, size));

        model.addAttribute("sales", salesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", salesPage.getTotalPages());

        return "sales";
    }

    // Mark As Paid
    @GetMapping("/pay/{id}")
    public String markAsPaid(@PathVariable Long id) {

        saleService.markAsPaid(id);

        return "redirect:/sales/list";
    }
    
    @GetMapping("/receipt/{id}")
    public String receipt(@PathVariable Long id,
                          Model model) {

        Sale sale = saleService.getSaleById(id);

        model.addAttribute("sale", sale);

        return "receipt";
    }
}