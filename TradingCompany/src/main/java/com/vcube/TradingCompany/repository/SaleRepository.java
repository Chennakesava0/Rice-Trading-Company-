package com.vcube.TradingCompany.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vcube.TradingCompany.model.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    // Find sales by rice stock
    List<Sale> findByRiceStockId(Long stockId);

    // Find sales by payment status
    List<Sale> findByPaymentStatus(String paymentStatus);

    // Find sales by customer name
    List<Sale> findByCustomerName(String customerName);
}