/**
 * Copyright 2009 Jason Hewitt
 */
package org.jaspa.eternity2.util;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ObjectToStringFormatter {

	public static String objectToString(Object o) {
		return ToStringBuilder.reflectionToString(o, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
