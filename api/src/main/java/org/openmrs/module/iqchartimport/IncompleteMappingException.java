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

package org.openmrs.module.iqchartimport;

import org.openmrs.api.APIException;

/**
 * Exception class thrown if user tries to run import before configuring module 
 */
public class IncompleteMappingException extends APIException {

	private static final long serialVersionUID = -6015638770411118185L;
	
	/**
	 * Default constructor
	 */
	public IncompleteMappingException() {
	}
	
	/**
	 * Creates an exception with a message
	 * @param message the message
	 */
	public IncompleteMappingException(String message) {
		super(message);
	}
}
