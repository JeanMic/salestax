package com.jean.salestax.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jean.salestax.model.entity.Product;
import com.jean.salestax.model.repository.ProductRepository;
import com.jean.salestax.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService
{
	private ProductRepository repository;
	
	@Autowired
	public ProductServiceImpl(ProductRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Optional<Product> findById(Long id) {
		return repository.findById(id);
	}
	
	@Override
	public Double calculateTaxs(List<Product> products) {
		
		return Calculator.calculateTaxs(products);
	}

	@Override
	public Double calculateAmountOfPurchase(List<Product> products, Double tax) {
		return Calculator.calculateAmountOfPurchase(products, tax);
	}

	@Override
	public Double calculateAmountOfProduct(Product product) {
		return Calculator.calculateAmountOfProduct(product);
	}
}
