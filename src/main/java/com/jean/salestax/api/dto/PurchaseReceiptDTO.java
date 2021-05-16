package com.jean.salestax.api.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseReceiptDTO {

	private Double tax;
	
	private Double amountDue;
	
	private ArrayList<PurchaseReceiptItemDTO> purchaseItems;
}

