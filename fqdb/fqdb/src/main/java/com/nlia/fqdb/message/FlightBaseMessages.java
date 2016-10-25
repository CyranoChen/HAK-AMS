package com.nlia.fqdb.message;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class FlightBaseMessages {
    private static final String	 BUNDLE_NAME     = "com.nlia.fqdb.message.flightbasemessages"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    private FlightBaseMessages() {
    }

    public static String getString(String key) {
	try {
	    return RESOURCE_BUNDLE.getString(key);
	} catch (MissingResourceException e) {
	    return '!' + key + '!';
	}
    }
}
