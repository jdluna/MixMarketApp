package com.mambu.xbrl.client.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.mambu.xbrl.client.XBRLProcessService;
import com.mambu.xbrl.client.XBRLProcessServiceAsync;
import com.mambu.xbrl.shared.Duration;
import com.mambu.xbrl.shared.ElementCategory;
import com.mambu.xbrl.shared.XBRLGenerationParamaters;
import com.mambu.xbrl.shared.XBRLElement;

public class XBRLCreatorView extends Composite implements HasRequestSettings {

	private final XBRLProcessServiceAsync processService = GWT.create(XBRLProcessService.class);

	
	private static XBRLCreatorViewUiBinder uiBinder = GWT.create(XBRLCreatorViewUiBinder.class);
	
	@UiField TabLayoutPanel tabPanel;
	@UiField TextArea XBRLOutput;
	@UiField Button executeButton;
	@UiField HTMLPanel outputPanel;
	
	@UiField
	TextBox domain, username, password;
	
	@UiField
	DateBox fromDate, toDate;

	HashMap<ElementCategory, FlowPanel> categoryTabMap = new HashMap<ElementCategory, FlowPanel>();
	List<XBRLElementWidget> elementWidgets = new ArrayList<XBRLElementWidget>();
	
	final DialogBox dialogBox = new DialogBox();
	Label dialogLabel = new Label();


	interface XBRLCreatorViewUiBinder extends UiBinder<Widget, XBRLCreatorView> {
	}

	public XBRLCreatorView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		//create the dialog box
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		dialogBox.setWidget(dialogLabel);
		dialogBox.setAutoHideEnabled(true);
		
		outputPanel.setVisible(false);
		
		DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat(PredefinedFormat.DATE_LONG);
		
		fromDate.setFormat(new DateBox.DefaultFormat(dateTimeFormat));
		toDate.setFormat(new DateBox.DefaultFormat(dateTimeFormat));

		createTabs();
		populateElements();
		
		//initialize
		domain.setText("demo.mambuonline.com");
		username.setText("api");
		password.setText("api");
	}
	
	/**
	 * Populates the tabs with the XBRL Element as they are defined
	 */
	private void populateElements() {
		
		for (XBRLElement element : XBRLElement.values()) {
			//skip those not part of any category
			if (element.getCategory() == null) {
				continue;
			}
			
			//get the corresponding tab panel
			FlowPanel flowPanel = categoryTabMap.get(element.getCategory());
			
			//create the widget and add it to the panel
			XBRLElementWidget elementWidget = new XBRLElementWidget(element);
			elementWidget.setRequestController(this);
			flowPanel.add(elementWidget);
			
			//add the widget
			elementWidgets.add(elementWidget);
			
		}
		
	}

	/**
	 * Gets the connection info settings
	 * @return
	 */
	public XBRLGenerationParamaters getRequestParams() {
	
		XBRLGenerationParamaters info = new XBRLGenerationParamaters();
		info.domain = domain.getValue();
		info.username = username.getValue();
		info.password = password.getValue();
		
		//duration
		Duration duration = new Duration(fromDate.getValue(),toDate.getValue());
		if (duration.isDefined()) {
			info.durations.add(duration);

		}
		
		//values
		info.values =  getXBRLValues();		

		return info;
	}
	
	/**
	 * Creates the tabs and keeps track of them in the hashmap
	 */
	private void createTabs() {
		
		for (ElementCategory cat : ElementCategory.values()) {
			FlowPanel flowPanel = new FlowPanel();
			tabPanel.insert(flowPanel, cat.getName(), cat.ordinal());
			categoryTabMap.put(cat, flowPanel);
			
		}
	}

	@UiHandler("executeButton")
	void onExecuteButtonClick(ClickEvent event) {
		
		//get the values
		XBRLGenerationParamaters params = getRequestParams();
		processService.generateXML(params, new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				outputPanel.setVisible(true);
				XBRLOutput.setText(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				dialogLabel.setText(caught.getMessage());
				dialogBox.center();				
			}
		});
	}

	/**
	 * Gets the XBRL Values from the widget
	 * @return
	 */
	private LinkedHashMap<XBRLElement, String> getXBRLValues() {
		LinkedHashMap<XBRLElement, String> values = new LinkedHashMap<XBRLElement, String>();
		
		for (XBRLElementWidget widget : elementWidgets) {
			XBRLElement XBRLElemenet = widget.getXBRLElemenet();
			String value = widget.getValue();
			values.put(XBRLElemenet, value);
		}
		
		return values;
	}
}
