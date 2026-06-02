package com.vcube.TradingCompany.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vcube.TradingCompany.model.Rice;
import com.vcube.TradingCompany.model.RiceStock;
import com.vcube.TradingCompany.repository.RiceStockRepository;

@Service
public class RiceStockService {

	@Autowired
	private RiceStockRepository riceStockRepository;

	// ➕ Add or Update Stock
	public String addStock(RiceStock newStock) {
		
		 // ✅ Current Date
	    newStock.setStockDate(LocalDate.now());

		// ✅ Stock validation
		if (newStock.getStock() <= 0) {

			return "Stock must be greater than 0";
		}

		// ✅ Stock count validation
		if (newStock.getStock() > 500) {

			return "Stock should be below 500 bags";
		}

		// ✅ Price validation
		if (newStock.getPrice() <= 0) {

			return "Price must be greater than 0";
		}

		// ✅ Price 5 digit validation
		if (newStock.getPrice() > 99999) {

			return "Price should contain only 5 digits";
		}

		// ✅ Bag size validation
		if (newStock.getBagSize() == null || newStock.getBagSize().trim().isEmpty()) {

			return "Bag size is required";
		}

		// ✅ Remove spaces
		newStock.setBagSize(newStock.getBagSize().trim());

		// ✅ Check existing stock
		RiceStock existing = riceStockRepository.findByRiceAndBagSize(newStock.getRice(), newStock.getBagSize());

		// ✅ Update existing stock
		if (existing != null) {

			existing.setStock(existing.getStock() + newStock.getStock());

			// Update latest price
			existing.setPrice(newStock.getPrice());

			riceStockRepository.save(existing);

			return "Stock updated successfully";
		}

		// ✅ Save new stock
		riceStockRepository.save(newStock);

		return "Stock added successfully";
	}

	// 📋 Get all stock
	public List<RiceStock> getAllStock() {
	    return riceStockRepository.findAll();
	}

	public Page<RiceStock> getAllStock(Pageable pageable) {
	    return riceStockRepository.findAll(pageable);
	}

	// 🔍 Get stock by ID
	public RiceStock getStockById(Long id) {

		return riceStockRepository.findById(id).orElse(null);
	}

	// 📦 Get stock by rice
	public List<RiceStock> getStockByRice(Rice rice) {

		return riceStockRepository.findByRice(rice);
	}

	// 📦 Get available stock only
	public List<RiceStock> getAvailableStock() {

		return riceStockRepository.findByStockGreaterThan(0);
	}

	// 🔄 Update stock manually
	public String updateStock(Long id, int newStock) {

		RiceStock stock = riceStockRepository.findById(id).orElse(null);

		// ✅ Null validation
		if (stock == null) {

			return "Stock not found";
		}

		// ✅ Negative validation
		if (newStock < 0) {

			return "Stock cannot be negative";
		}

		// ✅ Limit validation
		if (newStock > 500) {

			return "Stock should be below 500 bags";
		}

		// ✅ Update stock
		stock.setStock(newStock);

		riceStockRepository.save(stock);

		return "Stock updated successfully";
	}

	// 🔥 Total Stock Value
	public double getTotalStockValue() {

		return riceStockRepository.findAll()

				.stream()

				.mapToDouble(s -> s.getStock() * s.getPrice())

				.sum();
	}

	// 🔥 Total Bags Count
	public int getTotalBags() {

		return riceStockRepository.findAll()

				.stream()

				.mapToInt(RiceStock::getStock)

				.sum();
	}
	
	public List<RiceStock> getStockByDate(LocalDate date) {
	    return riceStockRepository.findByStockDate(date);
	}

	public List<RiceStock> getStockByMonth(int year, int month) {
	    return riceStockRepository.findByMonth(year, month);
	}

}