package com.mambu.xbrl.shared;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.google.web.bindery.requestfactory.shared.ProxyForName;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

@ProxyForName(value = "com.mambu.xbrl.shared.XBRLGenerationParamaters", locator = "com.mambu.xbrl.shared.XBRLGenerationParamatersLocator")
public interface XBRLGenerationParamatersProxy extends ValueProxy {

	String getDomain();

	void setDomain(String domain);

	String getUsername();

	void setUsername(String username);

	String getPassword();

	void setPassword(String password);

	LinkedHashMap<XBRLElement, String> getValues();

	void setValues(LinkedHashMap<XBRLElement, String> values);

	ArrayList<Duration> getDurations();

	void setDurations(ArrayList<Duration> durations);

	void setKey(String key);

	String getKey();

}
