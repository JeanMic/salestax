package com.jean.salestax.validator;

import java.util.List;

import com.jean.salestax.api.dto.PurchaseDTO;
import com.jean.salestax.exception.ImputException;
import com.jean.salestax.model.enums.ProductOrigin;
import com.jean.salestax.model.enums.TypeProduct;
import com.jean.salestax.service.PurchaseService;

public class PurchaseDTOValidator {

	public static void validate(List<PurchaseDTO> dtos, PurchaseService service) {
		listValidate(dtos);
		listItemsNotNullValidate(dtos);
		listItemsPositiveValuesValidate(dtos);
	}
	
	private static void listValidate(List<PurchaseDTO> dtos) {
		
		if (dtos.isEmpty())
			throwException("List cannot be empty");
	}
	
	private static void listItemsNotNullValidate(List<PurchaseDTO> dtos) {
		
		for (PurchaseDTO dto : dtos) {
			
			if (dto.getOrigin() == null)
				throwException("Origin cannot be null");

			if (dto.getPrice() == null)
				throwException("Price cannot be null");
			
			if (dto.getQuantity() == null)
				throwException("Quantity cannot be null");

			if (dto.getType() == null)
				throwException("Type cannot be null");
			
			if (dto.getName() == null)
				throwException("Name cannot be null");
		}
	
	}
	
	private static void listItemsPositiveValuesValidate(List<PurchaseDTO> dtos) {
		
		for (PurchaseDTO dto : dtos) {
			
			if (dto.getPrice() <= 0)
				throwException("Price cannot be negative or zero");

			if (dto.getQuantity() <= 0)
				throwException("Quantity cannot be negative or zero");
		}
	
	}
	
	private static void throwException(String message) {
		throw new ImputException(message);
	}
}
