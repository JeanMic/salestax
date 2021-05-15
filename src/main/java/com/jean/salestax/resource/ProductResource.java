package com.jean.salestax.resource;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jean.salestax.model.entity.Product;
import com.jean.salestax.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductResource {

	private final ProductService service;
	
	@GetMapping("{id}")
	public ResponseEntity obterSaldo(@PathVariable("id") Long id) {
		
//		System.out.println();
		Optional<Product> produto = service.findById(id);
		
		return ResponseEntity.ok(produto);
	}
	
}
