package com.nlia.fqdb.message.base;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class TerminalMessages {
	private static final String BUNDLE_NAME = "com.nlia.fqdb.message.base.terminalmessages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private TerminalMessages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
