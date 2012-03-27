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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.EncounterType;
import org.openmrs.Person;
import org.openmrs.PersonName;
import org.openmrs.api.context.Context;

/**
 * Mapping utility functions
 */
public class MappingUtils {
	
	protected static final Log log = LogFactory.getLog(MappingUtils.class);
	
	/**
	 * Gets the provider person for all encounters
	 * @return the provider or null if it doesn't exist
	 */
	public static Person getEncounterProvider() {
		return Context.getPersonService().getPersonByUuid(Constants.IQCHART_PROVIDER_UUID.toString());
	}
	
	/**
	 * Creates the provider person for all encounters
	 */
	public static void createEncounterProvider() {
		Person provider = new Person();
		provider.addName(new PersonName("IQChart", null, "Provider"));
		provider.setUuid(Constants.IQCHART_PROVIDER_UUID.toString());
		provider.setGender("");
		Context.getPersonService().savePerson(provider);
	}

	/**
	 * Gets an encounter type by name
	 * @param name the encounter type name
	 * @return the encounter type
	 * @throws IncompleteMappingException if encounter type doesn't exist
	 */
	public static EncounterType getEncounterType(String name) {
		EncounterType encType = Context.getEncounterService().getEncounterType(name);
		if (encType == null) 
			throw new IncompleteMappingException("Missing '" + name + "' encounter type");
		
		return encType;
	}

	/**
	 * Gets a concept by id, name or global property
	 * @param identifier the id, name or global property
	 * @return the concept
	 * @throws IncompleteMappingException if concept doesn't exist
	 */
	public static Concept getConcept(Object identifier) {
		// If name is null, return null
		if (identifier == null)
			return null;
		
		if (identifier instanceof Integer)
			return getConceptById((Integer)identifier);
		
		String str = (String)identifier;
			
		// If string is prefixed with @ then it's a global property
		if (str.startsWith("@")) {
			String property = str.substring(1);
			String propVal = Context.getAdministrationService().getGlobalProperty(property);
			
			if (propVal == null || propVal.length() == 0) 
				throw new IncompleteMappingException("Missing '" + property + "' global property");
			
			try {
				return getConceptById(Integer.parseInt(propVal));
			}
			catch (NumberFormatException ex) {
				throw new IncompleteMappingException("Invalid '" + property + "' global property");
			}
		}

		Concept concept = Context.getConceptService().getConcept(str);
		if (concept == null) 
			throw new IncompleteMappingException("Missing '" + str + "' concept");
		
		return concept;
	}
	
	/**
	 * 
	 * @param conceptId
	 * @return
	 */
	private static Concept getConceptById(int conceptId) {
		Concept concept = Context.getConceptService().getConcept(conceptId);
		if (concept == null) 
			throw new IncompleteMappingException("Missing " + conceptId + " concept");
		
		return concept;
	}
}
