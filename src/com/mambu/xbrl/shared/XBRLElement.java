package com.mambu.xbrl.shared;

import static com.mambu.xbrl.shared.ElementCategory.*;
import static com.mambu.xbrl.shared.ElementType.*;
import static com.mambu.xbrl.shared.Namespace.*;
import static com.mambu.xbrl.shared.PeriodType.*;

import java.io.Serializable;
/**
 * The XBRL Elements which can be added and generated
 * 
 * @author edanilkis
 * 
 */
public enum XBRLElement implements Serializable {

	CURRENT_TAX_ASSETS(IFRS, "CurrentTaxAssets", MONEY, INSTANT, BALANCE_SHEET),
	CURRENT_TAX_EXPENSE_INCOME(IFRS, "CurrentTaxExpenseIncome", MONEY, DURATION, BALANCE_SHEET),
	CURRENT_TAX_LIABILITIES(IFRS, "CurrentTaxLiabilities", MONEY, INSTANT, BALANCE_SHEET),

	
	//string
	NAME_OF_ENTITY(IFRS,"NameOfReportingEntityOrOtherMeansOfIdentification", STRING, null, GENERAL),
	LEGAL_ENTITY_FORM(IFRS, "LegalFormOfEntity", STRING, null, GENERAL),
	POINTS_OF_SERVICE(IFRS, "NumberOfPointsOfService", INTEGER, INSTANT, GENERAL),

	
	NUMBER_DEPOSIT_ACCOUNTS(MIX,"NumberOfDepositAccounts", INTEGER, INSTANT, null),
	NUMBER_OUTSANDING_LOANS(MIX,"NumberOfOutstandingLoans", INTEGER, INSTANT, null),
	;

	private ElementCategory category;
	private PeriodType period;
	private ElementType type;
	private String name;
	private String description;
	private Namespace namespace;


	private XBRLElement(Namespace namespace, String name, ElementType type, PeriodType period, ElementCategory category) {
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
