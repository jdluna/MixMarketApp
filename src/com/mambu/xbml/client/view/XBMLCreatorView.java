package com.mambu.xbml.client.view;

import java.util.LinkedHashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.mambu.xbml.client.XMBLProcessService;
import com.mambu.xbml.client.XMBLProcessServiceAsync;
import com.mambu.xbml.shared.XBMLElement;

public class XBMLCreatorView extends Composite  {

	private final XMBLProcessServiceAsync processService = GWT.create(XMBLProcessService.class);

	
	private static XBMLCreatorViewUiBinder uiBinder = GWT.create(XBMLCreatorViewUiBinder.class);
	
	@UiField TabLayoutPanel tabPanel;
	@UiField TextArea xbmlOutput;
	@UiField Button executeButton;

	interface XBMLCreatorViewUiBinder extends UiBinder<Widget, XBMLCreatorView> {
	}

	public XBMLCreatorView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public XBMLCreatorView(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	

	@UiHandler("executeButton")
	void onExecuteButtonClick(ClickEvent event) {
		LinkedHashMap<XBMLElement, String> values = new LinkedHashMap<XBMLElement, String>();
		values.put(XBMLElement.ADMINISTRATIVE_EXPENSE, "{1100} - 50");
		processService.generateXML(null, values, new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				xbmlOutput.setText(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
