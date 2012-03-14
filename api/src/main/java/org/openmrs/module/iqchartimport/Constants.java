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

/**
 * Module constants
 */
public class Constants {
	
	public static final String MODULE_ID = "iqchartimport";
	public static final String SESSION_ATTR_DATABASE = "iqchartimport_database";
	public static final String NEW_TRACNET_ID_TYPE_NAME = "TRACnet ID";
	public static final String NEW_TRACNET_ID_TYPE_FORMAT = "\\d{6}";
	public static final String NEW_TRACNET_ID_TYPE_FORMAT_DESC = "Six digit number (123456)";
	public static final int ADULT_START_AGE = 15;
	
	/**
	 * Global properties
	 */
	public static final String PROP_TRACNET_ID_TYPE_ID = MODULE_ID + ".tracnetIDTypeId";
	public static final String PROP_ADDRESS_COUNTRY = MODULE_ID + ".addressCountry";
	public static final String PROP_ADDRESS_PROVINCE = MODULE_ID + ".addressProvince";
	public static final String PROP_ADDRESS_ALL_PROVINCES = MODULE_ID + ".addressAllProvinces";
	public static final String PROP_HIV_PROGRAM_ID = MODULE_ID + ".hivProgramId";
	public static final String PROP_ENCOUNTER_LOCATION_ID = MODULE_ID + ".encounterLocationId";
}
