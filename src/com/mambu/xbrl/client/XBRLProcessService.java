package com.mambu.xbrl.client;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mambu.xbrl.shared.XBRLElement;
import com.mambu.xbrl.shared.XBRLGenerationParameters;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface XBRLProcessService extends RemoteService {
	String processRequest(XBRLGenerationParameters conn, XBRLElement element, String input);
	String generateXML(XBRLGenerationParameters conn);
	String storeParams(XBRLGenerationParameters params);
	XBRLGenerationParameters getParams(String key);
}
