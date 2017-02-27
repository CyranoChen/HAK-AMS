package com.wonders.frame.ams.model.basic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wonders.frame.ams.model.ILongIdRemoveFlagModel;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

/*
* 收费项目
* */
@Entity
@Table(name = "CHARGE_SUBJECT")
public class ChargeSubject implements ILongIdRemoveFlagModel {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 收费性质
     */
    @Column(name = "CHARGE_PROPERTY", length = 20)
    private String chargeProperty;

    /**
     * 创建人
     */
    @Column(name = "CREATE_USER", length = 100)
    private String createUser;

    /**
     * 货币
     */
    @Column(name = "CURRENCY", length = 10)
    private String currency;

    /**
     * 货币单位
     */
    @Column(name = "CURRENCY_UNIT", length = 10)
    private String currencyUnit;

    /**
     * 描述
     */
    @Column(name = "DESCRIPTION", length = 100)
    private String description;

    /**
     * 公式
     */
    @Column(name = "FORMULA", length = 100)
    private String formula;

    /**
     * 修改人
     */
    @Column(name = "MODIFY_USER", length = 100)
    private String modifyUser;

    /**
     * 项目名称
     */
    @Column(name = "NAME", length = 100)
    private String name;

    /**
     * 公式参数
     */
    @Column(name = "PARAMETER", length = 100)
    private String parameter;

    /**
     * 逻辑删除标识位
     */
    @Column(name = "REMOVE_FLAG", length = 1)
    private String removeFlag;

    /**
     * 关联收费类型
     */
    @Column(name = "CHARGE_TYPE_ID", length = 20)
    private Integer chargeTypeId;

    /**
     * 关联收费规则
     */
    @Column(name = "CHARGE_RULE_ID", length = 20)
    private Long chargeRuleId;

    /**
     *  特殊规则标志；0：普通；1特殊
     */
    @Column(name = "SPECIAL_FLAG", length = 10)
    private String specialFlag;

    /**
     *  进港系数
     */
    @Column(name = "A_FEE_COE", length = 20)
    private Double afeeCoe;

    /**
     *  离港系数
     */
    @Column(name = "D_FEE_COE", length = 20)
    private Double dfeeCoe;

    /**
     *  单价
     */
    @Column(name = "UNIT_PRICE", length = 10)
    private Double unitPrice;

    /**
     *  编码
     */
    @Column(name = "SUBJECT_CODE", length = 100)
    private String subjectCode;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Temporal(TIMESTAMP)
    @Column(name = "CREATE_TIME")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Temporal(TIMESTAMP)
    @Column(name = "MODIFY_TIME")
    private Date modifyTime;

    // 需要通过表关联进行查询具体的收费类型是什么
    @Transient
    private String chargeType;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getChargeProperty() {
        return chargeProperty;
    }

    public void setChargeProperty(String chargeProperty) {
        this.chargeProperty = chargeProperty;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public String getRemoveFlag() {
        return removeFlag;
    }

    @Override
    public void setRemoveFlag(String removeFlag) {
        this.removeFlag = removeFlag;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Integer getChargeTypeId() {
        return chargeTypeId;
    }

    public void setChargeTypeId(Integer chargeTypeId) {
        this.chargeTypeId = chargeTypeId;
    }

    public Long getChargeRuleId() {
        return chargeRuleId;
    }

    public void setChargeRuleId(Long chargeRuleId) {
        this.chargeRuleId = chargeRuleId;
    }

    public String getSpecialFlag() {
        return specialFlag;
    }

    public void setSpecialFlag(String specialFlag) {
        this.specialFlag = specialFlag;
    }

    public Double getAfeeCoe() {
        return afeeCoe;
    }

    public void setAfeeCoe(Double afeeCoe) {
        this.afeeCoe = afeeCoe;
    }

    public Double getDfeeCoe() {
        return dfeeCoe;
    }

    public void setDfeeCoe(Double dfeeCoe) {
        this.dfeeCoe = dfeeCoe;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    @Override
    public String toString() {
        return "ChargeSubject{" +
                "id=" + id +
                ", currency='" + currency + '\'' +
                ", formula='" + formula + '\'' +
                ", name='" + name + '\'' +
                ", chargeTypeId=" + chargeTypeId +
                ", chargeRuleId=" + chargeRuleId +
                ", chargeType='" + chargeType + '\'' +
                '}';
    }
}

