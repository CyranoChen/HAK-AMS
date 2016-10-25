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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

import com.nlia.fqdb.entity.base.Airline;

import static javax.persistence.FetchType.EAGER;

/**
 * @author shimingjie 收费对象
 * 
 */
@Entity
@Table(name = "CHARGE_TARGET")
public class ChargeTarget implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	/**
	 * 航空公司
	 */
	@OneToOne
	private Airline airline;

	/**
	 * 航空公司分公司
	 */
	@OneToOne
	private Airline subairline;

	/**
	 * 收费项目集合
	 */
	@ManyToMany(fetch = EAGER)
	@JoinTable(joinColumns = @JoinColumn(name = "CHARGE_TARGET_ID", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "CHARGE_SUBJECT_ID", referencedColumnName = "id"), name = "CHARGE_TARGET_X_CHARGE_SUBJECT")
	private Set<ChargeSubject> chargeSubjects;

	@Column(length = 1, name = "REMOVE_FLAG")
	private String removeFlag;

	@Column(name = "CREATE_USER")
	private String createUser;

	@Temporal(TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;

	@Column(name = "MODIFY_USER")
	private String modifyUser;

	@Column(name = "MODIFY_TIME")
	@Temporal(TIMESTAMP)
	private Date modifyTime;



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public Airline getSubairline() {
		return subairline;
	}

	public void setSubairline(Airline subairline) {
		this.subairline = subairline;
	}

	public Set<ChargeSubject> getChargeSubjects() {
		return chargeSubjects;
	}

	public void setChargeSubjects(Set<ChargeSubject> chargeSubjects) {
		this.chargeSubjects = chargeSubjects;
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

}
