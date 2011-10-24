package com.mambu.xbrl.shared;

import java.util.HashMap;

import com.mambu.intelligence.shared.model.Intelligence.Indicator;

/**
 * Map of Mambu indicators to XBRL Elements
 * 
 * @author edanilkis
 * 
 */
public class IndicatorElementMap {

	private static HashMap<Indicator, XBRLElement> MAP = new HashMap<Indicator, XBRLElement>();

	static {
		MAP.put(Indicator.NUM_CLIENTS, XBRLElement.NUMBER_OF_ACTIVE_CLIENTS_ALL_SERVICES);
		MAP.put(Indicator.NUM_INDIVIDUAL_BORROWERS, XBRLElement.NUMBER_OF_ACTIVE_BORROWERS);
		MAP.put(Indicator.NUM_INDIVIDUAL_SAVERS, XBRLElement.NUMBER_OF_DEPOSITORS);
		MAP.put(Indicator.NUM_CREDIT_OFFICERS, XBRLElement.NUMBER_OF_LOAN_OFFICERS);
		MAP.put(Indicator.NUM_ACTIVE_SAVINGS_ACCOUNTS, XBRLElement.NUMBER_OF_DEPOSIT_ACCOUNTS);
		MAP.put(Indicator.NUM_LOANS_OUTSTANDING, XBRLElement.NUMBER_OF_OUTSTANDING_LOANS);
	}

	public static HashMap<Indicator, XBRLElement> getMap() {
		return MAP;
	}

}
