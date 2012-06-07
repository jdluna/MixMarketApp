package com.mambu.xbrl.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mambu.xbrl.shared.TenantSettings;
import com.mambu.xbrl.shared.XBRLElement;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface XBRLProcessServiceAsync {
	
	
	void processRequest(XBRLElement element, String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void generateXML(AsyncCallback<String> callback);

	void storeParams(TenantSettings params, AsyncCallback<TenantSettings> callback);

	void getParams(AsyncCallback<TenantSettings> callback) throws IllegalArgumentException;
}
