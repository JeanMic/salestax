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
		
		List<Product> products = (new ProductFactory()).createProduct(dtos);

		List<PurchaseReceiptItemDTO> receiptItems = getPurchaseReceiptItems(products);
		PurchaseReceiptDTO purchaseReceipt = buildPurchaseReceipt(products, receiptItems);

		return purchaseReceipt;
	}
	
	private PurchaseReceiptItemDTO buildPurchaseReceiptItem(Product product) {
		
		Double calculatedPrice = calculator.calculateAmountOfProduct(product);
		
		return PurchaseReceiptItemDTO.builder()
				.quantity(product.getQuantity())
				.description(product.getName())
				.calculatedPrice(calculatedPrice)
				.build();
	}
	
	private PurchaseReceiptDTO buildPurchaseReceipt(List<Product> listProducts, List<PurchaseReceiptItemDTO> listItens) {
		
		Double tax = calculator.calculateTaxs(listProducts);
		Double amountDue = calculator.calculateAmountOfPurchase(listProducts, tax);
		
		return PurchaseReceiptDTO.builder()
				.tax(tax)
				.amountDue(amountDue)
				.purchaseItems(listItens)
				.build();
	}
	
	private List<PurchaseReceiptItemDTO> getPurchaseReceiptItems(List<Product> products){
		List<PurchaseReceiptItemDTO> listItens = new ArrayList<>();

		for (Product product : products) {
			listItens.add(buildPurchaseReceiptItem(product));
		}
		return listItens;
	}
}

