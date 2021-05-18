package com.jean.salestax.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseReceiptDTO {

	private Double tax;
	
	private Double amountDue;
	
	private List<PurchaseReceiptItemDTO> purchaseItems;
}

