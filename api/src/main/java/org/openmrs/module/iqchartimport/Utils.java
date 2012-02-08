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

import org.openmrs.api.APIAuthenticationException;
import org.openmrs.api.context.Context;

/**
 * General utility functions
 */
public class Utils {
	/**
	 * Checks that the current user is a super-user
	 * @throws APIAuthenticationException if user is not authenticated or a super-user
	 */
	public static void checkSuperUser() throws APIAuthenticationException {
		if (!Context.isAuthenticated() || !Context.getUserContext().getAuthenticatedUser().isSuperUser())
			throw new APIAuthenticationException("Must be super-user");
	}
}
