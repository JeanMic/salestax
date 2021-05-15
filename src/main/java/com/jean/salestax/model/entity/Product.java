package com.jean.salestax.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.jean.salestax.model.enums.ProductOrigin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "products", schema = "sales")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "origin")
	@Enumerated(value = EnumType.STRING)
	private ProductOrigin origin;
	
	@ManyToOne
	@JoinColumn(name = "id_type")
	private TypeOfProduct type;
	
	@Column(name = "price")
	private String price;
}
