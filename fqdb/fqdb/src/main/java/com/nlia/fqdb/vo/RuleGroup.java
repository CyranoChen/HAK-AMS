package com.nlia.fqdb.vo;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

/**
 * 规则组
 * 
 * @author shimingjie
 * 
 */
@Entity
@Table(name = "RULE_GROUP")
public class RuleGroup implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	/* 连接符 */
	@Column(length = 20)
	private String connector;
	/* 服务规则表达式 */
	@OneToMany(mappedBy = "ruleGroup", cascade = ALL, fetch = EAGER)
	private Set<RuleSingleExpression> ruleSingleExpressions;
	/* 服务规则组 */
	@OneToMany(mappedBy = "parentRuleGroup", cascade = ALL, fetch = EAGER)
	private Set<RuleGroup> ruleGroups;
	@ManyToOne(cascade = ALL)
	@JoinColumn(name = "PARENT_RULE_GROUP_ID", referencedColumnName = "id")
	private RuleGroup parentRuleGroup;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getConnector() {
		return connector;
	}
	public void setConnector(String connector) {
		this.connector = connector;
	}
	public Set<RuleSingleExpression> getRuleSingleExpressions() {
		return ruleSingleExpressions;
	}
	public void setRuleSingleExpressions(Set<RuleSingleExpression> ruleSingleExpressions) {
		this.ruleSingleExpressions = ruleSingleExpressions;
	}
	public Set<RuleGroup> getRuleGroups() {
		return ruleGroups;
	}
	public void setRuleGroups(Set<RuleGroup> ruleGroups) {
		this.ruleGroups = ruleGroups;
	}
	public RuleGroup getParentRuleGroup() {
		return parentRuleGroup;
	}
	public void setParentRuleGroup(RuleGroup parentRuleGroup) {
		this.parentRuleGroup = parentRuleGroup;
	}
	
}
