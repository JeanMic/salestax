package com.jean.salestax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jean.salestax.api.dto.PurchaseDTO;
import com.jean.salestax.api.dto.PurchaseReceiptDTO;
import com.jean.salestax.model.enums.ProductOrigin;
import com.jean.salestax.model.enums.TypeProduct;

public class PurchaseServiceImplTest {

	PurchaseServiceImpl service = new PurchaseServiceImpl();

	@Test
	void allProductsNationalEspecialNotHasTax() {
		List<PurchaseDTO> listDTO = new ArrayList<PurchaseDTO>();
		
		PurchaseDTO dto = PurchaseDTO.builder().origin(ProductOrigin.NATIONAL).price(20.00).quantity(1).type(TypeProduct.BOOKS).name("book").build();
		PurchaseDTO dto2 = PurchaseDTO.builder().origin(ProductOrigin.NATIONAL).price(20.00).quantity(1).type(TypeProduct.CARES).name("care").build();
		PurchaseDTO dto3 = PurchaseDTO.builder().origin(ProductOrigin.NATIONAL).price(20.00).quantity(1).type(TypeProduct.FOODS).name("food").build();
		
		listDTO.add(dto);
		listDTO.add(dto2);
		listDTO.add(dto3);
		
		PurchaseReceiptDTO sumary = service.sumary(listDTO);
		
		assertEquals(0.0, sumary.getTax());
	}
	
	@Test
	void anyProductsNationalWithoutClassificationHasTax() {
		List<PurchaseDTO> listDTO = new ArrayList<PurchaseDTO>();
		
		PurchaseDTO dto = PurchaseDTO.builder().origin(ProductOrigin.NATIONAL).price(20.00).quantity(1).type(TypeProduct.OTHER).name("other").build();
		
		listDTO.add(dto);
		
		PurchaseReceiptDTO sumary = service.sumary(listDTO);
		
		assertTrue(sumary.getTax() != 0.0);
	}
	
	@Test
	void anyProductsImportedHasTax() {
		List<PurchaseDTO> listDTO = new ArrayList<PurchaseDTO>();
		
		PurchaseDTO dto = PurchaseDTO.builder().origin(ProductOrigin.IMPORTED).price(20.00).quantity(1).type(TypeProduct.BOOKS).name("book").build();
		PurchaseDTO dto2 = PurchaseDTO.builder().origin(ProductOrigin.IMPORTED).price(20.00).quantity(1).type(TypeProduct.OTHER).name("other").build();
		
		listDTO.add(dto);
		listDTO.add(dto2);
		
		PurchaseReceiptDTO sumary = service.sumary(listDTO);
		PurchaseReceiptDTO sumary2 = service.sumary(listDTO);
		
		assertTrue(sumary.getTax() != 0.0);
		assertTrue(sumary2.getTax() != 0.0);
	}
}
