package com.mambu.xbml.shared;

/**
 * A category for various XBML elements used for organization purposes
 * 
 * @author edanilkis
 * 
 */
public enum ElementCategory {
	
	GENERAL("General Info"),

	BALANCE_SHEET("Balance Sheet"),

	CASH("Cash"),

	LOAN_PORTFOLO("Loan Portfolio"),

	INCOME_STATEMENT("Income Statement")

	;

	private String name;

	private ElementCategory(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
