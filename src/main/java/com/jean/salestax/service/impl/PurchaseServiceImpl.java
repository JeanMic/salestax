package com.jean.salestax.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jean.salestax.api.dto.PurchaseDTO;
import com.jean.salestax.api.dto.PurchaseReceiptDTO;
import com.jean.salestax.api.dto.PurchaseReceiptItemDTO;
import com.jean.salestax.factories.ProductFactory;
import com.jean.salestax.model.entity.Product;
import com.jean.salestax.service.Calculator;
import com.jean.salestax.service.PurchaseService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService
{
	private final Calculator calculator;
	
	@Override
	public PurchaseReceiptDTO sumary(List<PurchaseDTO> dtos) {
		
		ProductFactory productFactory = new ProductFactory();
		List<Product> products = productFactory.createProduct(dtos);
		ArrayList<PurchaseReceiptItemDTO> listItens = new ArrayList<>();

		for (Product product : products) {
			listItens.add(buildPurchaseReceiptItem(product));
		}

		Double tax = calculator.calculateTaxs(products);

		PurchaseReceiptDTO result = buildPurchaseReceipt(tax, products, listItens);
		return result;
	}
	
	private PurchaseReceiptItemDTO buildPurchaseReceiptItem(Product product) {
		
		Double calculatedPrice = calculator.calculateAmountOfProduct(product);
		
		return PurchaseReceiptItemDTO.builder()
				.quantity(product.getQuantity())
				.description(product.getName())
				.calculatedPrice(calculatedPrice)
				.build();
	}
	
	private PurchaseReceiptDTO buildPurchaseReceipt(Double tax, List<Product> listProducts, ArrayList<PurchaseReceiptItemDTO> listItens) {
		
		Double amountDue = calculator.calculateAmountOfPurchase(listProducts, tax);
		
		return PurchaseReceiptDTO.builder()
				.tax(tax)
				.amountDue(amountDue)
				.purchaseItems(listItens)
				.build();
	}
}

