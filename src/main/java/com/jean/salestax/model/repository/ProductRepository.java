package com.jean.salestax.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jean.salestax.model.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
