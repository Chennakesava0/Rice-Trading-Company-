package com.vcube.TradingCompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
	public String stockList(@RequestParam(defaultValue = "0") int page,
	                        Model model) {

	    int size = 5; // records per page

	    Page<RiceStock> stockPage =
	            riceStockService.getAllStock(PageRequest.of(page, size));

	    model.addAttribute("stocks", stockPage.getContent());
	    model.addAttribute("currentPage", page);
	    model.addAttribute("totalPages", stockPage.getTotalPages());

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
	public String saveStock(@RequestParam("riceId") Long riceId, @ModelAttribute RiceStock stock, Model model) {

		// ✅ Fetch rice
		Rice rice = riceService.getRiceById(riceId);

		// ✅ Rice validation
		if (rice == null) {

			model.addAttribute("message", "Rice not found");

			model.addAttribute("riceList", riceService.getAllRice());

			return "add-stock";
		}

		// ✅ Set rice
		stock.setRice(rice);

		// ✅ Save stock
		String message = riceStockService.addStock(stock);

		// ✅ If validation error stay on same page
		if (!message.equals("Stock added successfully") && !message.equals("Stock updated successfully")) {

			model.addAttribute("message", message);

			model.addAttribute("riceList", riceService.getAllRice());

			model.addAttribute("stock", stock);

			return "add-stock";
		}

		// ✅ Success → Redirect to stock list
		return "redirect:/stock/list";
	}
}