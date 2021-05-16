package com.jean.salestax.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseReceiptItemDTO {

	private Integer quantity;
	
	private String description;
	
	private Double calculatedPrice;
}
