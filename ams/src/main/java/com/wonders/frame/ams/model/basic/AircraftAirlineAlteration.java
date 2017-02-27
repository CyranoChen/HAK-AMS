package com.wonders.frame.ams.model.basic;

import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wonders.frame.ams.model.ILongIdRemoveFlagModel;

@Entity
@Table( name = "AIRCRAFT_AIRLINE_ALTERATION")
public class AircraftAirlineAlteration implements ILongIdRemoveFlagModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	/**
	 *  飞机号
	 */
	@Column(name = "AIRCRAFT_REGISTRATION", length = 10)
	private String aircraftRegistration;

	/**
	 * 原属航空公司
	 */
	@Column(name = "ORIGINAL_AIRLINE_ID", length = 20)
	private Long originalAirline;

	/**
	 * 原属航空分公司
	 */
	@Column(name = "ORIGINAL_SUBAIRLINE_ID", length = 20)
	private Long originalSubairline;

	/**
	 * 现属航空公司
	 */
	@Column(name = "CURRENT_AIRLINE_ID", length = 20)
	private Long currentAirline;

	/**
	 * 现属航空分公司
	 */
	@Column(name = "CURRENT_SUBAIRLINE_ID", length = 20)
	private Long currentSubairline;
	
	@Column(name="AIRLINE_OF_FLIGHT",length = 3)
	private String airlineOfFlight;

	/**
	 * 开始日期
	 */
	@Temporal(DATE)
	@Column(name = "START_DATE")
	private Date startDate;

	/**
	 * 结束日期
	 */
	@Temporal(DATE)
	@Column(name = "END_DATE")
	private Date endDate;
	
	/**
	 * 有效标识
	 */
	@Column(name = "VALID_FLAG", length = 1)
	private String validFlag;


	private String remark;
	
//	public static enum VALIDFLAG {
//		INVALID("0"), VALID("1");
//
//		private final String value;
//
//		public String getValue() {
//			return this.value;
//		}
//
//		VALIDFLAG(String value) {
//			this.value = value;
//		}
//	}

	@Column(name = "REMOVE_FLAG", length = 1)
	private String removeFlag;

	@Column(name = "CREATE_USER")
	private String createUser;
	
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@Temporal(TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;
	
	@Column(name = "MODIFY_USER")
	private String modifyUser;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	@Temporal(TIMESTAMP)
	@Column(name = "MODIFY_TIME")
	private Date modifyTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAircraftRegistration() {
		return aircraftRegistration;
	}

	public void setAircraftRegistration(String aircraftRegistration) {
		this.aircraftRegistration = aircraftRegistration;
	}

	public Long getOriginalAirline() {
		return originalAirline;
	}

	public void setOriginalAirline(Long originalAirline) {
		this.originalAirline = originalAirline;
	}

	public Long getOriginalSubairline() {
		return originalSubairline;
	}

	public void setOriginalSubairline(Long originalSubairline) {
		this.originalSubairline = originalSubairline;
	}

	public Long getCurrentAirline() {
		return currentAirline;
	}

	public void setCurrentAirline(Long currentAirline) {
		this.currentAirline = currentAirline;
	}

	public Long getCurrentSubairline() {
		return currentSubairline;
	}

	public void setCurrentSubairline(Long currentSubairline) {
		this.currentSubairline = currentSubairline;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getAirlineOfFlight() {
		return airlineOfFlight;
	}

	public void setAirlineOfFlight(String airlineOfFlight) {
		this.airlineOfFlight = airlineOfFlight;
	}
	
	/*
	 * 以下为Aircraft机号内容
	 */
	///////////////////////////////////////////////////////////////////////
	/**
     * 租赁航空公司
     */
    @Column(name = "AIRCRAFT_LEASING_AIRLINE", length = 10)
    private String aircraftLeasingAirline;
    
    /**
     * 飞机类型三字码
     */
    @Column(name = "AIRCRAFT_TYPE_ICAO_CODE", length = 10)
    private String aircraftTypeICAOCode;
    
    /**
     * 飞机类型二字码
     */
    @Column(name = "AIRCRAFT_TYPE_IATA_CODE", length = 10)
    private String aircraftTypeIATACode;


    /**
     * 飞机类型
     */
    @Column(name = "AIRCRAFT_TYPE", length = 10)
    private String aircraftType;

    /**
     * 所属航空公司
     */
    @Column(name = "AIRCRAFT_AIRLINE", length = 10)
    private String aircraftAirline;

    /**
     * 飞机描述说明
     */
    @Column(name = "AIRCRAFT_DESCRIPTION", length = 100)
    private String aircraftDescription;

    /**
     * 飞机引擎数量
     */
    @Column(name = "ARICRAFT_ENGINE_NUMBER", length = 2)
    private Long aircraftEngineNumber;

    /**
     * 最大起飞重量
     */
    @Column(name = "AIRCRAFT_TAKEOFF_WEIGHT", length = 6)
    private Long aircraftTakeoffWeight;

    /**
     * 最大落地重量
     */
    @Column(name = "AIRCRAFT_LANDING_WEIGHT", length = 6)
    private Long aircraftLandingWeight;

    /**
     * 最大配载数
     */
    @Column(name = "AIRCRAFT_PAYLOAD", length = 6)
    private Long aircraftPayload;

    /**
     * 最大客座数
     */
    @Column(name = "AIRCRAFT_SEAT_CAPACITY", length = 3)
    private Long aircraftSeatCapacity;

    /**
     * 噪音级别
     */
    @Column(name = "AIRCRAFT_NOISE_CATEGORY", length = 2)
    private String aircraftNoiseCategory;

    /**
     * 飞机高度
     */
    @Column(name = "AIRCRAFT_HEIGHT", length = 12)
    private Long aircraftHeight;

    /**
     * 飞机长度
     */
    @Column(name = "AIRCRAFT_LENGHT", length = 12)
    private Long aircraftLength;

    /**
     * 飞机宽度
     */
    @Column(name = "AIRCRAFT_WIDTH", length = 12)
    private Long aircraftWidth;
    
    // adder:zhuhaijian,20130701
    @Column(name = "Basic_Data_ID", length = 20)
    private String basicDataID;

    @Transient
    private String verifyDescription;
    
//    @Transient
//    private Map<Integer,List<Integer>> errorMessage;

    public String getAircraftLeasingAirline() {
        return aircraftLeasingAirline;
    }

    public void setAircraftLeasingAirline(String aircraftLeasingAirline) {
        this.aircraftLeasingAirline = aircraftLeasingAirline;
    }

    public String getAircraftTypeICAOCode() {
        return aircraftTypeICAOCode;
    }

    public void setAircraftTypeICAOCode(String aircraftTypeICAOCode) {
        this.aircraftTypeICAOCode = aircraftTypeICAOCode;
    }

    public String getAircraftTypeIATACode() {
        return aircraftTypeIATACode;
    }

    public void setAircraftTypeIATACode(String aircraftTypeIATACode) {
        this.aircraftTypeIATACode = aircraftTypeIATACode;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    public String getAircraftAirline() {
        return aircraftAirline;
    }

    public void setAircraftAirline(String aircraftAirline) {
        this.aircraftAirline = aircraftAirline;
    }

    public String getAircraftDescription() {
        return aircraftDescription;
    }

    public void setAircraftDescription(String aircraftDescription) {
        this.aircraftDescription = aircraftDescription;
    }

    public Long getAircraftEngineNumber() {
        return aircraftEngineNumber;
    }

    public void setAircraftEngineNumber(Long aircraftEngineNumber) {
        this.aircraftEngineNumber = aircraftEngineNumber;
    }

    public Long getAircraftTakeoffWeight() {
        return aircraftTakeoffWeight;
    }

    public void setAircraftTakeoffWeight(Long aircraftTakeoffWeight) {
        this.aircraftTakeoffWeight = aircraftTakeoffWeight;
    }

    public Long getAircraftLandingWeight() {
        return aircraftLandingWeight;
    }

    public void setAircraftLandingWeight(Long aircraftLandingWeight) {
        this.aircraftLandingWeight = aircraftLandingWeight;
    }

    public Long getAircraftPayload() {
        return aircraftPayload;
    }

    public void setAircraftPayload(Long aircraftPayload) {
        this.aircraftPayload = aircraftPayload;
    }

    public Long getAircraftSeatCapacity() {
        return aircraftSeatCapacity;
    }

    public void setAircraftSeatCapacity(Long aircraftSeatCapacity) {
        this.aircraftSeatCapacity = aircraftSeatCapacity;
    }

    public String getAircraftNoiseCategory() {
        return aircraftNoiseCategory;
    }

    public void setAircraftNoiseCategory(String aircraftNoiseCategory) {
        this.aircraftNoiseCategory = aircraftNoiseCategory;
    }

    public Long getAircraftHeight() {
        return aircraftHeight;
    }

    public void setAircraftHeight(Long aircraftHeight) {
        this.aircraftHeight = aircraftHeight;
    }

    public Long getAircraftLength() {
        return aircraftLength;
    }

    public void setAircraftLength(Long aircraftLength) {
        this.aircraftLength = aircraftLength;
    }

    public Long getAircraftWidth() {
        return aircraftWidth;
    }

    public void setAircraftWidth(Long aircraftWidth) {
        this.aircraftWidth = aircraftWidth;
    }

    public String getBasicDataID() {
        return basicDataID;
    }

    public void setBasicDataID(String basicDataID) {
        this.basicDataID = basicDataID;
    }

    public String getVerifyDescription() {
        return verifyDescription;
    }

    public void setVerifyDescription(String verifyDescription) {
        this.verifyDescription = verifyDescription;
    }

//    public Map<Integer, List<Integer>> getErrorMessage() {
//        return errorMessage;
//    }
//
//    public void setErrorMessage(Map<Integer, List<Integer>> errorMessage) {
//        this.errorMessage = errorMessage;
//    }
    ////////////////////////////////////////////////////////
    
    /*
     * 宽窄体标志
     */
    @Column(name = "IS_WIDE_OR_NARROW", length = 1)
    private String isWideOrNarrow;
    
    public String getIsWideOrNarrow() {
		return isWideOrNarrow;
	}

	public void setIsWideOrNarrow(String isWideOrNarrow) {
		this.isWideOrNarrow = isWideOrNarrow;
	}

	/*
	 * 高密度座位标志 现用作是否客座率打折
	 */
	@Column(name = "IS_HIGH_DENSITY", length = 1)
	private String isHighDensity;

    public String getIsHighDensity() {
        return isHighDensity;
    }

    public void setIsHighDensity(String isHighDensity) {
        this.isHighDensity = isHighDensity;
    }
    
    /*
     * 是否集装设备
     */
    @Column(name="IS_PACKAGING_FACILITY", length = 1)
    private String isPackagingFacility;

    public String getIsPackagingFacility() {
        return isPackagingFacility;
    }

    public void setIsPackagingFacility(String isPackagingFacility) {
        this.isPackagingFacility = isPackagingFacility;
    }
    
//    @OneToMany(mappedBy = "aircraftAirlineAlteration", cascade = ALL, fetch = EAGER)
//    private Set<Discount> discounts;
//
//    public Set<Discount> getDiscounts() {
//        return discounts;
//    }
//
//    public void setDiscounts(Set<Discount> discounts) {
//        this.discounts = discounts;
//    }
  //折扣
}
