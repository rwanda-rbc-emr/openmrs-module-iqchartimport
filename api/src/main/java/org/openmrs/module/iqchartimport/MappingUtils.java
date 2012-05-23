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
	 * Gets a concept by its ID
	 * @param identifier the concept ID or name
	 * @return the concept
	 * @throws IncompleteMappingException if there is no such concept
	 */
	public static Concept getConcept(Integer conceptId) {
		Concept concept = Context.getConceptService().getConcept(conceptId);
		if (concept == null) 
			throw new IncompleteMappingException("Missing concept with id " + conceptId);
		
		return concept;
	}
	
	/**
	 * Gets a concept by its name
	 * @param identifier the concept name
	 * @return the concept
	 * @throws IncompleteMappingException if there is no such concept
	 */
	public static Concept getConcept(String name) {
		Concept concept = Context.getConceptService().getConcept(name);
		if (concept == null) 
			throw new IncompleteMappingException("Missing '" + name + "' concept");
		
		return concept;
	}
}
