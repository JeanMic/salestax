package com.jean.salestax.service;

import java.util.List;
import java.util.Optional;

import com.jean.salestax.model.entity.Product;

public interface ProductService {

	Optional<Product> findById(Long id);
	
	boolean existsById(Long id);
	
	public Double calculateTaxs(List<Product> products);
	
	public Double calculateAmountOfPurchase(List<Product> products, Double tax);
	
	public Double calculateAmountOfProduct(Product product);
}
