package com.jean.salestax.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jean.salestax.model.entity.Product;
import com.jean.salestax.service.Calculator;

@Service
public class CalculatorImpl implements Calculator {

	@Override
	public Double calculateTaxs(List<Product> products) {
		Double amountTaxes = 0.0;

		for (Product product : products) {

			if (product.isTaxed()) {
				amountTaxes += getAmountTaxes(product);
			}
		}

		return roundTax(amountTaxes);
	}
	
	private Double getAmountTaxes(Product product) {
		return (product.getPrice() * product.getQuantity()) * getTotalAliquotes(product);
	}
	
	@Override
	public Double calculateAmountOfProduct(Product product) {
		
		Double totalAliquotes = getTotalAliquotes(product);
		Double productPrice = product.getPrice();
		Integer quantity = product.getQuantity();
		
		Double roundedTax = roundTax(totalAliquotes * (productPrice * quantity));
		Double roundedAmount = roundAmount(productPrice * quantity, roundedTax);
		
		return roundedAmount;
	}
	
	@Override
	public Double calculateAmountOfPurchase(List<Product> products, Double tax) {
		
		Double amountDue = 0.0;
		for (Product product : products) {
			amountDue += (product.getPrice() * product.getQuantity());
		}
		
		return roundAmount(amountDue, tax);
	}

	private Double getTotalAliquotes(Product product) {

		Double totalAliquotes = 0.0;

		List<Double> aliquots = product.getTaxes();

		for (Double aliquote : aliquots) {
			totalAliquotes += aliquote;
		}
		
		return totalAliquotes;
	}
	
	private Double roundTax(Double amountTaxes) {
		double roundedTaxes = Math.round(amountTaxes * 20.0) / 20.0;
		return (Double) roundedTaxes;
	}
	
	private Double roundAmount(Double amount, Double tax) {
		double amountRounded = Math.round((amount + tax)  * 100.0) / 100.0;
		return (Double) amountRounded;
	}
}

