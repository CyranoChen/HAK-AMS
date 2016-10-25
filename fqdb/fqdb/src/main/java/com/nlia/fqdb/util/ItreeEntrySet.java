package com.nlia.fqdb.util;

import java.util.Set;

public interface ItreeEntrySet<T> {
	public String getName();
	public void setName(String name);
	public void setChildren(Set<T> children);
	public Set<T> getChildren();
	public String getRemoveFlag();
	public void setRemoveFlag(String removeFlag);
	public void removeChild(T child);
	public Long getId();
}