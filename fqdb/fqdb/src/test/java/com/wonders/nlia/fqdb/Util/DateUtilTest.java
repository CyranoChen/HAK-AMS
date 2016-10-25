package com.wonders.nlia.fqdb.Util;

import junit.framework.Assert;

import org.junit.Test;

import com.nlia.fqdb.util.DateUtils;

public class DateUtilTest {

	
	@Test
	public void testIsValidDate(){
		Assert.assertTrue(DateUtils.isValidDate(null, ""));
	}
}
