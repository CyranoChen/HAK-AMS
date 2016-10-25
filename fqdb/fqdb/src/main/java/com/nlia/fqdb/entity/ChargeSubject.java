package com.nlia.fqdb.entity;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

import com.nlia.fqdb.vo.RuleGroup;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.CascadeType.ALL;

/**
 * @author shimingjie 收费项目
 * 
 */
@Entity
@Table(name = "CHARGE_SUBJECT")
public class ChargeSubject implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	/**
	 * 项目名称
	 */
	@Column(length = 200)
	private String name;

	/**
	 * 描述
	 */
	@Column(length = 255)
	private String description;

	/**
	 * 收费性质（枚举，航空性业务收费、非航空性业务重要收费、非航空性业务其他收费）
	 */
	@Column(length = 20, name = "CHARGE_PROPERTY")
	private CHARGE_PROPERTY chargeProperty;

	/**
	 * 公式 （如 2000 + （100T - 30）* T，参数全用T表示，有几个T必须配备几个参数）
	 */
	@Column(length = 255)
	private String formula;

	/**
	 * 参数 （枚举，如：货物邮件重量、飞机最大起飞全重等)
	 */
	@Column(length = 200)
	private String parameter;

	/**
	 * 货币
	 */
	@Column(length = 10)
	private String currency;

	/**
	 * 货币单位（如：元，万元等）
	 */
	@Column(length = 10, name = "CURRENCY_UNIT")
	private String currencyUnit;

	/**
	 * 收费规则
	 */

	@OneToOne
	@JoinColumn(name = "CHARGE_RULE_ID", referencedColumnName = "id")
	private RuleGroup chargeRule;

	/**
	 * 折扣规则
	 */
	
//	@OneToMany(mappedBy = "chargeSubject", fetch = EAGER, cascade = ALL)
//	private Set<ChargeSubjectAndDiscountRuleRelation> subjectAndDiscountRuleRelations;

	/**
	 * 收费类型
	 */
	@ManyToOne
	@JoinColumn(name = "CHARGE_TYPE_ID", referencedColumnName = "id")
	private ChargeType chargeType;

	@Column(name = "REMOVE_FLAG", length = 1)
	private String removeFlag;

	@Column(name = "CREATE_USER")
	private String createUser;

	@Column(name = "CREATE_TIME")
	@Temporal(TIMESTAMP)
	private Date createTime;

	@Column(name = "MODIFY_USER")
	private String modifyUser;

	@Column(name = "MODIFY_TIME")
	@Temporal(TIMESTAMP)
	private Date modifyTime;

	// 航空性业务收费、非航空性业务重要收费、非航空性业务其他收费
	public static enum CHARGE_PROPERTY {
		FLIGHT_BUSINESS_CHARGE("0"), NONFLIGHT_IMPORTANT_BUSINESS_CHARGE("1"), NONFLIGHT_OTHER_BUSINESS_CHARGE("2");
		private final String value;

		public String getValue() {
			return this.value;
		}

		CHARGE_PROPERTY(String value) {
			this.value = value;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CHARGE_PROPERTY getChargeProperty() {
		return chargeProperty;
	}

	public void setChargeProperty(CHARGE_PROPERTY chargeProperty) {
		this.chargeProperty = chargeProperty;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyUnit() {
		return currencyUnit;
	}

	public void setCurrencyUnit(String currencyUnit) {
		this.currencyUnit = currencyUnit;
	}

	public RuleGroup getChargeRule() {
		return chargeRule;
	}

	public void setChargeRule(RuleGroup chargeRule) {
		this.chargeRule = chargeRule;
	}

	public ChargeType getChargeType() {
		return chargeType;
	}

	public void setChargeType(ChargeType chargeType) {
		this.chargeType = chargeType;
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

//	public Set<ChargeSubjectAndDiscountRuleRelation> getSubjectAndDiscountRuleRelations() {
//		return subjectAndDiscountRuleRelations;
//	}
//
//	public void setSubjectAndDiscountRuleRelations(Set<ChargeSubjectAndDiscountRuleRelation> subjectAndDiscountRuleRelations) {
//		this.subjectAndDiscountRuleRelations = subjectAndDiscountRuleRelations;
//	}
	//add by march 20151130 特殊规则标志；0：普通；1特殊
	@Column(name = "SPECIAL_FLAG")
	private String specialFlag;
    public String getSpecialFlag() {
        return specialFlag;
    }

    public void setSpecialFlag(String specialFlag) {
        this.specialFlag = specialFlag;
    }
    
    /*
     * 进港系数
     */
    @Column(name = "A_FEE_COE")
    private double aFeeCoe;
    
	/*
	 * 离港系数
	 */
    @Column(name = "D_FEE_COE")
    private double dFeeCoe;
    public double getaFeeCoe() {
        return aFeeCoe;
    }

    public void setaFeeCoe(double aFeeCoe) {
        this.aFeeCoe = aFeeCoe;
    }

    public double getdFeeCoe() {
        return dFeeCoe;
    }

    public void setdFeeCoe(double dFeeCoe) {
        this.dFeeCoe = dFeeCoe;
    }
    /*
     * 单价
     */
    @Column(name = "UNIT_PRICE")
    private double unitPrice;
    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    /*
     * 代码
     */
    @Column(name = "SUBJECT_CODE")
    private String subjectCode;
    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
    
}
