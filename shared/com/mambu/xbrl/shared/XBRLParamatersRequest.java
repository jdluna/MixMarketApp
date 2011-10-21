package com.mambu.xbrl.shared;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.ServiceName;

@ServiceName(value = "com.mambu.xbrl.server.XBRLParamatersService", locator = "com.mambu.xbrl.server.XBRLParamatersServiceLocator")
public interface XBRLParamatersRequest extends RequestContext {

	Request<XBRLGenerationParamatersProxy> createXBRLGenerationParamaters();

	Request<XBRLGenerationParamatersProxy> readXBRLGenerationParamaters(Long id);

	Request<XBRLGenerationParamatersProxy> updateXBRLGenerationParamaters(
			XBRLGenerationParamatersProxy xbrlgenerationparamaters);

	Request<Void> deleteXBRLGenerationParamaters(XBRLGenerationParamatersProxy xbrlgenerationparamaters);

	Request<List<XBRLGenerationParamatersProxy>> queryXBRLGenerationParamaterss();

}
