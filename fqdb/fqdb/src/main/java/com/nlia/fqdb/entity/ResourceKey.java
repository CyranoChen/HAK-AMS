package com.nlia.fqdb.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.nlia.fqdb.util.AbstractPersistable;

@Entity
@Table(name = "RESOURCE_KEY")
public class ResourceKey extends AbstractPersistable<Long> {

	private static final long serialVersionUID = 1L;

	/**
	 * 资源类型
	 */
	@Column(name = "RESOURCE_CATEGORY", length = 50)
	private String ResourceCategory;
	
	/**
	 * 资源ID号
	 */
	@Column(name = "RESOURCE_ID", length = 50)
	private Long ResourceID;

	public String getResourceCategory() {
		return ResourceCategory;
	}

	public void setResourceCategory(String resourceCategory) {
		ResourceCategory = resourceCategory;
	}

	public Long getResourceID() {
		return ResourceID;
	}

	public void setResourceID(Long resourceID) {
		ResourceID = resourceID;
	}
	
}
