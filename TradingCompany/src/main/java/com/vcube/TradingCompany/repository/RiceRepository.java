package com.vcube.TradingCompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vcube.TradingCompany.model.Rice;

public interface RiceRepository extends JpaRepository<Rice, Long> {

    // Find rice by name (optional)
    Rice findByName(String name);
}