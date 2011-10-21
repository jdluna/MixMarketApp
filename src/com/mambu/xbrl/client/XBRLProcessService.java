package com.mambu.xbrl.client;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mambu.xbrl.shared.XBRLElement;
import com.mambu.xbrl.shared.XBRLGenerationParamaters;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface XBRLProcessService extends RemoteService {
	String processRequest(XBRLGenerationParamaters conn, XBRLElement element, String input);
	String generateXML(XBRLGenerationParamaters conn);
}
