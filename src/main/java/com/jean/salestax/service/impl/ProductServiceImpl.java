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
import com.jean.salestax.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService
{
	private ProductRepository repository;
	
	@Autowired
	public ProductServiceImpl(ProductRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public Optional<Product> findById(Long id) {
		return repository.findById(id);
	}
	
	@Override
	public boolean existsById(Long id) {
		return repository.existsById(id);
	}
	
	@Override
	public Double calculateTaxs(List<Product> products) {
		return Calculator.calculateTaxs(products);
	}

	@Override
	public Double calculateAmountOfPurchase(List<Product> products, Double tax) {
		return Calculator.calculateAmountOfPurchase(products, tax);
	}

	@Override
	public Double calculateAmountOfProduct(Product product) {
		return Calculator.calculateAmountOfProduct(product);
	}
	
	@Override
	public PurchaseReceiptDTO sumary(List<PurchaseDTO> dtos) {
		
		List<Product> listProducts = new ArrayList<Product>();
		ArrayList<PurchaseReceiptItemDTO> listItens = new ArrayList<>();

		for (PurchaseDTO dto : dtos) {
			
			Product product = (findById(dto.getProductId())).get();

			listProducts.add(product);
			listItens.add(buildPurchaseReceiptItem(dto, product));
		}

		Double tax = calculateTaxs(listProducts);

		PurchaseReceiptDTO result = buildPurchaseReceipt(tax, listProducts, listItens);
		return result;
		
	}
	
	private PurchaseReceiptItemDTO buildPurchaseReceiptItem(PurchaseDTO dto, Product product) {
		return PurchaseReceiptItemDTO.builder().quantity(dto.getQuantity())
				.description(product.getDescription()).calculatedPrice(calculateAmountOfProduct(product))
				.build();
	}
	
	private PurchaseReceiptDTO buildPurchaseReceipt(Double tax, List<Product> listProducts, ArrayList<PurchaseReceiptItemDTO> listItens) {
		return PurchaseReceiptDTO.builder().tax(tax)
				.amountDue(calculateAmountOfPurchase(listProducts, tax)).purchaseItems(listItens).build();
	}
}

