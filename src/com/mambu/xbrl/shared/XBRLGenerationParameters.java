package com.mambu.xbrl.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(detachable = "true")
public class XBRLGenerationParameters implements Serializable {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	private String encodedKey;

	private static final long serialVersionUID = 1L;

	@NotPersistent
	public String domain;

	@NotPersistent
	public String username;

	@NotPersistent
	public String password;

	/**
	 * @gwt.typeArgs <com.mambu.xbrl.shared.Duration>
	 */
	@NotPersistent
	public ArrayList<Duration> durations = new ArrayList<Duration>();
	
	/**
	 * @gwt.typeArgs <com.mambu.xbrl.shared.XBRLElement,java.lang.String>
	 */
	@Persistent(serialized = "true", defaultFetchGroup = "true")
	public HashMap<XBRLElement, String> values = new HashMap<XBRLElement, String>();

	public XBRLGenerationParameters() {

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

	public HashMap<XBRLElement, String> getValues() {
		return values;
	}

	public void setValues(HashMap<XBRLElement, String> values) {
		this.values = values;
	}

	public ArrayList<Duration> getDurations() {
		return durations;
	}

	public void setDurations(ArrayList<Duration> durations) {
		this.durations = durations;
	}

	public void setEncodedKey(String encodedKey) {
		this.encodedKey = encodedKey;
	}

	public String getEncodedKey() {
		return encodedKey;
	}

}