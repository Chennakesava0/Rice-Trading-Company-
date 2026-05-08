package com.vcube.TradingCompany.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Sale {

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RiceStock getRiceStock() {
		return riceStock;
	}

	public void setRiceStock(RiceStock riceStock) {
		this.riceStock = riceStock;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getBagsSold() {
		return bagsSold;
	}

	public void setBagsSold(int bagsSold) {
		this.bagsSold = bagsSold;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private RiceStock riceStock;

    private String customerName;

    private int bagsSold;

    private double totalAmount;

    private String paymentStatus;

    private Date date;
}