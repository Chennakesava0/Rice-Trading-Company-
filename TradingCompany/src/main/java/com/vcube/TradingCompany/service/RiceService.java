package com.vcube.TradingCompany.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vcube.TradingCompany.model.Rice;
import com.vcube.TradingCompany.repository.RiceRepository;

@Service
public class RiceService {

    @Autowired
    private RiceRepository riceRepository;

    // ➕ Add Rice
    public Rice addRice(Rice rice) {
        return riceRepository.save(rice);
    }

    // 📋 Get all rice
    public List<Rice> getAllRice() {
        return riceRepository.findAll();
    }

    // 🔍 Get rice by ID
    public Rice getRiceById(Long id) {
        return riceRepository.findById(id).orElse(null);
    }
}