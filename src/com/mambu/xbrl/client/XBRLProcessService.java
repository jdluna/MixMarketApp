package com.mambu.xbrl.client;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mambu.xbrl.shared.XBRLElement;
import com.mambu.xbrl.shared.TenantSettings;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface XBRLProcessService extends RemoteService {
	String processRequest(XBRLElement element, String input);
	String generateXML();
	TenantSettings storeParams(TenantSettings params);
	TenantSettings getParams();
}
