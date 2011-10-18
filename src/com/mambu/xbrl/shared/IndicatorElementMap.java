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
		MAP.put(Indicator.NUM_ACTIVE_SAVINGS_ACCOUNTS, XBRLElement.NUMBER_DEPOSIT_ACCOUNTS);
		MAP.put(Indicator.NUM_LOANS_OUTSTANDING, XBRLElement.NUMBER_OUTSANDING_LOANS);
	}

	public static HashMap<Indicator, XBRLElement> getMap() {
		return MAP;
	}

}
