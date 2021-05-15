package com.jean.salestax.service;

import java.util.Optional;

import com.jean.salestax.model.entity.Product;

public interface ProductService {

	Optional<Product> findById(Long id);
}
