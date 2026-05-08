package com.vcube.TradingCompany.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vcube.TradingCompany.model.Rice;
import com.vcube.TradingCompany.model.RiceStock;
import com.vcube.TradingCompany.service.RiceService;
import com.vcube.TradingCompany.service.RiceStockService;

@Controller
@RequestMapping("/stock")
public class RiceStockController {

    @Autowired
    private RiceStockService riceStockService;

    @Autowired
    private RiceService riceService;

    // 📋 View stock list
    @GetMapping("/list")
    public String stockList(Model model) {

        List<RiceStock> stocks = riceStockService.getAllStock();

        model.addAttribute("stocks", stocks);

        // 🔥 ADD TOTALS
        model.addAttribute("totalValue", riceStockService.getTotalStockValue());
        model.addAttribute("totalBags", riceStockService.getTotalBags());

        return "stock-list";
    }

    // ➕ Add stock page
    @GetMapping("/add")
    public String addStockPage(Model model) {

        model.addAttribute("stock", new RiceStock());
        model.addAttribute("riceList", riceService.getAllRice());

        return "add-stock";
    }

    // 💾 Save stock (CLEAN VERSION)
    @PostMapping("/save")
    public String saveStock(@RequestParam("riceId") Long riceId,
                            @ModelAttribute RiceStock stock) {

        // ✅ Fetch rice from DB
        Rice rice = riceService.getRiceById(riceId);

        // ❗ Check if rice exists
        if (rice == null) {
            throw new RuntimeException("Rice not found");
        }

        // ✅ Set rice into stock
        stock.setRice(rice);

        // ✅ Save
        riceStockService.addStock(stock);

        return "redirect:/stock/list";
    }
}