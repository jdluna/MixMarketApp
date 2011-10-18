package com.mambu.xbrl.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mambu.xbrl.shared.Namespace;
import com.mambu.xbrl.shared.XBRLElement;

import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.NodeFactory;
import nu.xom.Serializer;

public class XBRLGenerator {

	private static String DATE_FORMAT = "yyyy-MM-dd";
	
	private String contextId;
	private String currencyUnit;
	private String numericUnit = "Number";

	private Document doc;
	private Element root;

	public XBRLGenerator() {
		root = generateRoot();

		doc = new Document(root);

		addContext();
		addNumberUnit();
		addCurrencyUnit("KHR");

	}

	/**
	 * Adds an XBRL Element to the sheet
	 * 
	 * @param element
	 * @param value
	 */
	public void addElement(XBRLElement element, BigDecimal value) {

		// create the element
		Element xmlElement = new Element(element.getFullName(), element.getNamespace().getUrl());
		xmlElement.addAttribute(new Attribute("contextRef", contextId));
		xmlElement.addAttribute(new Attribute("unitRef", getUnitRef(element)));
		xmlElement.addAttribute(new Attribute("decimals", getNumberOfDecimalPlaces(value).toString()));

		// add the child
		xmlElement.appendChild(value.toPlainString());

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
	private void addNumberUnit() {
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
		Element currencyUnitEleemnt = new Element("unit");
		currencyUnitEleemnt.addAttribute(new Attribute("id", currencyUnit));
		Element measure = new Element("measure");
		measure.appendChild(Namespace.ISO4217.getPrefix() + ":" + currencyCode);
		currencyUnitEleemnt.appendChild(measure);

		root.appendChild(currencyUnitEleemnt);

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
	 * Adds the context to the document
	 */
	private void addContext() {
		// create the context ID

		String contextIdDate = new SimpleDateFormat(DATE_FORMAT).format(new Date());
		contextId = "As_Of_" + contextIdDate;

		Element context = new Element("context");
		context.addAttribute(new Attribute("id", contextId));

		Element entity = new Element("entity");
		context.appendChild(entity);

		Element identifier = new Element("identifier");
		identifier.addAttribute(new Attribute("scheme", "http://www.themix.org"));
		entity.appendChild(identifier);

		Element period = new Element("period");
		Element instant = new Element("instant");
		instant.appendChild(contextIdDate);
		period.appendChild(instant);
		context.appendChild(period);

		root.appendChild(context);

	}

	private Integer getNumberOfDecimalPlaces(BigDecimal bigDecimal) {
		String string = bigDecimal.stripTrailingZeros().toPlainString();
		int index = string.indexOf(".");
		return index < 0 ? 0 : string.length() - index - 1;
	}

}
