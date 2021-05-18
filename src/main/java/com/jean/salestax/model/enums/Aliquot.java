package com.jean.salestax.model.enums;

public enum Aliquot {

	IMPORTED(0.05),
	NATIONAL(0.1);

	private double aliquot;

	Aliquot(double tax) {
		this.aliquot = tax;
	}
	
	public double getAliquot() {
		return aliquot;
	}
}
