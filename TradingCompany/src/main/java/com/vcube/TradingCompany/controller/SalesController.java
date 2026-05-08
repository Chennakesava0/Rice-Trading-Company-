package com.vcube.TradingCompany.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

        String message =
                saleService.sellRice(
                        stockId,
                        customerName,
                        bagsSold,
                        paymentStatus);

        model.addAttribute("message", message);

        model.addAttribute("stockList",
                riceStockService.getAvailableStock());

        return "sell-rice";
    }

    // View Sales
    @GetMapping("/list")
    public String salesList(Model model) {

        List<Sale> sales = saleService.getAllSales();

        model.addAttribute("sales", sales);

        return "sales";
    }

    // Mark As Paid
    @GetMapping("/pay/{id}")
    public String markAsPaid(@PathVariable Long id) {

        saleService.markAsPaid(id);

        return "redirect:/sales/list";
    }
}