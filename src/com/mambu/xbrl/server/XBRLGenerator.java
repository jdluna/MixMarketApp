package com.mambu.xbrl.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.NodeFactory;
import nu.xom.Serializer;

import com.mambu.xbrl.shared.Duration;
import com.mambu.xbrl.shared.Namespace;
import com.mambu.xbrl.shared.XBRLElement;

public class XBRLGenerator {

	
	private String instantContextID;
	
	/**
	 * Maps durations to context ids for this generation
	 */
	private HashMap<Duration, String> durationContextIDsMap = new HashMap<Duration, String>();
	
	private String currencyUnit;
	private String numericUnit = "Number";
	
	// ID of the organization
	private static String IDENTIFIER = "0000000";

	private Document doc;
	private Element root;

	public XBRLGenerator() {
		root = generateRoot();

		doc = new Document(root);

	}

	/**
	 * Adds an XBRL Element to the sheet for a given duration
	 * 
	 * @param element
	 * @param value
	 */
	public void addElement(XBRLElement element, BigDecimal value, Duration durr) {

		// create the element
		Element xmlElement = new Element(element.getFullName(), element.getNamespace().getUrl());
		xmlElement.addAttribute(new Attribute("contextRef", durationContextIDsMap.get(durr)));
		xmlElement.addAttribute(new Attribute("unitRef", getUnitRef(element)));
		xmlElement.addAttribute(new Attribute("decimals", getNumberOfDecimalPlaces(value).toString()));

		// add the child
		xmlElement.appendChild(value.toPlainString());

		root.appendChild(xmlElement);
	}
	
	/**
	 * Adds an XBRL Element to the sheet for a given instance
	 * 
	 * @param element
	 * @param value
	 */
	public void addElement(XBRLElement element, BigDecimal value) {

		// create the element
		Element xmlElement = new Element(element.getFullName(), element.getNamespace().getUrl());
		xmlElement.addAttribute(new Attribute("contextRef", instantContextID));
		xmlElement.addAttribute(new Attribute("unitRef", getUnitRef(element)));
		xmlElement.addAttribute(new Attribute("decimals", getNumberOfDecimalPlaces(value).toString()));

		// add the child
		xmlElement.appendChild(value.toPlainString());

		root.appendChild(xmlElement);
	}
	
	/**
	 * Adds an XBRL Element to the sheet for a given instance
	 * 
	 * @param element
	 * @param value
	 */
	public void addElement(XBRLElement element, String value) {

		// create the element
		Element xmlElement = new Element(element.getFullName(), element.getNamespace().getUrl());

		// add the child
		xmlElement.appendChild(value);

		root.appendChild(xmlElement);
	}

	/**
	 * Gets the unit reference for an element
	 * 
	 * @param element
	 * @return
	 */
	private String getUnitRef(XBRLElement element) {

		switch (element.getType()) {
		case AMOUNT:
		case INTEGER:
			return numericUnit;
		case MONEY:
			return currencyUnit;	
		}

		return "";
	}

	/**
	 * Generates the document to xml and returns the string output
	 * 
	 * @return
	 */
	public String generate() {

		// Instantiate the stream you want to use.
		ByteArrayOutputStream sos = new ByteArrayOutputStream();

		try {
			Serializer serializer = new Serializer(sos, "UTF-8");
			serializer.setIndent(4);
			serializer.setMaxLength(256);
			serializer.write(doc);
		} catch (IOException ex) {
			System.err.println(ex);
		}

		String result = sos.toString();
		return result;
	}

	/**
	 * Adds the generic number unit
	 */
	void addNumberUnit() {
		Element numerUnit = new Element("unit");
		numerUnit.addAttribute(new Attribute("id", numericUnit));
		Element measure = new Element("measure");
		measure.appendChild("xbrli:pure");
		numerUnit.appendChild(measure);
		root.appendChild(numerUnit);

	}

	/**
	 * Adds the currency unit to the document
	 * 
	 * @param currencyCode
	 */
	public void addCurrencyUnit(String currencyCode) {
		currencyUnit = currencyCode;
		Element currencyUnitElement = new Element("unit");
		currencyUnitElement.addAttribute(new Attribute("id", currencyUnit));
		Element measure = new Element("measure");
		measure.appendChild(Namespace.ISO4217.getPrefix() + ":" + currencyCode);
		currencyUnitElement.appendChild(measure);

		root.appendChild(currencyUnitElement);

	}

	/**
	 * Generates the root element with the namesspaces
	 * 
	 * @return
	 */
	private Element generateRoot() {
		Element root = new Element("xbrl");
		root.appendChild(new NodeFactory().makeComment("Created by The Mambu XBRL Tool on " + new Date().toString())
				.get(0));

		// add the namespaces
		for (Namespace ns : Namespace.values()) {
			root.addNamespaceDeclaration(ns.getPrefix(), ns.getUrl());

		}

		return root;

	}
	

	/**
	 * Adds the link
	 */
	public void addLink() {

		Element schemaRef = new Element("schemaRef");
		schemaRef.addNamespaceDeclaration("link", "http://www.themix.org/sites/default/files/Taxonomy2010/dct/dc-all_2010-08-31.xsd");
		root.appendChild(schemaRef);
	}

	/**
	 * Adds the context to the document
	 */
	void addContext(List<Duration> durations) {
		// create the context ID

		String contextIdDate = DateUtils.format(new Date());
		this.instantContextID = "As_Of_" + contextIdDate;

		Element context = new Element("context");
		context.addAttribute(new Attribute("id", instantContextID));

		Element entity = new Element("entity");
		context.appendChild(entity);

		Element identifier = new Element("identifier");
		identifier.addAttribute(new Attribute("scheme", "http://www.themix.org"));
		identifier.appendChild(IDENTIFIER);
		entity.appendChild(identifier);

		Element period = new Element("period");
		Element instant = new Element("instant");
		instant.appendChild(contextIdDate);
		period.appendChild(instant);
		context.appendChild(period);
		
		root.appendChild(context);

		
		//add the durations
		for (Duration duration : durations) {
			String durationIDFrom = DateUtils.format(duration.from);
			String durationIDTo = DateUtils.format(duration.to);
			String durationContextID = "Duration_" + durationIDFrom + "_To_" + durationIDTo;

			Element durationContext = new Element("context");
			durationContext.addAttribute(new Attribute("id", durationContextID));
			
			//add entity and identifier for the duration
			Element entity2 = new Element("entity");
			durationContext.appendChild(entity2);

			Element identifier2 = new Element("identifier");
			identifier2.addAttribute(new Attribute("scheme", "http://www.themix.org"));
			identifier2.appendChild(IDENTIFIER);
			entity2.appendChild(identifier2);

			//add the period
			Element durationPeriod = new Element("period");
			Element startDate = new Element("startDate");
			startDate.appendChild(durationIDFrom);
			Element endDate = new Element("endDate");
			endDate.appendChild(durationIDTo);
			durationPeriod.appendChild(startDate);
			durationPeriod.appendChild(endDate);
			
			durationContext.appendChild(durationPeriod);
			
			//add to root
			root.appendChild(durationContext);
			
			//track the id
			this.durationContextIDsMap.put(duration, durationContextID);
		}


	}

	private Integer getNumberOfDecimalPlaces(BigDecimal bigDecimal) {
		String string = bigDecimal.stripTrailingZeros().toPlainString();
		int index = string.indexOf(".");
		return index < 0 ? 0 : string.length() - index - 1;
	}


}
