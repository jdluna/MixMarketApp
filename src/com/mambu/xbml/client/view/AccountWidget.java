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

public class AccountWidget extends Composite {

	private static AccountWidgetUiBinder uiBinder = GWT.create(AccountWidgetUiBinder.class);

	interface AccountWidgetUiBinder extends UiBinder<Widget, AccountWidget> {
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

	String valueExpression = "";
	XBMLElement element;

	public AccountWidget() {
		initWidget(uiBinder.createAndBindUi(this));

		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		dialogBox.setWidget(dialogLabel);
		dialogBox.setAutoHideEnabled(true);
	}

	public @UiConstructor
	AccountWidget(String elementName) {
		this();
		element = XBMLElement.valueOf(elementName);

		this.label.setText(element.getName());
	}

	/**
	 * Fired when the user types in the nameField.
	 */
	@UiHandler("value")
	public void onKeyUp(KeyUpEvent event) {
		//store the current expression
		valueExpression = value.getValue();

		if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
			processService.processRequest(null, value.getValue(), new AsyncCallback<String>() {

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
	
	@UiHandler("value")
	public void onClick(ClickEvent e){
		if (valueExpression.isEmpty()) {
			valueExpression = value.getValue();
		}
		
		value.setStyleName(style.value());
		value.setText(valueExpression);
	}

}
