package com.jean.salestax.service;

import java.util.List;

import com.jean.salestax.model.entity.Product;

public interface Calculator {

	public Double calculateTaxs(List<Product> products);
	public Double calculateAmountOfProduct(Product product);
	public Double calculateAmountOfPurchase(List<Product> products, Double tax);
	
}
