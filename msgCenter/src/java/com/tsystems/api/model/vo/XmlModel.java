
package com.tsystems.api.model.vo;

import net.sf.json.JSONObject;
public class XmlModel {
	private String date="";
	private String type="";
	private JSONObject record=null;
	
	public XmlModel(String date,String type){
		this.date=date;
		this.type=type;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the records
	 */
	public JSONObject getRecord() {
		return record;
	}
	/**
	 * @param record the records to set
	 */
	public void setRecord(JSONObject recordObj) {
		this.record=recordObj;
	}
	
}
