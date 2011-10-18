package com.mambu.xbml.client.view;

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
import com.mambu.xbml.client.XMBLProcessService;
import com.mambu.xbml.client.XMBLProcessServiceAsync;
import com.mambu.xbml.shared.RequestSetttings;
import com.mambu.xbml.shared.ElementCategory;
import com.mambu.xbml.shared.XBMLElement;

public class XBMLCreatorView extends Composite implements HasRequestSettings {

	private final XMBLProcessServiceAsync processService = GWT.create(XMBLProcessService.class);

	
	private static XBMLCreatorViewUiBinder uiBinder = GWT.create(XBMLCreatorViewUiBinder.class);
	
	@UiField TabLayoutPanel tabPanel;
	@UiField TextArea xbmlOutput;
	@UiField Button executeButton;
	@UiField HTMLPanel outputPanel;
	
	@UiField
	TextBox domain, username, password;
	
	@UiField
	DateBox fromDate, toDate;

	HashMap<ElementCategory, FlowPanel> categoryTabMap = new HashMap<ElementCategory, FlowPanel>();
	List<XBMLElementWidget> elementWidgets = new ArrayList<XBMLElementWidget>();
	
	final DialogBox dialogBox = new DialogBox();
	Label dialogLabel = new Label();


	interface XBMLCreatorViewUiBinder extends UiBinder<Widget, XBMLCreatorView> {
	}

	public XBMLCreatorView() {
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
		username.setText("apied");
		password.setText("apied");
	}
	
	/**
	 * Populates the tabs with the XBML Element as they are defined
	 */
	private void populateElements() {
		
		for (XBMLElement element : XBMLElement.values()) {
			//skip those not part of any category
			if (element.getCategory() == null) {
				continue;
			}
			
			//get the corresponding tab panel
			FlowPanel flowPanel = categoryTabMap.get(element.getCategory());
			
			//create the widget and add it to the panel
			XBMLElementWidget elementWidget = new XBMLElementWidget(element);
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
	public RequestSetttings getRequestSettings() {
	
		RequestSetttings info = new RequestSetttings();
		info.domain = domain.getValue();
		info.username = username.getValue();
		info.password = password.getValue();
		info.fromDate = fromDate.getValue();
		info.toDate = toDate.getValue();
		
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
		LinkedHashMap<XBMLElement, String> values = getXBMLValues();
		
		processService.generateXML(getRequestSettings(), values, new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				outputPanel.setVisible(true);
				xbmlOutput.setText(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				dialogLabel.setText(caught.getMessage());
				dialogBox.center();				
			}
		});
	}

	/**
	 * Gets the XBML Values from the widget
	 * @return
	 */
	private LinkedHashMap<XBMLElement, String> getXBMLValues() {
		LinkedHashMap<XBMLElement, String> values = new LinkedHashMap<XBMLElement, String>();
		
		for (XBMLElementWidget widget : elementWidgets) {
			XBMLElement xbmlElemenet = widget.getXBMLElemenet();
			String value = widget.getValue();
			values.put(xbmlElemenet, value);
		}
		
		return values;
	}
}
