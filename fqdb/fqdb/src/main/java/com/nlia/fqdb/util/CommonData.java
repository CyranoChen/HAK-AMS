package com.nlia.fqdb.util;


public interface CommonData {

	public final static String ADMIN = "管理员";
	public static enum REMOVEFLAG {
		REMOVED("0"), NOT_REMOVED("1");

		private final String value;

		public String getValue() {
			return this.value;
		}

		REMOVEFLAG(String value) {
			this.value = value;
		}
	}
}