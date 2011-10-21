package com.mambu.xbrl.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class XBRLGenerationParamaters implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String domain;
	public String username;
	public String password;
	
	/**
	 * @gwt.typeArgs <com.mambu.xbrl.shared.XBRLElement,java.lang.String>
	 */
	public LinkedHashMap<XBRLElement, String> values = new LinkedHashMap<XBRLElement, String>();
	
	/**
	 * @gwt.typeArgs <com.mambu.xbrl.shared.Duration>
	 */
	public ArrayList<Duration> durations = new ArrayList<Duration>();
	
	public XBRLGenerationParamaters() {
		
	}
		   
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LinkedHashMap<XBRLElement, String> getValues() {
		return values;
	}

	public void setValues(LinkedHashMap<XBRLElement, String> values) {
		this.values = values;
	}

	public ArrayList<Duration> getDurations() {
		return durations;
	}

	public void setDurations(ArrayList<Duration> durations) {
		this.durations = durations;
	}

}
