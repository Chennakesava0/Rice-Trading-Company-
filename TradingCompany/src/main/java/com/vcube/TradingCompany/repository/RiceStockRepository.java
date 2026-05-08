package com.vcube.TradingCompany.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vcube.TradingCompany.model.Rice;
import com.vcube.TradingCompany.model.RiceStock;

public interface RiceStockRepository extends JpaRepository<RiceStock, Long> {

    // Get all stock for a particular rice
    List<RiceStock> findByRice(Rice rice);

    // Get specific stock by rice + bag size
    RiceStock findByRiceAndBagSize(Rice rice, String bagSize);

    // Get all stocks with available quantity
    List<RiceStock> findByStockGreaterThan(int stock);
}