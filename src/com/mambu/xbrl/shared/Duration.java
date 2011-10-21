package com.mambu.xbrl.shared;

import java.io.Serializable;
import java.util.Date;

/**
 * Time period duration
 * @author edanilkis
 *
 */
public class Duration implements Serializable {

	private static final long serialVersionUID = 1L;
	public Date from;
	public Date to;
	
	public Duration() {
		
	}
	
	public Duration(Date from, Date to) {
		this.from=from;
		this.to = to;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public boolean isDefined() {
		return from != null && to != null;
	}
}
