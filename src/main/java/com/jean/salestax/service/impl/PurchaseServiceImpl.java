package com.jean.salestax.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jean.salestax.api.dto.PurchaseDTO;
import com.jean.salestax.api.dto.PurchaseReceiptDTO;
import com.jean.salestax.api.dto.PurchaseReceiptItemDTO;
import com.jean.salestax.model.entity.Product;
import com.jean.salestax.model.repository.ProductRepository;
import com.jean.salestax.service.PurchaseService;

@Service
public class PurchaseServiceImpl implements PurchaseService
{
	private ProductRepository repository;
	
	@Autowired
	public PurchaseServiceImpl(ProductRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public boolean existsById(Long id) {
		return repository.existsById(id);
	}
	
	@Override
	public PurchaseReceiptDTO sumary(List<PurchaseDTO> dtos) {
		
		List<Product> listProducts = new ArrayList<Product>();
		ArrayList<PurchaseReceiptItemDTO> listItens = new ArrayList<>();

		for (PurchaseDTO dto : dtos) {
			
			Product product = (repository.findById(dto.getProductId())).get();

			listProducts.add(product);
			listItens.add(buildPurchaseReceiptItem(dto, product));
		}

		Double tax = Calculator.calculateTaxs(listProducts);

		PurchaseReceiptDTO result = buildPurchaseReceipt(tax, listProducts, listItens);
		return result;
		
	}
	
	private PurchaseReceiptItemDTO buildPurchaseReceiptItem(PurchaseDTO dto, Product product) {
		
		Double calculatedPrice = Calculator.calculateAmountOfProduct(product);
		
		return PurchaseReceiptItemDTO.builder()
				.quantity(dto.getQuantity())
				.description(product.getDescription())
				.calculatedPrice(calculatedPrice)
				.build();
	}
	
	private PurchaseReceiptDTO buildPurchaseReceipt(Double tax, List<Product> listProducts, ArrayList<PurchaseReceiptItemDTO> listItens) {
		
		Double amountDue = Calculator.calculateAmountOfPurchase(listProducts, tax);
		
		return PurchaseReceiptDTO.builder()
				.tax(tax)
				.amountDue(amountDue)
				.purchaseItems(listItens)
				.build();
	}
}

