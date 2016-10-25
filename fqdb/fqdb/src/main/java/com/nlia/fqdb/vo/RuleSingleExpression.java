package com.nlia.fqdb.vo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.Column;

/**
 * 规则原子表达式
 * @author shimingjie
 *
 */
@Entity
@Table(name = "RULE_SINGLE_EXPRESSION")
public class RuleSingleExpression implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	/* 字段名称 */
	@Column(length = 100)
	private String identity;
	/* 关系符 */
	@Column(length = 20)
	private String operator;
	/* 表达值 */
	@Column(name = "EXPRESSION_VALUE")
	private String expressionValue;
	/* 实体类 */
	@Column(name = "ENTITY_CLASS")
	private String entityClass;
	/* 有效标识 */
	@Column(name = "VALID_FLAG", length = 1)
	private String validFlag;
	/* 关联规则组 */
	@ManyToOne
	@JoinColumn(name = "RULE_GROUP_ID", referencedColumnName = "id")
	private RuleGroup ruleGroup;
	
	public static enum EXPRESSION_VALID {
		INVALID("0"), VALID("1");
		
		private final String value;

		public String getValue() {
			return this.value;
		}

		EXPRESSION_VALID(String value) {
			this.value = value;
		}
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getExpressionValue() {
		return expressionValue;
	}
	public void setExpressionValue(String expressionValue) {
		this.expressionValue = expressionValue;
	}
	public RuleGroup getRuleGroup() {
		return ruleGroup;
	}
	public void setRuleGroup(RuleGroup ruleGroup) {
		this.ruleGroup = ruleGroup;
	}
	public String getEntityClass() {
		return entityClass;
	}
	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}
	public String getValidFlag() {
		return validFlag;
	}
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}

}
