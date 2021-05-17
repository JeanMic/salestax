package com.jean.salestax.resource;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jean.salestax.api.dto.PurchaseDTO;
import com.jean.salestax.api.dto.PurchaseReceiptDTO;
import com.jean.salestax.service.PurchaseService;
import com.jean.salestax.validator.PurchaseDTOValidator;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class PurchaseResource {

	private final PurchaseService service;

	@PostMapping("/purchase_summary")
	public ResponseEntity purchaseSummary(@RequestBody List<PurchaseDTO> dtos) {

		PurchaseDTOValidator.validate(dtos, service);
		
		PurchaseReceiptDTO result = service.sumary(dtos);

		return new ResponseEntity(result, HttpStatus.OK);
	}
}
