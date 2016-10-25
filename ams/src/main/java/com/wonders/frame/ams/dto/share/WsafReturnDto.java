package com.wonders.frame.ams.dto.share;

public class WsafReturnDto<T> {
	
	private WsafInfo info;
	
	private T data;
	
	
	
	
	

	public WsafReturnDto() {
		super();
	
	}

	public WsafInfo getInfo() {
		return info;
	}

	public void setInfo(WsafInfo info) {
		this.info = info;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
	
}
