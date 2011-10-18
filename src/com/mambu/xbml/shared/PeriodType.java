package com.mambu.xbml.shared;

/**
 * How the calculation is performed on the element
 * @author edanilkis
 *
 */
public enum PeriodType {

	/**
	 * A date with a from and a to date
	 */
	DURATION,
	
	/**
	 * A snapshot of the current point in time
	 */
	INSTANT
}
