package com.mambu.xbml.client;

import java.util.LinkedHashMap;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mambu.xbml.shared.RequestSetttings;
import com.mambu.xbml.shared.XBMLElement;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface XMBLProcessService extends RemoteService {
	String processRequest(RequestSetttings conn, String input);
	String generateXML(RequestSetttings conn, LinkedHashMap<XBMLElement, String> values);
}
