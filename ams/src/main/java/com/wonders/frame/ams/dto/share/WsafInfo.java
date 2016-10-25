package com.wonders.frame.ams.dto.share;

import java.util.HashMap;

public class WsafInfo {
	private boolean success;
	private HashMap<String, String> error;

	public boolean isSuccess() {
		return success;
	}
	
	public String code;

	public void setSuccess(boolean success) {
		this.success = success;
	}

	

	public HashMap<String, String> getError() {
		return error;
	}



	public void setError(HashMap<String, String> error) {
		this.error = error;
	}



	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}
