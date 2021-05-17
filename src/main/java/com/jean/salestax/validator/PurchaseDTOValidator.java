package com.jean.salestax.validator;

import java.util.List;

import com.jean.salestax.api.dto.PurchaseDTO;
import com.jean.salestax.exception.ImputException;
import com.jean.salestax.service.ProductService;

public class PurchaseDTOValidator {

	public static void validate(List<PurchaseDTO> dtos, ProductService service) {
		listValidate(dtos);
		listItemsNotNullValidate(dtos);
		listItemsPositiveValuesValidate(dtos);
		listItemsExistsOnBdValidate(dtos, service);		
	}
	
	private static void listValidate(List<PurchaseDTO> dtos) {
		
		if (dtos.isEmpty())
			throwException("List cannot be empty");
	}
	
	private static void listItemsNotNullValidate(List<PurchaseDTO> dtos) {
		
		for (PurchaseDTO dto : dtos) {
			
			if (dto.getProductId() == null)
				throwException("ProductId cannot be null");

			if (dto.getQuantity() == null)
				throwException("Quantity cannot be null");
		}
	
	}
	
	private static void listItemsPositiveValuesValidate(List<PurchaseDTO> dtos) {
		
		for (PurchaseDTO dto : dtos) {
			
			if (dto.getProductId() <= 0)
				throwException("ProductId cannot be negative or zero");

			if (dto.getQuantity() <= 0)
				throwException("Quantity cannot be negative or zero");
		}
	
	}
	
	private static void listItemsExistsOnBdValidate(List<PurchaseDTO> dtos, ProductService service) {
		
		for (PurchaseDTO dto : dtos) {
			if (!service.existsById(dto.getProductId()))
				throwException("Product not found");
		}
	}
	
	private static void throwException(String message) {
		throw new ImputException(message);
	}
}
