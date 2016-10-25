package com.nlia.fqdb.message.base;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class AircraftTypeMessages {
	private static final String BUNDLE_NAME = "com.nlia.fqdb.message.base.aircrafttypemessages";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private AircraftTypeMessages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
