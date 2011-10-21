package com.mambu.xbrl.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mambu.xbrl.shared.XBRLGenerationParameters;
import com.mambu.xbrl.shared.XBRLElement;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface XBRLProcessServiceAsync {
	void processRequest(XBRLGenerationParameters conn, XBRLElement element, String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void generateXML(XBRLGenerationParameters conn, AsyncCallback<String> callback);

	void storeParams(XBRLGenerationParameters params, AsyncCallback<String> callback);

	void getParams(String key, AsyncCallback<XBRLGenerationParameters> callback) throws IllegalArgumentException;
}
