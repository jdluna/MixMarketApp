package com.mambu.xbrl.client;

import java.util.LinkedHashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mambu.xbrl.shared.RequestSetttings;
import com.mambu.xbrl.shared.XBRLElement;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface XBRLProcessServiceAsync {
	void processRequest(RequestSetttings conn, String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void generateXML(RequestSetttings conn, LinkedHashMap<XBRLElement, String> values, AsyncCallback<String> callback);
}
