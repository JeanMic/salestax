package com.jean.salestax.service;

import java.util.List;
import java.util.Optional;

import com.jean.salestax.api.dto.PurchaseDTO;
import com.jean.salestax.api.dto.PurchaseReceiptDTO;
import com.jean.salestax.model.entity.Product;

public interface PurchaseService {

	Optional<Product> findById(Long id);
	
	boolean existsById(Long id);
	
	public Double calculateTaxs(List<Product> products);
	
	public Double calculateAmountOfPurchase(List<Product> products, Double tax);
	
	public Double calculateAmountOfProduct(Product product);
	
	public PurchaseReceiptDTO sumary(List<PurchaseDTO> dtos);
}
