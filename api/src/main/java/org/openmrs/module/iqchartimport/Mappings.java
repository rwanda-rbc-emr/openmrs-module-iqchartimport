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

import org.openmrs.GlobalProperty;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;

/**
 * Holds the mapping options
 */
public class Mappings {
	
	protected static Mappings mapping;
	
	protected int tracnetIDTypeId;
	protected String addressProvince;
	protected int hivProgramId;
	protected int encounterLocationId;
	
	/**
	 * The default constructor
	 */
	protected Mappings() {
		load(); 
	}
	
	/**
	 * Gets the singleton instance of this class
	 * @return the config instance
	 */
	public static Mappings getInstance() {
		if (mapping == null)
			mapping = new Mappings();
		return mapping;
	}
	
	/**
	 * Loads the options from global properties
	 */
	public void load() {
		tracnetIDTypeId = loadIntOption(Constants.PROP_TRACNET_ID_TYPE_ID, -1);
		addressProvince = loadStringOption(Constants.PROP_ADDRESS_PROVINCE, null);
		hivProgramId = loadIntOption(Constants.PROP_HIV_PROGRAM_ID, -1);
		encounterLocationId = loadIntOption(Constants.PROP_ENCOUNTER_LOCATION_ID, -1);
	}
	
	/**
	 * Saves the options to global properties
	 */
	public void save() {
		saveOption(Constants.PROP_TRACNET_ID_TYPE_ID, tracnetIDTypeId);
		saveOption(Constants.PROP_ADDRESS_PROVINCE, addressProvince);
		saveOption(Constants.PROP_HIV_PROGRAM_ID, hivProgramId);
		saveOption(Constants.PROP_ENCOUNTER_LOCATION_ID, encounterLocationId);
	}

	/**
	 * Gets the TRACnetID identifier type id
	 * @return the type id
	 */
	public int getTracnetIDTypeId() {
		return tracnetIDTypeId;
	}

	/**
	 * Sets the TRACnetID identifier type id
	 * @param tracnetIDTypeId the type id
	 */
	public void setTracnetIDTypeId(int tracnetIDTypeId) {
		this.tracnetIDTypeId = tracnetIDTypeId;
	}
	
	/**
	 * Gets the address province
	 * @return the province
	 */
	public String getAddressProvince() {
		return addressProvince;
	}

	/**
	 * Sets the address province
	 * @param addressProvince the province
	 */
	public void setAddressProvince(String addressProvince) {
		this.addressProvince = addressProvince;
	}
	
	/**
	 * Gets the HIV program id
	 * @return the program id
	 */
	public int getHivProgramId() {
		return hivProgramId;
	}

	/**
	 * Sets the HIV program id
	 * @param hivProgramId the program id
	 */
	public void setHivProgramId(int hivProgramId) {
		this.hivProgramId = hivProgramId;
	}
	
	/**
	 * Gets the encounter location id
	 * @return the location id
	 */
	public int getEncounterLocationId() {
		return encounterLocationId;
	}

	/**
	 * Sets the encounter location id
	 * @param encounterLocationId the location id
	 */
	public void setEncounterLocationId(int encounterLocationId) {
		this.encounterLocationId = encounterLocationId;
	}

	/**
	 * Utility method to load a string option from global properties
	 * @param name the name of the global property
	 * @param def the default value if global property is invalid
	 * @return the string value
	 */
	private static String loadStringOption(String name, String def) {
		AdministrationService svc = Context.getAdministrationService();
		String s = svc.getGlobalProperty(name);
		return (s != null) ? s : def;
	}

	/**
	 * Utility method to load an integer option from global properties
	 * @param name the name of the global property
	 * @param def the default value if global property is invalid
	 * @return the integer value
	 */
	private static int loadIntOption(String name, int def) {
		AdministrationService svc = (AdministrationService)Context.getAdministrationService();
		String s = svc.getGlobalProperty(name);
		try {
			return Integer.parseInt(s);
		}
		catch (NumberFormatException ex) {
			return def;
		}
	}
	
	/**
	 * Utility method to load an boolean option from global properties
	 * @param name the name of the global property
	 * @return the boolean value
	 */
	@SuppressWarnings("unused")
	private static boolean loadBooleanOption(String name, boolean def) {
		AdministrationService svc = (AdministrationService)Context.getAdministrationService();
		String s = svc.getGlobalProperty(name);
		try {
			return Boolean.parseBoolean(s);
		}
		catch (NumberFormatException ex) {
			return def;
		}
	}
	
	/**
	 * Utility method to save an option to global properties
	 * @param name the name of the global property
	 * @param value the value of the global property
	 */
	private static void saveOption(String name, Object value) {
		GlobalProperty property = Context.getAdministrationService().getGlobalPropertyObject(name);
		String val = value != null ? value.toString() : null;
				
		if (property == null)
			property = new GlobalProperty(name, val);
		else
			property.setPropertyValue(val);
		
		Context.getAdministrationService().saveGlobalProperty(property);
	}
}
