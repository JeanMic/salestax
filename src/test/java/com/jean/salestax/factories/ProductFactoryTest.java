package com.jean.salestax.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.jean.salestax.api.dto.PurchaseDTO;
import com.jean.salestax.model.entity.Product;
import com.jean.salestax.model.enums.ProductOrigin;
import com.jean.salestax.model.enums.TypeProduct;

@TestInstance(Lifecycle.PER_CLASS)
public class ProductFactoryTest {
	
	ProductFactory productFactory;
	
	@BeforeAll
	public void setup() {
		productFactory = new ProductFactory();
	}
	
	@Test
	void returnOfFactoryIsListOfProducts() {
		List<PurchaseDTO> listDTO = new ArrayList<PurchaseDTO>();
		
		PurchaseDTO dto = PurchaseDTO.builder().origin(ProductOrigin.IMPORTED).price(20.00).quantity(1).type(TypeProduct.OTHER).name("other").build();
		listDTO.add(dto);
		
		List<Product> createProduct = productFactory.createProduct(listDTO);
		
		for (Product product : createProduct) {
			Assertions.assertThat(product).isInstanceOf(Product.class);
		}
	}
	
	@Test
	void unratedImportedProductsMustContainFifteenTax() {
		List<PurchaseDTO> listDTO = new ArrayList<PurchaseDTO>();
		
		PurchaseDTO dto = PurchaseDTO.builder().origin(ProductOrigin.IMPORTED).price(20.00).quantity(1).type(TypeProduct.OTHER).name("other").build();
		listDTO.add(dto);
		
		List<Product> createProduct = productFactory.createProduct(listDTO);
		
		Product product = createProduct.get(0);
		
		Double totalTax = extractTaxFrom(product);
		Double totalTaxRounded = roud(totalTax);
		
		assertEquals(0.15, totalTaxRounded);
	}
	
	@Test
	void unratedNationalProductsMustContainTenTax() {
		List<PurchaseDTO> listDTO = new ArrayList<PurchaseDTO>();
		
		PurchaseDTO dto = PurchaseDTO.builder().origin(ProductOrigin.NATIONAL).price(20.00).quantity(1).type(TypeProduct.OTHER).name("other").build();
		listDTO.add(dto);
		
		List<Product> createProduct = productFactory.createProduct(listDTO);
		
		Product product = createProduct.get(0);
		
		Double totalTax = extractTaxFrom(product);
		Double totalTaxRounded = roud(totalTax);
		
		assertEquals(0.10, totalTaxRounded);
	}
	
	@Test
	void anyEspecialNationalProductsNotContainTax() {
		List<PurchaseDTO> listDTO = new ArrayList<PurchaseDTO>();
		
		PurchaseDTO dto = PurchaseDTO.builder().origin(ProductOrigin.NATIONAL).price(20.00).quantity(1).type(TypeProduct.BOOKS).name("books").build();
		PurchaseDTO dto2 = PurchaseDTO.builder().origin(ProductOrigin.NATIONAL).price(20.00).quantity(1).type(TypeProduct.CARES).name("caresr").build();
		PurchaseDTO dto3 = PurchaseDTO.builder().origin(ProductOrigin.NATIONAL).price(20.00).quantity(1).type(TypeProduct.FOODS).name("foods").build();
		
		listDTO.add(dto);
		listDTO.add(dto2);
		listDTO.add(dto3);
		
		List<Product> createProducts = productFactory.createProduct(listDTO);
		
		for(Product product: createProducts) {
			Double totalTax = extractTaxFrom(product);
			Double totalTaxRounded = roud(totalTax);
			
			assertEquals(0.0, totalTaxRounded);
		}
	}
	
	@Test
	void anyEspecialInternationalProductsContainFivePercentOfTax() {
		List<PurchaseDTO> listDTO = new ArrayList<PurchaseDTO>();
		
		PurchaseDTO dto = PurchaseDTO.builder().origin(ProductOrigin.IMPORTED).price(20.00).quantity(1).type(TypeProduct.BOOKS).name("books").build();
		PurchaseDTO dto2 = PurchaseDTO.builder().origin(ProductOrigin.IMPORTED).price(20.00).quantity(1).type(TypeProduct.CARES).name("caresr").build();
		PurchaseDTO dto3 = PurchaseDTO.builder().origin(ProductOrigin.IMPORTED).price(20.00).quantity(1).type(TypeProduct.FOODS).name("foods").build();
		
		listDTO.add(dto);
		listDTO.add(dto2);
		listDTO.add(dto3);
		
		List<Product> createProducts = productFactory.createProduct(listDTO);
		
		for(Product product: createProducts) {
			Double totalTax = extractTaxFrom(product);
			Double totalTaxRounded = roud(totalTax);
			
			assertEquals(0.05, totalTaxRounded);
		}
	}
	
	private Double roud(Double number) {
		double numberRounded = Math.round(number  * 100.0) / 100.0;
		return  (Double) numberRounded;
	}
	
	private Double extractTaxFrom(Product product) {
		List<Double> taxes = product.getTaxes();
		
		Double totalTax = 0.0;
		
		for(Double tax: taxes ) {
			totalTax += tax;
		}
		
		return totalTax;
	}
}
