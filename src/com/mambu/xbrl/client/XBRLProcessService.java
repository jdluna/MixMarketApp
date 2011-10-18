package com.mambu.xbrl.client;

import java.util.LinkedHashMap;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mambu.xbrl.shared.RequestSetttings;
import com.mambu.xbrl.shared.XBRLElement;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface XBRLProcessService extends RemoteService {
	String processRequest(RequestSetttings conn, String input);
	String generateXML(RequestSetttings conn, LinkedHashMap<XBRLElement, String> values);
}
