package com.mambu.xbml.shared;

/**
 * Summary of the namesapces used in the XBML report
 * 
 * @author edanilkis
 * 
 */
public enum Namespace {

	IFRS("ifrs", "http://xbrl.iasb.org/taxonomy/2009-04-01/ifrs"),

	ISO4217("iso4217", "http://www.xbrl.org/2003/iso4217"),

	MIX("mix", "http://www.themix.org/int/fr/ifrs/basi/YYYY-MM-DD/mx-cor"),

	XBRLDI("xbrldi", "http://xbrl.org/2006/xbrldi"),

	XBRLI("xbrli", "http://www.xbrl.org/2003/instance");

	private String prefix;
	private String url;

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private Namespace(String prefix, String url) {
		this.prefix = prefix;
		this.url = url;
	}

}
