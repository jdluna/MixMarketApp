package com.mambu.xbml.client.view;

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
import com.mambu.xbml.client.XMBLProcessService;
import com.mambu.xbml.client.XMBLProcessServiceAsync;
import com.mambu.xbml.shared.XBMLElement;

public class XBMLElementWidget extends Composite {

	private static AccountWidgetUiBinder uiBinder = GWT.create(AccountWidgetUiBinder.class);

	interface AccountWidgetUiBinder extends UiBinder<Widget, XBMLElementWidget> {
	}

	private final XMBLProcessServiceAsync processService = GWT.create(XMBLProcessService.class);

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
	XBMLElement element;

	/**
	 * The parent object which has the request settings
	 */
	HasRequestSettings requestController;

	public XBMLElementWidget() {
		initWidget(uiBinder.createAndBindUi(this));

		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		dialogBox.setWidget(dialogLabel);
		dialogBox.setAutoHideEnabled(true);
	}

	public @UiConstructor
	XBMLElementWidget(String elementName) {
		this();
		element = XBMLElement.valueOf(elementName);

		this.label.setText(element.getName());
	}

	public XBMLElementWidget(XBMLElement elementName) {
		this(elementName.toString());
	}
	
	/**
	 * Fired when the user types in the nameField.
	 */
	@UiHandler("value")
	public void onKeyUp(KeyUpEvent event) {
		//store the current expression
		valueExpression = value.getValue();

		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			processService.processRequest(requestController.getRequestSettings(), value.getValue(), new AsyncCallback<String>() {

				@Override
				public void onSuccess(String result) {
					value.setStyleName(style.valueComputed());
					value.setText(result);
				}

				@Override
				public void onFailure(Throwable caught) {
					value.setStyleName(style.valueError());
					dialogLabel.setText(caught.getMessage());
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
	 * Returns the XBML element
	 * @return
	 */
	public XBMLElement getXBMLElemenet() {
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
