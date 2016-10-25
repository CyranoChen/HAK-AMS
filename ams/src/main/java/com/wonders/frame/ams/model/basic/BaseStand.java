package com.wonders.frame.ams.model.basic;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wonders.frame.ams.model.ILongIdRemoveFlagModel;

/**
 * 机位
 */
@Entity
@Table(name = "BASE_STAND")
public class BaseStand implements ILongIdRemoveFlagModel {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	
	//add by Ninja, IDD, 2013-09-27
	/**
	 * 机位长度
	 */
	@Column(name = "STAND_LENGTH", length = 20)
	private Long standLength; 
	
	/**
	 * 机位宽度
	 */
	@Column(name = "STAND_WIDTH", length = 20)
	private Long standWidth; 
	
	// adder:zhuhaijian,20130701
	@Column(name = "Basic_Data_ID", length = 20)
	private String basicDataID;

	/**
	 * 机位号
	 */
	@Column(name = "STAND_CODE", length = 5, unique = true)
	private String standCode;

	/**
	 * 机位所属候机楼
	 */
	@Column(name = "STAND_TERMINAL", length = 5)
	private String standTerminal;

	/**
	 * 机位容积
	 */
	@Column(name = "STAND_CAPACITY", length = 3)
	private Long standCapacity;

	/**
	 * 机位描述说明
	 */
	@Column(name = "STAND_DESCRIPTION", length = 100)
	private String standDescription;

	/**
	 * 机位类型 Type:T,P,C,B,H
	 */
	@Column(name = "STAND_TYPE", length = 1)
	private String standType;

	/**
	 * 机位最大装箱高度
	 */
	@Column(name = "STAND_MAX_TAIL_HEIGHT", length = 6)
	private Long standMaxTailHeight;

	/**
	 * 机位最大承载重量
	 */
	@Column(name = "STAND_MAX_WEIGHT", length = 6)
	private Long standMaxWeight;

	/**
	 * 机位桥位数量
	 */
	@Column(name = "STAND_BRIDGES_NUMBER", length = 6)
	private Long standBridgesNumber;

	/**
	 * 机位登机类型
	 */
	@Column(name = "STAND_BOARDING_TYPE", length = 10)
	private String standBoardingType;

	/**
	 * 机位牵引信息
	 */
	@Column(name = "STAND_TOW", length = 10)
	private String standTow;

	@Column(name = "REMOVE_FLAG", length = 1)
	private String removeFlag;

	@Column(name = "CREATE_USER", length = 20)
	private String createUser;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@Temporal(TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "MODIFY_USER", length = 20)
	private String modifyUser;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@Temporal(TIMESTAMP)
	@Column(name = "MODIFY_TIME")
	private Date modifyTime;

	@Transient
	private String verifyDescription;
	
//	@Transient
//	private Map<Integer,List<Integer>> errorMessage;
	
	public String getVerifyDescription() {
		return verifyDescription;
	}

	public void setVerifyDescription(String verifyDescription) {
		this.verifyDescription = verifyDescription;
	}

//	public Map<Integer, List<Integer>> getErrorMessage() {
//		return errorMessage;
//	}
//
//	public void setErrorMessage(Map<Integer, List<Integer>> errorMessage) {
//		this.errorMessage = errorMessage;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStandCode() {
		return standCode;
	}

	public void setStandCode(String standCode) {
		this.standCode = standCode;
	}

	public String getStandTerminal() {
		return standTerminal;
	}

	public void setStandTerminal(String standTerminal) {
		this.standTerminal = standTerminal;
	}

	public Long getStandCapacity() {
		return standCapacity;
	}

	public void setStandCapacity(Long standCapacity) {
		this.standCapacity = standCapacity;
	}

	public String getStandDescription() {
		return standDescription;
	}

	public void setStandDescription(String standDescription) {
		this.standDescription = standDescription;
	}

	public String getStandType() {
		return standType;
	}

	public void setStandType(String standType) {
		this.standType = standType;
	}

	public Long getStandMaxTailHeight() {
		return standMaxTailHeight;
	}

	public void setStandMaxTailHeight(Long standMaxTailHeight) {
		this.standMaxTailHeight = standMaxTailHeight;
	}

	public Long getStandMaxWeight() {
		return standMaxWeight;
	}

	public void setStandMaxWeight(Long standMaxWeight) {
		this.standMaxWeight = standMaxWeight;
	}

	public Long getStandBridgesNumber() {
		return standBridgesNumber;
	}

	public void setStandBridgesNumber(Long standBridgesNumber) {
		this.standBridgesNumber = standBridgesNumber;
	}

	public String getStandBoardingType() {
		return standBoardingType;
	}

	public void setStandBoardingType(String standBoardingType) {
		this.standBoardingType = standBoardingType;
	}

	public String getStandTow() {
		return standTow;
	}

	public void setStandTow(String standTow) {
		this.standTow = standTow;
	}

	public String getRemoveFlag() {
		return removeFlag;
	}

	public void setRemoveFlag(String removeFlag) {
		this.removeFlag = removeFlag;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getBasicDataID() {
		return basicDataID;
	}

	public void setBasicDataID(String basicDataID) {
		this.basicDataID = basicDataID;
	}
	
	public Long getStandLength() {
		return standLength;
	}

	public void setStandLength(Long standLength) {
		this.standLength = standLength;
	}

	public Long getStandWidth() {
		return standWidth;
	}

	public void setStandWidth(Long standWidth) {
		this.standWidth = standWidth;
	}

//StandRefGate
	@Column(name = "STAND_REF_GATE", length = 5)
    private String standRefGate;

    public String getStandRefGate() {
        return standRefGate;
    }

    public void setStandRefGate(String standRefGate) {
        this.standRefGate = standRefGate;
    }
}
