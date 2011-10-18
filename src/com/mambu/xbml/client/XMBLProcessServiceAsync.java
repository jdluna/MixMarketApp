package com.mambu.xbml.client;

import java.util.LinkedHashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mambu.xbml.shared.ConnectionInfo;
import com.mambu.xbml.shared.XBMLElement;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface XMBLProcessServiceAsync {
	void processRequest(ConnectionInfo conn, String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void generateXML(ConnectionInfo conn, LinkedHashMap<XBMLElement, String> values, AsyncCallback<String> callback);
}
