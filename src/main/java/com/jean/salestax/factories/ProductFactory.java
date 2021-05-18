package com.jean.salestax.factories;

import java.util.ArrayList;
import java.util.List;

import com.jean.salestax.api.dto.PurchaseDTO;
import com.jean.salestax.model.entity.Product;
import com.jean.salestax.model.entity.ProductSpecial;
import com.jean.salestax.model.entity.ProductUnclassified;
import com.jean.salestax.model.enums.Aliquot;
import com.jean.salestax.model.enums.ProductOrigin;
import com.jean.salestax.model.enums.TypeProduct;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductFactory {

	public List<Product> createProduct(List<PurchaseDTO> dtos) {

		List<Product> listProducts = new ArrayList<Product>();

		for (PurchaseDTO dto : dtos) {

			if (isEspecial(dto)) {
				listProducts.add(createEspecialProduct(dto));
			} else {
				listProducts.add(createUnclassifiedProduct(dto));
			}
		}

		return listProducts;
	}

	private boolean isEspecial(PurchaseDTO product) {
		return product.getType() == TypeProduct.OTHER ? false : true;
	}

	private Product createEspecialProduct(PurchaseDTO dto) {

		ProductSpecial productSpecial = new ProductSpecial();
		productSpecial.setOrigin(dto.getOrigin());
		productSpecial.setPrice(dto.getPrice());
		productSpecial.setQuantity(dto.getQuantity());
		productSpecial.setType(dto.getType());
		productSpecial.setName(dto.getName());
		productSpecial.setTaxable(productEspecialIsTaxable(dto));
		productSpecial.setTaxes(getTaxesOfProductEspecial(dto));

		return productSpecial;
	}

	private Product createUnclassifiedProduct(PurchaseDTO dto) {

		ProductUnclassified productUnclassified = new ProductUnclassified();
		productUnclassified.setOrigin(dto.getOrigin());
		productUnclassified.setPrice(dto.getPrice());
		productUnclassified.setQuantity(dto.getQuantity());
		productUnclassified.setType(dto.getType());
		productUnclassified.setName(dto.getName());
		productUnclassified.setTaxable(true);
		productUnclassified.setTaxes(getTaxesOfProductUnclassified(dto));

		return productUnclassified;
	}

	private boolean productEspecialIsTaxable(PurchaseDTO dto) {
		return dto.getOrigin() == ProductOrigin.IMPORTED ? true : false;
	}

	private List<Double> getTaxesOfProductEspecial(PurchaseDTO dto) {
		List<Double> taxes = new ArrayList<Double>();

		if (dto.getOrigin() == ProductOrigin.IMPORTED) {
			taxes.add(Aliquot.IMPORTED.getAliquot());
		}

		return taxes;
	}

	private List<Double> getTaxesOfProductUnclassified(PurchaseDTO dto) {
		List<Double> taxes = new ArrayList<Double>();

		taxes.add(Aliquot.NATIONAL.getAliquot());
		
		if (dto.getOrigin() == ProductOrigin.IMPORTED) {
			taxes.add(Aliquot.IMPORTED.getAliquot());
		}

		return taxes;
	}
}
