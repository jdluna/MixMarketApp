package com.mambu.xbrl.client.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.FormElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.mambu.xbrl.client.XBRLProcessService;
import com.mambu.xbrl.client.XBRLProcessServiceAsync;
import com.mambu.xbrl.shared.Duration;
import com.mambu.xbrl.shared.ElementCategory;
import com.mambu.xbrl.shared.ElementType;
import com.mambu.xbrl.shared.XBRLElement;
import com.mambu.xbrl.shared.XBRLGenerationParameters;

public class XBRLCreatorView extends Composite implements HasRequestSettings {

	private final XBRLProcessServiceAsync processService = GWT.create(XBRLProcessService.class);

	
	private static XBRLCreatorViewUiBinder uiBinder = GWT.create(XBRLCreatorViewUiBinder.class);
	
	@UiField TabLayoutPanel tabPanel;
	@UiField TextArea XBRLOutput;
	@UiField HTMLPanel outputPanel;
	
	@UiField 
	Button executeButton, storeButton, loadButton, retrieveButton, resetButton, exportButton;

	@UiField
	TextBox domain, username, password, getXBRLMappingKey;
	
	@UiField
	DateBox fromDate, toDate;
	
	@UiField
	DialogBox storedDialog, loadDialog;
	
	@UiField
	Label storedKeyValue, storedKeyError;
	
	@UiField
	Image loadingImage;
	
	@UiField
	FormPanel exportFormPanel;
	
	@UiField
	Hidden xmlContents;

	HashMap<ElementCategory, FlowPanel> categoryTabMap = new HashMap<ElementCategory, FlowPanel>();
	List<XBRLElementWidget> elementWidgets = new ArrayList<XBRLElementWidget>();
	
	final DialogBox errorDialogBox = new DialogBox();
	Label dialogLabel = new Label();

	XBRLGenerationParameters parameters = new XBRLGenerationParameters();

	interface XBRLCreatorViewUiBinder extends UiBinder<Widget, XBRLCreatorView> {
	}

	public XBRLCreatorView() {
		initWidget(uiBinder.createAndBindUi(this));
		
		storedKeyError.setVisible(false);
		
		//create the dialog box
		errorDialogBox.setText("Remote Procedure Call");
		errorDialogBox.setAnimationEnabled(true);
		errorDialogBox.setWidget(dialogLabel);
		errorDialogBox.setAutoHideEnabled(true);
		
		//initialize other dialogs
		storedDialog.setAnimationEnabled(true);
		storedDialog.setAutoHideEnabled(true);
		storedDialog.setGlassEnabled(true);
		storedDialog.show();
		storedDialog.hide();
		
		loadDialog.setAnimationEnabled(true);
		loadDialog.setAutoHideEnabled(true);
		loadDialog.setGlassEnabled(true);
		loadDialog.show();
		loadDialog.hide();
		
		loadingImage.setVisible(false);
		
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
		
		//setup the form
		FormElement.as(exportFormPanel.getElement()).setAcceptCharset("UTF-8");
		exportFormPanel.setAction("/mambuxbrl/xmldownload");
		exportFormPanel.setEncoding(FormPanel.ENCODING_URLENCODED);
		exportFormPanel.setMethod(FormPanel.METHOD_POST);
		xmlContents.setName("xml");
		
		exportButton.setVisible(false);
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
			if (element.getType() == ElementType.STRING) {
				elementWidget.value.setWidth("325px");
			} else {
				elementWidget.setRequestController(this);
			}			

			flowPanel.add(elementWidget);
			
			//add the widget
			elementWidgets.add(elementWidget);
			
		}

	}

	/**
	 * Gets the connection info settings
	 * @return
	 */
	public XBRLGenerationParameters getRequestParams() {
	
		XBRLGenerationParameters info = new XBRLGenerationParameters();
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
			tabPanel.add(flowPanel, cat.getName());
			categoryTabMap.put(cat, flowPanel);
			
		}
	}

	@UiHandler("executeButton")
	void onExecuteButtonClick(ClickEvent event) {
		
		//get the values
		loadingImage.setVisible(true);
		XBRLGenerationParameters params = getRequestParams();
		processService.generateXML(params, new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				outputPanel.setVisible(true);
				XBRLOutput.setText(result);
				loadingImage.setVisible(false);
				exportButton.setVisible(true);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				dialogLabel.setText(caught.toString() + " : " + caught.getMessage());
				errorDialogBox.center();		
				loadingImage.setVisible(false);
				exportButton.setVisible(false);


			}
		});
	}
	
	@UiHandler("storeButton")
	void onStoreButton(ClickEvent e) {
		
		//if first save, then just store
		if (parameters.getEncodedKey() == null) {
			storeParams(getRequestParams());

		// update existing params
		} else {
			
			parameters.setValues(getXBRLValues());
			storeParams(parameters);
		}
		
		
		
	}
	
	/**
	 * When clicking on the export button the filename and HTML table in the form is updated.
	 * 
	 * @param e
	 */
	@UiHandler("exportButton")
	void exportButtonClicked(ClickEvent e) {

		// update values
		xmlContents.setValue(XBRLOutput.getText());

		// submit form
		exportFormPanel.submit();

	}

	/**
	 * Stores the specifed params on the servers
	 */
	private void storeParams(XBRLGenerationParameters params) {

		processService.storeParams(params, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				dialogLabel.setText(caught.getMessage());
				errorDialogBox.center();					
			}

			@Override
			public void onSuccess(String result) {
				storedKeyValue.setText(result);
				storedDialog.center();				
			}
		});
	}

	@UiHandler("loadButton")
	void onLoadButton(ClickEvent e) {
		getXBRLMappingKey.setText("");
		loadDialog.center();
		
	}
	
	@UiHandler("resetButton")
	void resetButton(ClickEvent e) {
		//reset paramaters
		parameters = new XBRLGenerationParameters();
		populateResults();
		exportButton.setVisible(false);
		outputPanel.setVisible(false);
		
	}
	
	@UiHandler("retrieveButton")
	void onRetrieveButtonClicked(ClickEvent e) {
		
		String key = getXBRLMappingKey.getText();
		
		processService.getParams(key, new AsyncCallback<XBRLGenerationParameters>() {

			@Override
			public void onFailure(Throwable caught) {
				storedKeyError.setVisible(true);				
			}

			@Override
			public void onSuccess(XBRLGenerationParameters result) {
				storedKeyError.setVisible(false);				

				parameters = result;
				populateResults();

				loadDialog.hide();
				
								
			}
		});
		
	}
	
	/**
	 * Populates the view with the results
	 */
	protected void populateResults() {
		
		HashMap<XBRLElement, String> values = parameters.getValues();
		
		for (XBRLElementWidget widget : elementWidgets) {
			XBRLElement element = widget.element;
			if (values.containsKey(element)) {
				widget.value.setText(values.get(element));
			} else {
				widget.value.setText("");

			}
			
		}		
	}

	/**
	 * Gets the XBRL Values from the widget
	 * @return
	 */
	private HashMap<XBRLElement, String> getXBRLValues() {
		HashMap<XBRLElement, String> values = new HashMap<XBRLElement, String>();
		
		for (XBRLElementWidget widget : elementWidgets) {
			XBRLElement XBRLElemenet = widget.getXBRLElemenet();
			String value = widget.getValue();
			values.put(XBRLElemenet, value);
		}
		
		return values;
	}
}
