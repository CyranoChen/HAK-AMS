package com.nlia.fqdb.message;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class AircraftAirlineAlterationMessages {
    private static final String	 BUNDLE_NAME     = "com.nlia.fqdb.message.aircraftairlinealterationmessages"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

    private AircraftAirlineAlterationMessages() {
    }

    public static String getString(String key) {
	try {
	    return RESOURCE_BUNDLE.getString(key);
	} catch (MissingResourceException e) {
	    return '!' + key + '!';
	}
    }
}
