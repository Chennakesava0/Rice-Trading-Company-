package com.vcube.TradingCompany.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vcube.TradingCompany.model.RiceStock;
import com.vcube.TradingCompany.model.Sale;
import com.vcube.TradingCompany.service.RiceStockService;
import com.vcube.TradingCompany.service.SaleService;

@Controller
public class ReportsController {

	@Autowired
	private SaleService saleService;

	@Autowired
	private RiceStockService riceStockService;

	// ---------------- REPORT PAGE ----------------
	@GetMapping("/reports")
	public String reportsPage() {
		return "reports";
	}

	// ---------------- SALES PAGINATION ----------------
	@GetMapping("/reports/sales")
	public String salesHistory(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size,
			Model model) {

		Pageable pageable = PageRequest.of(page, size);

		Page<Sale> salesPage = saleService.getAllSales(pageable);

		model.addAttribute("salesPage", salesPage);
		model.addAttribute("salesList", salesPage.getContent());

		return "sales-history";
	}

	@GetMapping("/reports/sales/date")
	public String salesByDate(@RequestParam LocalDate date, Model model) {
		model.addAttribute("salesList", saleService.getSalesByDate(date));
		return "sales-history";
	}

	@GetMapping("/reports/sales/month")
	public String salesByMonth(@RequestParam String month, Model model) {
		String[] parts = month.split("-");
		int year = Integer.parseInt(parts[0]);
		int monthValue = Integer.parseInt(parts[1]);
		model.addAttribute("salesList", saleService.getSalesByMonth(year, monthValue));
		return "sales-history";
	}

	// ---------------- STOCK PAGINATION ----------------
	@GetMapping("/reports/stock")
	public String stockHistory(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size,
			Model model) {

		Pageable pageable = PageRequest.of(page, size);

		Page<RiceStock> stockPage = riceStockService.getAllStock(pageable);

		model.addAttribute("stockPage", stockPage);
		model.addAttribute("stockList", stockPage.getContent());

		return "stock-history";
	}

	// ---------------- STOCK BY DATE ----------------
	@GetMapping("/reports/stock/date")
	public String stockByDate(@RequestParam LocalDate date, Model model) {

		model.addAttribute("stockList", riceStockService.getStockByDate(date));

		return "stock-history";
	}

	// ---------------- STOCK BY MONTH ----------------
	@GetMapping("/reports/stock/month")
	public String stockByMonth(@RequestParam String month, Model model) {

		String[] parts = month.split("-");
		int year = Integer.parseInt(parts[0]);
		int monthValue = Integer.parseInt(parts[1]);

		model.addAttribute("stockList", riceStockService.getStockByMonth(year, monthValue));

		return "stock-history";
	}

	// ---------------- UNPAID ----------------
	@GetMapping("/reports/unpaid")
	public String unpaidCustomers(Model model) {

		model.addAttribute("unpaidList", saleService.getUnpaidCustomers());

		return "unpaid-list";
	}
}