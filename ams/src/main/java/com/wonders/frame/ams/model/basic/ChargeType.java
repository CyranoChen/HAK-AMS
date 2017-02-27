package com.wonders.frame.ams.model.basic;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wonders.frame.ams.model.ILongIdRemoveFlagModel;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

/**
 * 收费类型
 * */
@Entity
@Table(name = "CHARGE_TYPE")
public class ChargeType implements ILongIdRemoveFlagModel {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 创建人
     */
    @Column(name = "CREATE_USER", length = 10)
    private String createUser;

    /**
     * 修改人
     */
    @Column(name = "MODIFY_USER", length = 10)
    private String modifyUser;

    /**
     * 收费类型名称
     */
    @Column(name = "NAME", length = 10)
    private String name;

    /**
     * 逻辑删除标识位
     */
    @Column(name = "REMOVE_FLAG", length = 1)
    private String removeFlag;

    /**
     * 收费类型编码
     */
    @Column(name = "CHARGE_CODE", length = 10)
    private String chargeCode;

    /**
     * 备注
     */
    @Column(name = "REMK", length = 10)
    private String remk;

    @Column(name = "SORT_TYPE", length = 10)
    private String sortType;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Temporal(TIMESTAMP)
    @Column(name = "CREATE_TIME")
    private Date createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Temporal(TIMESTAMP)
    @Column(name = "MODIFY_TIME")
    private Date modifyTime;

    /*
        页面附加栏: 收费科目计数
     */
    @Transient
    private Integer subjectCount;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getRemoveFlag() {
        return removeFlag;
    }

    @Override
    public void setRemoveFlag(String removeFlag) {
        this.removeFlag = removeFlag;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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

    public String getChargeCode() {
        return chargeCode;
    }

    public void setChargeCode(String chargeCode) {
        this.chargeCode = chargeCode;
    }

    public String getRemk() {
        return remk;
    }

    public void setRemk(String remk) {
        this.remk = remk;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
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

    public Integer getSubjectCount() {
        return subjectCount;
    }

    public void setSubjectCount(Integer subjectCount) {
        this.subjectCount = subjectCount;
    }

    @Override
    public String toString() {
        return "ChargeType{" +
                "id=" + id +
                ", subjectCount=" + subjectCount +
                ", name='" + name + '\'' +
                ", chargeCode='" + chargeCode + '\'' +
                '}';
    }
}
