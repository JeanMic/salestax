package com.jean.salestax.service;

import java.util.List;
import java.util.Optional;

import com.jean.salestax.api.dto.PurchaseDTO;
import com.jean.salestax.api.dto.PurchaseReceiptDTO;
import com.jean.salestax.model.entity.Product;

public interface PurchaseService {

	public PurchaseReceiptDTO sumary(List<PurchaseDTO> dtos);
}
