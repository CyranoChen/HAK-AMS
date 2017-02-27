
package com.tsystems.api.model.vo;

import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.tsystems.api.util.Tools;

public class ImfUsUpdateMsgVo {
	private Document doc; 
	private Element imfRoot;
	private Element sysInfo;
	private Element messageSequenceID;
	
	private Element data;
	private Element flightScheduledDate;
	private Element flightIdentity;
	private Element flightDirection;
	private Element airportIATACode;

	private Element operationalDateTime;

	private Element actualOnBlockDateTime;

	private Element actualOffBlockDateTime;
	
	private Element groundHandleDateTime;
	
	private Element actualGroundHandleStartDateTime;
	private Element actualGroundHandleEndDateTime;
	
	private String[][] sysInfos=new String[][]{{"MessageType","FlightData"},{"ServiceType","FUS"},
			{"OperationMode","MOD"},{"SendDateTime","sysdate"},{"CreateDateTime","sysdate"},
			{"OriginalDateTime","sysdate"},{"Receiver","IMF"},{"Sender","FQDB"},{"Owner","FQDB"},{"Priority","5"}};
	
	public ImfUsUpdateMsgVo(){
		this.doc=DocumentHelper.createDocument();
		this.imfRoot=doc.addElement("IMFRoot");
		this.imfRoot.addAttribute("xsi:noNamespaceSchemaLocation", "../OverView.xsd");
		this.imfRoot.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		this.sysInfo=imfRoot.addElement("SysInfo");
		this.messageSequenceID=sysInfo.addElement("MessageSequenceID");
		
		String currentTime=Tools.timeFormat(Tools.TIME_FORMAT_TZ, new Date());
		Element e;
		for(int i=0;i<sysInfos.length;i++){
			String [] nodeInfo=sysInfos[i];
			
			e=sysInfo.addElement(nodeInfo[0]);
			if(nodeInfo[1].equalsIgnoreCase("sysdate")){
				e.setText(currentTime);
			}else{
				e.setText(nodeInfo[1]);
			}
		}
		this.data=imfRoot.addElement("Data");
		e=data.addElement("PrimaryKey").addElement("FlightKey");
		this.flightScheduledDate=e.addElement("FlightScheduledDate");	
		this.flightIdentity=e.addElement("FlightIdentity");	
		this.flightDirection=e.addElement("FlightDirection");
		this.airportIATACode=e.addElement("BaseAirport").addElement("AirportIATACode");
		this.operationalDateTime=data.addElement("FlightData").addElement("OperationalDateTime");
				
	}
	
	public void setMessageSequenceID(String messageSequenceID){
		if(messageSequenceID!=null && !messageSequenceID.equals("")){
			this.messageSequenceID.setText(messageSequenceID);
		}
	}
			
	public void setFlightScheduledDate(String flightScheduledDate){
		if(flightScheduledDate!=null && !flightScheduledDate.equals("")){			
		this.flightScheduledDate.setText(Tools.timeFormat(Tools.DATE_FORMAT, Tools.timeParse(Tools.TIME_FORMAT, flightScheduledDate)));
		}
	}
	
	public void setFlightIdentity(String flightIdentity){
		if(flightIdentity!=null && !flightIdentity.equals("")){
		this.flightIdentity.setText(flightIdentity);
		}
	}
	
	public void setFlightDirection(String flightDirection){
		if(flightDirection!=null && !flightDirection.equals("")){
			this.flightDirection.setText(flightDirection.equals("0")?"A":"D");
		}
	}
	
	public void setAirportIATACode(String airportIATACode){
		if(airportIATACode!=null && !airportIATACode.equals("")){
			this.airportIATACode.setText(airportIATACode);
		}
	}

	public void setActualOnBlockDateTime(String actualOnBlockDateTime){
		if(actualOnBlockDateTime!=null && !actualOnBlockDateTime.equals("")){
			this.actualOnBlockDateTime=this.operationalDateTime.addElement("OnBlockDateTime").addElement("ActualOnBlockDateTime");		
			this.actualOnBlockDateTime.setText(Tools.timeFormat(Tools.TIME_FORMAT_TZ, Tools.timeParse(Tools.TIME_FORMAT, actualOnBlockDateTime)));
		}
	}
	
	public void setActualOffBlockDateTime(String actualOffBlockDateTime){
		if(actualOffBlockDateTime!=null && !actualOffBlockDateTime.equals("")){
			this.actualOffBlockDateTime=this.operationalDateTime.addElement("OffBlockDateTime").addElement("ActualOffBlockDateTime");		
			this.actualOffBlockDateTime.setText(Tools.timeFormat(Tools.TIME_FORMAT_TZ, Tools.timeParse(Tools.TIME_FORMAT, actualOffBlockDateTime)));
		}
	}
	
	public void setActualGroundHandleStartDateTime(String actualGroundHandleStartDateTime){
		if(actualGroundHandleStartDateTime!=null && !actualGroundHandleStartDateTime.equals("")){
			if(this.groundHandleDateTime==null){
				this.groundHandleDateTime=this.operationalDateTime.addElement("GroundHandleDateTime");
			}
			this.actualGroundHandleStartDateTime=this.groundHandleDateTime.addElement("ActualGroundHandleStartDateTime");		
			this.actualGroundHandleStartDateTime.setText(Tools.timeFormat(Tools.TIME_FORMAT_TZ, Tools.timeParse(Tools.TIME_FORMAT, actualGroundHandleStartDateTime)));
		}
	}
	
	public void setActualGroundHandleEndDateTime(String actualGroundHandleEndDateTime){
		if(actualGroundHandleEndDateTime!=null && !actualGroundHandleEndDateTime.equals("")){
			if(this.groundHandleDateTime==null){
				this.groundHandleDateTime=this.operationalDateTime.addElement("GroundHandleDateTime");
			}
			this.actualGroundHandleEndDateTime=this.groundHandleDateTime.addElement("ActualGroundHandleEndDateTime");		
			this.actualGroundHandleEndDateTime.setText(Tools.timeFormat(Tools.TIME_FORMAT_TZ, Tools.timeParse(Tools.TIME_FORMAT, actualGroundHandleEndDateTime)));
		}
	}

	
	public String toXML(){
		return this.doc.asXML();	
	}		
			
	
	public static void main(String[] args){
		ImfUsUpdateMsgVo msg=new ImfUsUpdateMsgVo();
		System.out.println(msg.toXML());
	}


}
