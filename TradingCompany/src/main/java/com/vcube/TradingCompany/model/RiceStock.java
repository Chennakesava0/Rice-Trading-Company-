package com.vcube.TradingCompany.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class RiceStock {

//    public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public Rice getRice() {
//		return rice;
//	}
//
//	public void setRice(Rice rice) {
//		this.rice = rice;
//	}
//
//	public String getBagSize() {
//		return bagSize;
//	}
//
//	public void setBagSize(String bagSize) {
//		this.bagSize = bagSize;
//	}
//
//	public double getPrice() {
//		return price;
//	}
//
//	public void setPrice(double price) {
//		this.price = price;
//	}
//
//	public int getStock() {
//		return stock;
//	}
//
//	public void setStock(int stock) {
//		this.stock = stock;
//	}
//
//	public double getTotalPrice() {
//		return totalPrice;
//	}
//
//	public void setTotalPrice(double totalPrice) {
//		this.totalPrice = totalPrice;
//	}

	@Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Rice rice;

    private String bagSize; // 5kg, 25kg, etc

    private double price;

    private int stock;

    private double totalPrice;
}