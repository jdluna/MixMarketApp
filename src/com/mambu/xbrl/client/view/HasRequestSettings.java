package com.mambu.xbrl.client.view;

import com.mambu.xbrl.shared.XBRLGenerationParameters;

/**
 * Interface for the object which has the XBRL Request settings
 * @author edanilkis
 *
 */
public interface HasRequestSettings {

	public XBRLGenerationParameters getRequestParams();
}
