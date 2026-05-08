package com.vcube.TradingCompany.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vcube.TradingCompany.model.RiceStock;
import com.vcube.TradingCompany.model.Sale;
import com.vcube.TradingCompany.repository.RiceStockRepository;
import com.vcube.TradingCompany.repository.SaleRepository;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private RiceStockRepository riceStockRepository;

    public static final String PAID = "PAID";
    public static final String PENDING = "PENDING";

    // 🛒 SELL RICE
    public String sellRice(Long stockId,
                           String customerName,
                           int bags,
                           String paymentStatus) {

        // ✅ Validate bags
        if (bags <= 0) {
            return "Invalid quantity";
        }

        // ✅ Find stock
        RiceStock stock = riceStockRepository.findById(stockId).orElse(null);

        if (stock == null) {
            return "Stock not found";
        }

        // ✅ Check stock availability
        if (stock.getStock() < bags) {
            return "Not enough stock available";
        }

        // ✅ Reduce stock
        stock.setStock(stock.getStock() - bags);
        riceStockRepository.save(stock);

        // ✅ Calculate amount
        double totalAmount = bags * stock.getPrice();

        // ✅ Create sale object
        Sale sale = new Sale();

        sale.setRiceStock(stock);
        sale.setCustomerName(customerName);
        sale.setBagsSold(bags);
        sale.setTotalAmount(totalAmount);
        sale.setPaymentStatus(paymentStatus);
        sale.setDate(new Date());

        // ✅ Save sale
        saleRepository.save(sale);

        return "Sale successful";
    }

    // 📋 Get all sales
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    // 💰 Total paid amount
    public double getTotalPaidAmount() {

        return saleRepository.findAll()
                .stream()
                .filter(sale -> PAID.equalsIgnoreCase(sale.getPaymentStatus()))
                .mapToDouble(Sale::getTotalAmount)
                .sum();
    }

    // ⏳ Total pending amount
    public double getPendingAmount() {

        return saleRepository.findAll()
                .stream()
                .filter(sale -> PENDING.equalsIgnoreCase(sale.getPaymentStatus()))
                .mapToDouble(Sale::getTotalAmount)
                .sum();
    }

    // 🔄 Mark as paid
    public String markAsPaid(Long saleId) {

        Sale sale = saleRepository.findById(saleId).orElse(null);

        if (sale == null) {
            return "Sale not found";
        }

        sale.setPaymentStatus(PAID);

        saleRepository.save(sale);

        return "Payment updated successfully";
    }

    // 📊 Total bags sold
    public int getTotalBagsSold() {

        return saleRepository.findAll()
                .stream()
                .mapToInt(Sale::getBagsSold)
                .sum();
    }
}