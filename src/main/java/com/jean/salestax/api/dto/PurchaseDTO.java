package com.jean.salestax.api.dto;

import com.jean.salestax.model.enums.ProductOrigin;
import com.jean.salestax.model.enums.TypeProduct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDTO {

	private ProductOrigin origin;
	private Double price; 
	private Integer quantity;
	private TypeProduct type;
	private String name;
}

