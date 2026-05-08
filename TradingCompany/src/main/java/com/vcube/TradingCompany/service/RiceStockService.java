package com.vcube.TradingCompany.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vcube.TradingCompany.model.Rice;
import com.vcube.TradingCompany.model.RiceStock;
import com.vcube.TradingCompany.repository.RiceStockRepository;

@Service
public class RiceStockService {

    @Autowired
    private RiceStockRepository riceStockRepository;

    // ➕ Add or Update Stock
    public RiceStock addStock(RiceStock newStock) {

        // ✅ Validation
        if (newStock.getStock() <= 0) {
            throw new RuntimeException("Stock must be greater than 0");
        }

        if (newStock.getPrice() <= 0) {
            throw new RuntimeException("Price must be greater than 0");
        }

        if (newStock.getBagSize() == null || newStock.getBagSize().isEmpty()) {
            throw new RuntimeException("Bag size is required");
        }

        // Clean bag size
        newStock.setBagSize(newStock.getBagSize().trim());

        // ✅ Check existing (same rice + bag size)
        RiceStock existing = riceStockRepository
                .findByRiceAndBagSize(newStock.getRice(), newStock.getBagSize());

        if (existing != null) {
            // 🔄 Update stock
            existing.setStock(existing.getStock() + newStock.getStock());

            // Update latest price
            existing.setPrice(newStock.getPrice());

            return riceStockRepository.save(existing);
        }

        // 🆕 New stock
        return riceStockRepository.save(newStock);
    }

    // 📋 Get all stock
    public List<RiceStock> getAllStock() {
        return riceStockRepository.findAll();
    }

    // 🔍 Get stock by ID
    public RiceStock getStockById(Long id) {
        return riceStockRepository.findById(id).orElse(null);
    }

    // 📦 Get stock by rice
    public List<RiceStock> getStockByRice(Rice rice) {
        return riceStockRepository.findByRice(rice);
    }

    // 📦 Get available stock only (>0)
    public List<RiceStock> getAvailableStock() {
        return riceStockRepository.findByStockGreaterThan(0);
    }

    // 🔄 Update stock manually
    public RiceStock updateStock(Long id, int newStock) {

        RiceStock stock = riceStockRepository.findById(id).orElse(null);

        if (stock == null) {
            throw new RuntimeException("Stock not found");
        }

        if (newStock < 0) {
            throw new RuntimeException("Stock cannot be negative");
        }

        stock.setStock(newStock);
        return riceStockRepository.save(stock);
    }

    // 🔥 NEW: Total Stock Value
    public double getTotalStockValue() {
        return riceStockRepository.findAll()
                .stream()
                .mapToDouble(s -> s.getStock() * s.getPrice())
                .sum();
    }

    // 🔥 NEW: Total Bags Count
    public int getTotalBags() {
        return riceStockRepository.findAll()
                .stream()
                .mapToInt(RiceStock::getStock)
                .sum();
    }

}