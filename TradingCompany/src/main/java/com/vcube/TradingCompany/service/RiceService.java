package com.vcube.TradingCompany.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    
    public Page<Rice> getRicePaginated(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return riceRepository.findAll(pageable);
    }
}