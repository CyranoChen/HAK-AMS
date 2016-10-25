package com.nlia.obcs.vo;

import static javax.persistence.FetchType.EAGER;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.nlia.obcs.entity.AlarmModeAndStaffRelation;
import com.nlia.obcs.entity.AlarmType;

@Entity
@Table(name = "ALARM_MODE")
public class AlarmMode implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	public AlarmMode(){
		
	}
	
	public AlarmMode(BigDecimal id, String modePara, String pattern, String status){
		this.id =  id.longValue();
		this.modePara = modePara;
		this.pattern = pattern;
		this.status = status;
	}
	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "MODE_PARA", length = 999)
	//告警细则
	private String modePara;
	
	@Column(name = "PATTERN")
	//告警方式
	private String pattern;
	
	@Column(name = "STATUS",length = 1)
	//有效标志
	private String status;
	
	@ManyToOne
	private AlarmType type;
	
	@OneToMany(fetch = EAGER,mappedBy = "mode")
	private Set<AlarmModeAndStaffRelation> alarmModeAndStaffRelations;
	
	
	public static enum MODE_STATUS {
		DISABLED("0"), ENABLED("1");

		private final String value;

		/**
		 * @return the value
		 */
		public String getValue() {
			return value;
		}

		MODE_STATUS(String value) {
			this.value = value;
		}
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getModePara() {
		return modePara;
	}
	public void setModePara(String modePara) {
		this.modePara = modePara;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public AlarmType getType() {
		return type;
	}

	public void setType(AlarmType type) {
		this.type = type;
	}

	public Set<AlarmModeAndStaffRelation> getAlarmModeAndStaffRelations() {
		return alarmModeAndStaffRelations;
	}

	public void setAlarmModeAndStaffRelations(Set<AlarmModeAndStaffRelation> alarmModeAndStaffRelations) {
		this.alarmModeAndStaffRelations = alarmModeAndStaffRelations;
	}
	
}
