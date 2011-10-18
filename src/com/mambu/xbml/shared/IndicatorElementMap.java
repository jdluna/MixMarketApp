package com.mambu.xbml.shared;

import java.util.HashMap;

import com.mambu.intelligence.shared.model.Intelligence.Indicator;

/**
 * Map of Mambu indicators to XBML Elements
 * 
 * @author edanilkis
 * 
 */
public class IndicatorElementMap {

	private static HashMap<Indicator, XBMLElement> MAP = new HashMap<Indicator, XBMLElement>();

	static {
		MAP.put(Indicator.NUM_ACTIVE_SAVINGS_ACCOUNTS, XBMLElement.NUMBER_DEPOSIT_ACCOUNTS);
		MAP.put(Indicator.NUM_LOANS_OUTSTANDING, XBMLElement.NUMBER_OUTSANDING_LOANS);
	}

	public static HashMap<Indicator, XBMLElement> getMap() {
		return MAP;
	}

}
