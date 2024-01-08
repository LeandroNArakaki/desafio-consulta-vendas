package com.devsuperior.dsmeta.dto;

import java.time.LocalDate;

import com.devsuperior.dsmeta.entities.Sale;

public class SaleDTO {

	private Long id;
	private Double amount;
	private LocalDate date;
	private String sellerName;
	private Double total;

	public SaleDTO(Long id, Double amount, LocalDate date, String sellerName) {
		this.id = id;
		this.amount = amount;
		this.date = date;
		this.sellerName = sellerName;
	}

	public SaleDTO(String sellerName, Double total) {
		this.sellerName = sellerName;
		this.total = total;
	}

	public SaleDTO(Sale entity) {
		id = entity.getId();
		amount = entity.getAmount();
		date = entity.getDate();
		sellerName = entity.getSeller().getName();
	}

	public Long getId() {
		return id;
	}

	public Double getAmount() {
		return amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getSellerName() {
		return sellerName;
	}

	public Double getTotal() {
		return total;
	}
}
