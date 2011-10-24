package com.mambu.xbrl.shared;

/**
 * A category for various XBRL elements used for organization purposes
 * 
 * @author edanilkis
 * 
 */
public enum ElementCategory {
	
	GENERAL("General Info"),

	BALANCE_SHEET("Balance Sheet"),
	
	INCOME("Incomes"),
	
	EXPENSE("Expenses"),

	;

	private String name;

	private ElementCategory(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
