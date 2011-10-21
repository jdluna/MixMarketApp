package com.mambu.xbrl.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mambu.xbrl.shared.XBRLGenerationParamaters;
import com.mambu.xbrl.shared.XBRLElement;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface XBRLProcessServiceAsync {
	void processRequest(XBRLGenerationParamaters conn, XBRLElement element, String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void generateXML(XBRLGenerationParamaters conn, AsyncCallback<String> callback);
}
