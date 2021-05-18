package com.jean.salestax.service.impl;

import java.util.List;
import java.util.Set;

import com.jean.salestax.model.entity.Product;
import com.jean.salestax.model.enums.Aliquot;

public class Calculator {

	public static Double calculateTaxs(List<Product> products) {
		Double amountTaxes = 0.0;

		for (Product product : products) {

			if (product.isTaxable()) {
				amountTaxes += getAmountTaxes(product);
			}
		}

		return roundTax(amountTaxes);
	}
	
	private static Double getAmountTaxes(Product product) {
		return (product.getPrice() * product.getQuantity()) * getTotalAliquotes(product);
	}
	
	public static Double calculateAmountOfProduct(Product product) {
		
		Double totalAliquotes = getTotalAliquotes(product);
		Double productPrice = product.getPrice();
		Integer quantity = product.getQuantity();
		
		Double roundedTax = roundTax(totalAliquotes * (productPrice * quantity));
		Double roundedAmount = roundAmount(productPrice * quantity, roundedTax);
		
		return roundedAmount;
	}
	
	public static Double calculateAmountOfPurchase(List<Product> products, Double tax) {
		
		Double amountDue = 0.0;
		for (Product product : products) {
			amountDue += (product.getPrice() * product.getQuantity());
		}
		
		return roundAmount(amountDue, tax);
	}

	private static Double getTotalAliquotes(Product product) {

		Double totalAliquotes = 0.0;

		List<Double> aliquots = product.getTaxes();

		for (Double aliquote : aliquots) {
			totalAliquotes += aliquote;
		}
		
		return totalAliquotes;
	}
	
	private static Double roundTax(Double amountTaxes) {
		double roundedTaxes = Math.round(amountTaxes * 20.0) / 20.0;
		return (Double) roundedTaxes;
	}
	
	private static Double roundAmount(Double amount, Double tax) {
		double amountRounded = Math.round((amount + tax)  * 100.0) / 100.0;
		return (Double) amountRounded;
	}
}

