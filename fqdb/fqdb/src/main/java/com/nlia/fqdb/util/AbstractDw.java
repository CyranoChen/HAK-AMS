package com.nlia.fqdb.util;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class AbstractDw<ID extends Serializable> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private ID id;

	// 生成时间
	@Column(name = "CREATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

	// 排序号
	@Column(name = "SORT_NUMBER")
	private String sortNumber;

	// 生成次数
	@Column(name = "GENERATE_NUMBER")
	private Integer generateNumber;

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSortNumber() {
		return sortNumber;
	}

	public void setSortNumber(String sortNumber) {
		this.sortNumber = sortNumber;
	}

	public Integer getGenerateNumber() {
		return generateNumber;
	}

	public void setGenerateNumber(Integer generateNumber) {
		this.generateNumber = generateNumber;
	}

}
