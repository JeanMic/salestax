package com.jean.salestax.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
