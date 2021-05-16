package com.jean.salestax.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jean.salestax.api.dto.PurchaseDTO;
import com.jean.salestax.api.dto.PurchaseReceiptDTO;
import com.jean.salestax.api.dto.PurchaseReceiptItemDTO;
import com.jean.salestax.model.entity.Product;
import com.jean.salestax.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductResource {

	private final ProductService service;

	@GetMapping("{id}")
	public ResponseEntity obterSaldo(@PathVariable("id") Long id) {

//		System.out.println();
		Optional<Product> produto = service.findById(id);

		return ResponseEntity.ok(produto);
	}

	@PostMapping("/purchase_summary")
	public ResponseEntity purchaseSummary(@RequestBody List<PurchaseDTO> dtos) {

		List<Product> listProducts = new ArrayList<Product>();
		ArrayList<PurchaseReceiptItemDTO> listItens = new ArrayList<>();

		for (PurchaseDTO dto : dtos) {
			Product product = (service.findById(dto.getProductId())).get();

			listProducts.add(product);
			listItens.add(buildPurchaseReceiptItem(dto, product));
		}

		Double tax = service.calculateTaxs(listProducts);

		PurchaseReceiptDTO result = buildPurchaseReceipt(tax, listProducts, listItens);

		return new ResponseEntity(result, HttpStatus.ACCEPTED);
	}
	
	private PurchaseReceiptItemDTO buildPurchaseReceiptItem(PurchaseDTO dto, Product product) {
		return PurchaseReceiptItemDTO.builder().quantity(dto.getQuantity())
				.description(product.getDescription()).calculatedPrice(service.calculateAmountOfProduct(product))
				.build();
	}
	
	private PurchaseReceiptDTO buildPurchaseReceipt(Double tax, List<Product> listProducts, ArrayList<PurchaseReceiptItemDTO> listItens) {
		return PurchaseReceiptDTO.builder().tax(tax)
				.amountDue(service.calculateAmountOfPurchase(listProducts, tax)).purchaseItems(listItens).build();
	}
}
