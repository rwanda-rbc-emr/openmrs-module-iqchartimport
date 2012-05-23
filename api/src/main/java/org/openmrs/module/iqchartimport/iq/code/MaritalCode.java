/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.iqchartimport.iq.code;

import org.openmrs.module.iqchartimport.Dictionary;

/**
 * Patient marital status code
 */
public enum MaritalCode {
	
	SINGLE ("SINGLE OR A CHILD"),
	MARRIED ("MARRIED"),
	WIDOWED ("WIDOWED"),
	DIVORCED ("DIVORCED"),
	LIVINGWITHPARTNER ("LIVING WITH PARTNER"),
	OTHER (Dictionary.UNKNOWN);
	
	public static final Integer mappedQuestion = Dictionary.CIVIL_STATUS;
	public final Object mappedAnswer;
	
	/**
	 * Constructs new enum value
	 * @param mappedAnswer the mapped answer
	 */
	MaritalCode(Object mappedAnswer) {
		this.mappedAnswer = mappedAnswer;
	}
	
	/**
	 * Converts a Byte value to an enum value
	 * @param val the Byte value
	 * @return the enum value or null if byte value is null
	 */
	public static MaritalCode fromByte(Byte val) {
		return val != null ? values()[val] : null;
	}
}
