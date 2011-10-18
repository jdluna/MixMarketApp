package com.mambu.xbrl.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.mambu.xbrl.client.view.XBRLCreatorView;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MambuXBRL implements EntryPoint {


	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
		rootLayoutPanel.clear();
		rootLayoutPanel.add(new XBRLCreatorView());

	}
}
