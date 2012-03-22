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

/**
 * Patient WHO AIDS stage code
 */
public enum WHOStageCode {
	
	STAGE_1 ("WHO STAGE 1 PEDS|WHO STAGE 1 ADULT"),
	STAGE_2 ("WHO STAGE 2 PEDS|WHO STAGE 2 ADULT"),
	STAGE_3 ("WHO STAGE 3 PEDS|WHO STAGE 3 ADULT"),
	STAGE_4 ("WHO STAGE 4 PEDS|WHO STAGE 4 ADULT");
	
	public static final String mappedQuestion = "WHO STAGE";
	public final String mappedAnswer;
	
	/**
	 * Constructs new enum value
	 * @param mappedAnswer the mapped answer
	 */
	WHOStageCode(String mappedAnswer) {
		this.mappedAnswer = mappedAnswer;
	}
	
	/**
	 * Converts a string value to an enum value
	 * @param val the string value
	 * @return the enum value or null if byte value is null
	 */
	public static WHOStageCode fromString(String val) {
		try {
			int value = Integer.parseInt(val);
			return values()[value - 1];
		}
		catch (NumberFormatException ex) {
			return null;
		}
	}
}
