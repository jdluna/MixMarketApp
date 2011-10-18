package com.mambu.xbml.server;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.mambu.accounting.shared.model.GLAccount;
import com.mambu.apisdk.MambuAPIFactory;
import com.mambu.apisdk.MambuAPIService;
import com.mambu.apisdk.exception.MambuApiException;
import com.mambu.xbml.client.XMBLProcessService;
import com.mambu.xbml.shared.ConnectionInfo;
import com.mambu.xbml.shared.XBMLElement;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class XMBLProcessServiceImpl extends RemoteServiceServlet implements XMBLProcessService {

	private final static ScriptEngine SCRIPT_ENGINE = new ScriptEngineManager().getEngineByName("JavaScript");

	/**
	 * Processses a single xbrml request
	 */
	public String processRequest(ConnectionInfo conn, String input) throws IllegalArgumentException {

		MambuAPIService mambu;
		try {
			mambu = MambuAPIFactory.crateService("apied", "apied", "demo.mambuonline.com");
			mambu.setProtocol("http");

		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}

		BigDecimal processInputstring = processInputstring(mambu, input);

		return processInputstring.stripTrailingZeros().toPlainString();
	}

	private BigDecimal processInputstring(MambuAPIService mambu, String input) {
		String oriString = new String(input);

		// parse input
		ArrayList<String> glCodes = getGLCodes(input);

		for (String glCode : glCodes) {
			// get the balance
			BigDecimal accountBalance;
			try {
				accountBalance = getAccountBalance(mambu, glCode);
			} catch (MambuApiException e) {
				System.out.println(e.getErrorMessage());
				throw new IllegalArgumentException(e.getErrorMessage());
			}

			// replace it
			oriString = replace(glCode, accountBalance, oriString);

		}

		// evaluate the expression
		Double eval = 0d;
		try {
			eval = (Double) SCRIPT_ENGINE.eval(oriString);
		} catch (ScriptException e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		return new BigDecimal(eval);
	}

	/**
	 * Gets the balance of a givent account
	 * 
	 * @param mambu
	 * @param glCode
	 * @return
	 * @throws MambuApiException
	 */
	private BigDecimal getAccountBalance(MambuAPIService mambu, String glCode) throws MambuApiException {

		// get just the client info
		GLAccount glAccount = mambu.getGLAccount(glCode);

		return glAccount.getBalance();

	}

	/**
	 * Extracts gl account placeholders from a template
	 * 
	 * @param template
	 * @return
	 */
	private ArrayList<String> getGLCodes(String template) {

		ArrayList<String> placeholders = new ArrayList<String>();

		if (template != null) {

			Pattern p = Pattern.compile("\\{(.*?)\\}");
			Matcher m = p.matcher(template);

			while (m.find()) { // find next match
				String match = m.group();
				String code = match.substring(1, match.length() - 1);
				placeholders.add(code);
			}

		}
		return placeholders;
	}

	/**
	 * Replaces a gl code in a template with a result and a number of
	 * 
	 * @param glCode
	 *            the code we're replacing, eg: 41
	 * @param result
	 *            the result (value of the gl code evaluation)
	 * @param originalString
	 *            the original template eg: {1} + {41}
	 * @return
	 */
	private String replace(String glCode, BigDecimal result, String originalString) {
		return originalString.replaceAll("\\{" + glCode + "\\}", result.toString());
	}

	/**
	 * Generates the xml for a given connection with the specified inputs
	 */
	@Override
	public String generateXML(ConnectionInfo conn, LinkedHashMap<XBMLElement, String> values) {

		MambuAPIService mambu;
		try {
			mambu = MambuAPIFactory.crateService("apied", "apied", "demo.mambuonline.com");
			mambu.setProtocol("http");

		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}

		String generatedXML = "";

		try {
			XBMLGenerator xbmlGenerator = new XBMLGenerator();

			// now process the inputs
			for (Entry<XBMLElement, String> entryValues : values.entrySet()) {

				BigDecimal processInputstring = processInputstring(mambu, entryValues.getValue());

				// add to the xml
				xbmlGenerator.addElement(entryValues.getKey(), processInputstring);

			}

			generatedXML = xbmlGenerator.generate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());

		}

		return generatedXML;
	}
}
