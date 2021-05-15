package com.jean.salestax.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jean.salestax.model.enums.TypeProduct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "product_types", schema = "sales")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeOfProduct {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "type")
	@Enumerated(value = EnumType.STRING)
	private TypeProduct type;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "taxable")
	private Boolean taxable;
}
