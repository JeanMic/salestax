package com.jean.salestax.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

	@GetMapping("/testeRetorno")
	public ResponseEntity obterCompra() {

		return ResponseEntity.ok(convert());
	}

	private PurchaseReceiptDTO convert() {

		PurchaseReceiptItemDTO primeiro = PurchaseReceiptItemDTO.builder()
				.quantity(1)
				.nameProduct("jean vai dormir!!")
				.calculatedPrice(15.90)
				.build();

		PurchaseReceiptItemDTO segundo = PurchaseReceiptItemDTO.builder()
				.quantity(1)
				.nameProduct("jean vai dormir ja e tarde!!")
				.calculatedPrice(20.90)
				.build();

		ArrayList<PurchaseReceiptItemDTO> lista = new ArrayList<>();

		lista.add(primeiro);
		lista.add(segundo);
		
		return PurchaseReceiptDTO.builder()
				.tax(7.65)
				.amountDue(65.15)
				.purchaseItems(lista)
				.build();
	}
	
	@PostMapping("/purchase_summary")
	public ResponseEntity purchaseSummary(@RequestBody List<PurchaseDTO> dto) {
	
		try {
			return new ResponseEntity(dto, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
