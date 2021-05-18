package com.jean.salestax.model.entity;

import java.util.List;

import com.jean.salestax.model.enums.ProductOrigin;
import com.jean.salestax.model.enums.TypeProduct;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

	private ProductOrigin origin;
	private Double price; 
	private Integer quantity;
	private TypeProduct type;
	private String name;
	private boolean taxed;
	private List<Double> taxes;
}
