package com.mambu.xbml.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.mambu.xbml.client.view.XBMLCreatorView;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MambuXBML implements EntryPoint {


	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		RootLayoutPanel rootLayoutPanel = RootLayoutPanel.get();
		rootLayoutPanel.clear();
		rootLayoutPanel.add(new XBMLCreatorView());

	}
}
