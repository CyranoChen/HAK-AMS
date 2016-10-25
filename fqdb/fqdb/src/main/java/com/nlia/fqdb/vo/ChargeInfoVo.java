package com.nlia.fqdb.vo;

import java.util.Date;

/**
 * 收费查询信息对象
 * ChargeInfoDao中使用
 * @author wangsq
 *
 */
public class ChargeInfoVo {
	
	private Long targetId;			//收费对象ID
	private Long subairlineId;		//子航空公司ID
	private String subjectId;			//收费项目ID(多个之间用半角逗号分割 例 : 1000,1001,1002); 
	public Long getTargetId() {
		return targetId;
	}
	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}
	public Long getSubairlineId() {
		return subairlineId;
	}
	public void setSubairlineId(Long subairlineId) {
		this.subairlineId = subairlineId;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	
	

	
//	private String aircraftRegistration;	//机号
//	private String currentSubairlineId;					//航空公司
//	private String targetId;						//收费对象表的ID
//	private Date startDate;						//有效期开始日
//	private Date endDate;						//有效期结束日
//	private String subjectId;						//收费项目ID(多个之间用半角逗号分割 例 : 1000,1001,1002); 
//	
//	public String getAircraftRegistration() {
//		return aircraftRegistration;
//	}
//	public void setAircraftRegistration(String aircraftRegistration) {
//		this.aircraftRegistration = aircraftRegistration;
//	}
//	public String getTargetId() {
//		return targetId;
//	}
//	public void setTargetId(String targetId) {
//		this.targetId = targetId;
//	}
//	public Date getStartDate() {
//		return startDate;
//	}
//	public void setStartDate(Date startDate) {
//		this.startDate = startDate;
//	}
//	public Date getEndDate() {
//		return endDate;
//	}
//	public void setEndDate(Date endDate) {
//		this.endDate = endDate;
//	}
//	public String getSubjectId() {
//		return subjectId;
//	}
//	public void setSubjectId(String subjectId) {
//		this.subjectId = subjectId;
//	}
//	public String getCurrentSubairlineId() {
//		return currentSubairlineId;
//	}
//	public void setCurrentSubairlineId(String currentSubairlineId) {
//		this.currentSubairlineId = currentSubairlineId;
//	}

	
	
	

}
