package com.mambu.xbml.shared;

/**
 * The XBML Elements which can be added and generated
 * 
 * @author edanilkis
 * 
 */
public enum XBMLElement {

	ADMINISTRATIVE_EXPENSE(Namespace.IFRS, "AdministrativeExpense", ElementType.MONEY),

	DEPOSISTS(Namespace.IFRS, "Deposits", ElementType.MONEY, "The total value of funds placed in an account with an MFI that are payable to a depositor. This item includes any current, checking, or savings accounts that are payable on demand. It also includes time deposits which have a fixed maturity date.")

	;

	private ElementType type;
	private String name;
	private String description;
	private Namespace namespace;

	private XBMLElement(Namespace namespace, String name, ElementType type, String description) {
		this.type = type;
		this.name = name;
		this.namespace = namespace;
		this.description = description;

	}

	private XBMLElement(Namespace namespace, String name, ElementType type) {
		this.type = type;
		this.name = name;
		this.namespace = namespace;
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

}
