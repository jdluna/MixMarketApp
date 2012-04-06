package com.mambu.xbrl.server;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jdo.PersistenceManager;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mambu.accounting.shared.model.GLAccount;
import com.mambu.apisdk.MambuAPIFactory;
import com.mambu.apisdk.MambuAPIService;
import com.mambu.apisdk.exception.MambuApiException;
import com.mambu.intelligence.shared.model.Intelligence.Indicator;
import com.mambu.xbrl.client.XBRLProcessService;
import com.mambu.xbrl.shared.Duration;
import com.mambu.xbrl.shared.IndicatorElementMap;
import com.mambu.xbrl.shared.PeriodType;
import com.mambu.xbrl.shared.XBRLElement;
import com.mambu.xbrl.shared.XBRLGenerationParameters;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class XBRLProcessServiceImpl extends RemoteServiceServlet implements XBRLProcessService {

    private static final Logger log = Logger.getLogger(XBRLProcessServiceImpl.class.getName());

	private static final ScriptEngine SCRIPT_ENGINE = new ScriptEngineManager().getEngineByName("JavaScript");

	/**
	 * Generates the xml for a given connection with the specified inputs
	 */
	@Override
	public String generateXML(XBRLGenerationParameters params) {

		MambuAPIService mambu;
		try {
			mambu = createService(params);

		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}

		String generatedXML = "";

		try {
			XBRLGenerator xBRLGenerator = new XBRLGenerator();
			xBRLGenerator.addLink();
			xBRLGenerator.addContext(params.durations);
			xBRLGenerator.addNumberUnit();
			xBRLGenerator.addCurrencyUnit(mambu.getCurrency().getCode());

			// now process the xbrl financial inputs
			processXBRLFinancials(mambu, xBRLGenerator, params);
			
			//and process
			processXBRLIndicators(mambu,xBRLGenerator);

			generatedXML = xBRLGenerator.generate();

		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());

		}

		return generatedXML;
	}
	
	/**
	 * Processses a single xbrml request
	 */
	
	@Override
	public String processRequest(XBRLGenerationParameters params, XBRLElement element, String input) throws IllegalArgumentException {

		MambuAPIService mambu = null;
		try {
			mambu = createService(params);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}

		Duration duration = null;
		if (element.getPeriod() ==  PeriodType.DURATION && params.durations.size() > 0) {
			duration = params.durations.get(0);
		}
		
		BigDecimal processInputstring = processInputString(mambu, input, duration);

		return processInputstring.stripTrailingZeros().toPlainString();
	}

	/**
	 * Processes an individual input string
	 * @param mambu
	 * @param input
	 * @return
	 */
	private BigDecimal processInputString(MambuAPIService mambu, String input, Duration duration) {
		String oriString = new String(input);

		// parse input
		ArrayList<String> glCodes = getGLCodes(input);

		for (String glCode : glCodes) {
			// get the balance
			BigDecimal accountBalance;
			try {
				accountBalance = getAccountBalance(mambu, glCode, duration);
			} catch (MambuApiException e) {
				log.severe(e.getErrorMessage());
				throw new IllegalArgumentException(e.getErrorMessage());
			}

			// replace it
			oriString = replace(glCode, accountBalance, oriString);

		}

		// evaluate the expression
		Float eval = 0f;
		try {
			Number value = (Number)SCRIPT_ENGINE.eval(oriString);
			eval = value.floatValue();
		} catch (ScriptException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e.getMessage());
		}

		return new BigDecimal(eval);
	}

	/**
	 * Gets the balance of a givent account
	 * 
	 * @param mambu
	 * @param glCode
	 * @param duration TODO
	 * @return
	 * @throws MambuApiException
	 */
	private BigDecimal getAccountBalance(MambuAPIService mambu, String glCode, Duration duration) throws MambuApiException {

		//get the account balance, with an optional date range
		GLAccount glAccount;
		if (duration == null) { 
			glAccount = mambu.getGLAccount(glCode);
		} else {
			glAccount = mambu.getGLAccount(glCode, DateUtils.format(duration.from), DateUtils.format(duration.to));
		}
			

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
	 * Process the XBRL financial elements specified as parameters
	 * @param mambu
	 * @param xBRLGenerator
	 * @param params
	 */
	private void processXBRLFinancials(MambuAPIService mambu, XBRLGenerator xBRLGenerator, XBRLGenerationParameters params) {
		for (Entry<XBRLElement, String> entryValues : params.values.entrySet()) {

			XBRLElement key = entryValues.getKey();
			//skip empties
			if (entryValues.getValue() == null || entryValues.getValue().isEmpty()) {
				continue;
			}
			
			
			//if it's not a number
			if (key.getPeriod() == null) {
				xBRLGenerator.addElement(key, entryValues.getValue());
				continue;
			}
			
			//process the key based on it's period type
			switch (key.getPeriod()) {
			case DURATION:
				//go through all durations
				for (Duration durr : params.durations) {
					
					BigDecimal processInputstring = processInputString(mambu, entryValues.getValue(), durr);

					// add to the xml
					xBRLGenerator.addElement(key, processInputstring, durr);
				}

				break;
				
			//for instances processes it for just right now
			case INSTANT:
				BigDecimal processInputstring = processInputString(mambu, entryValues.getValue(), null);

				// add to the xml
				xBRLGenerator.addElement(key, processInputstring);
				break;
			
			}
			

		}
	}
	
	/**
	 * Processes the indicators
	 * 
	 * @param mambu
	 * @param xBRLGenerator
	 * @throws MambuApiException
	 */
	private void processXBRLIndicators(MambuAPIService mambu, XBRLGenerator xBRLGenerator) throws MambuApiException {

		// go through the map
		for (Entry<Indicator, XBRLElement> entries : IndicatorElementMap.getMap().entrySet()) {

			BigDecimal indicatorValue = mambu.getIndicator(entries.getKey());

			xBRLGenerator.addElement(entries.getValue(), indicatorValue);
		}
	}

	/**
	 * Creates the API Service from the request settings
	 * 
	 * @param settings
	 * @return
	 * @throws MambuApiException
	 */
	private MambuAPIService createService(XBRLGenerationParameters settings) throws MambuApiException {
		MambuAPIService mambu = MambuAPIFactory.crateService(settings.username, settings.password, settings.domain);
		mambu.setProtocol("http");
		return mambu;
	}

	/**
	 * Stores parameters
	 */
	@Override
	public String storeParams(XBRLGenerationParameters params) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		params = pm.makePersistent(params);
		pm.close();
		
		return params.getEncodedKey();
		
	}

	/**
	 * Retreives parameters
	 */
	@Override
	public XBRLGenerationParameters getParams(String key) throws IllegalArgumentException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		
		try{
			XBRLGenerationParameters paramaters = pm.getObjectById(XBRLGenerationParameters.class,key);
			paramaters = pm.detachCopy(paramaters);
			return paramaters;
			
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		} finally {
			pm.close();

		}		

	
	}
}
