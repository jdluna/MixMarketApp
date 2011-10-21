package com.mambu.xbrl.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.mambu.xbrl.client.XBRLProcessService;
import com.mambu.xbrl.client.XBRLProcessServiceAsync;
import com.mambu.xbrl.shared.XBRLElement;

public class XBRLElementWidget extends Composite {

	private static AccountWidgetUiBinder uiBinder = GWT.create(AccountWidgetUiBinder.class);

	interface AccountWidgetUiBinder extends UiBinder<Widget, XBRLElementWidget> {
	}

	private final XBRLProcessServiceAsync processService = GWT.create(XBRLProcessService.class);

	@UiField
	Label label;

	@UiField
	TextBox value;

	interface MyStyle extends CssResource {
		String valueError();

		String valueComputed();

		String value();
	}

	@UiField
	MyStyle style;

	final DialogBox dialogBox = new DialogBox();
	Label dialogLabel = new Label();

	/**
	 * Current expression being evaluated. Stored as we also may show the actual computed value
	 */
	String valueExpression = "";
	
	/**
	 * The element being contained here
	 */
	XBRLElement element;

	/**
	 * The parent object which has the request settings
	 */
	HasRequestSettings requestController;

	public XBRLElementWidget() {
		initWidget(uiBinder.createAndBindUi(this));

		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		dialogBox.setWidget(dialogLabel);
		dialogBox.setAutoHideEnabled(true);
	}

	public @UiConstructor
	XBRLElementWidget(String elementName) {
		this();
		element = XBRLElement.valueOf(elementName);

		String name = element.getName();
		
		//add spaces to the name
		char[] charArray = name.toCharArray();
		StringBuilder nameWithSpaces = new StringBuilder();
		for (char c : charArray) {
			if (Character.isUpperCase(c)) {
				nameWithSpaces.append(" " + c);
			} else {
				nameWithSpaces.append(c);
			}
		}
		this.label.setText(nameWithSpaces.toString());
	}

	public XBRLElementWidget(XBRLElement elementName) {
		this(elementName.toString());
	}
	
	/**
	 * Fired when the user types in the nameField.
	 */
	@UiHandler("value")
	public void onKeyUp(KeyUpEvent event) {
		//nothing to do if no controller
		if (requestController == null) {
			return;	
		}
		
		//store the current expression
		valueExpression = value.getValue();

		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			processService.processRequest(requestController.getRequestParams(), element, value.getValue(), new AsyncCallback<String>() {

				@Override
				public void onSuccess(String result) {
					value.setStyleName(style.valueComputed());
					value.setText(result);
				}

				@Override
				public void onFailure(Throwable caught) {
					caught.printStackTrace();
					value.setStyleName(style.valueError());
					dialogLabel.setText(caught.toString() + " : " +caught.getMessage());
					dialogBox.center();
				}
			});
		}
	}
	
	/**
	 * Handles the click by resetting back to the value
	 * @param e
	 */
	@UiHandler("value")
	public void onClick(ClickEvent e){
		if (valueExpression.isEmpty()) {
			valueExpression = value.getValue();
		}
		
		value.setStyleName(style.value());
		value.setText(valueExpression);
	}

	/**
	 * Sets the widget from which to retrieve the request settings
	 * @param controller
	 */
	public void setRequestController(HasRequestSettings controller) {
		this.requestController = controller;
		
	}

	/**
	 * Returns the XBRL element
	 * @return
	 */
	public XBRLElement getXBRLElemenet() {
		return element;
	}
	
	/**
	 * Gets the element value expression
	 * @return
	 */
	public String getValue() {
		if (valueExpression.isEmpty()) {
			valueExpression = value.getValue();
		}
		
		return valueExpression;
		
	}
}
