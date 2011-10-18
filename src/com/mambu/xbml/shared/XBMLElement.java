package com.mambu.xbml.shared;

import static com.mambu.xbml.shared.Namespace.*;
import static com.mambu.xbml.shared.ElementType.*;
import static com.mambu.xbml.shared.PeriodType.*;
import static com.mambu.xbml.shared.ElementCategory.*;
/**
 * The XBML Elements which can be added and generated
 * 
 * @author edanilkis
 * 
 */
public enum XBMLElement {

	CURRENT_TAX_ASSETS(IFRS, "CurrentTaxAssets", MONEY, INSTANT, BALANCE_SHEET),
	CURRENT_TAX_EXPENSE_INCOME(IFRS, "CurrentTaxExpenseIncome", MONEY, DURATION, BALANCE_SHEET),
	CURRENT_TAX_LIABILITIES(IFRS, "CurrentTaxLiabilities", MONEY, INSTANT, BALANCE_SHEET),

	
	NUMBER_DEPOSIT_ACCOUNTS(MIX,"NumberOfDepositAccounts", INTEGER, INSTANT, null),
	NUMBER_OUTSANDING_LOANS(MIX,"NumberOfOutstandingLoans", INTEGER, INSTANT, null),
	;

	private ElementCategory category;
	private PeriodType period;
	private ElementType type;
	private String name;
	private String description;
	private Namespace namespace;


	private XBMLElement(Namespace namespace, String name, ElementType type, PeriodType period, ElementCategory category) {
		this.namespace = namespace;
		this.name = name;
		this.type = type;
		this.period = period;
		this.category = category;
	}

	public ElementType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Namespace getNamespace() {
		return namespace;
	}
	
	public String getFullName() {;
		return namespace.getPrefix() + ":" + name;
	}

	public ElementCategory getCategory() {
		return category;
	}

	public PeriodType getPeriod() {
		return period;
	}

}
