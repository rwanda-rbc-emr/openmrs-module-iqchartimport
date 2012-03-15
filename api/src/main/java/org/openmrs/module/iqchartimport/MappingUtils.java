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
import org.openmrs.api.context.Context;

/**
 * Mapping utility functions
 */
public class MappingUtils {
	
	protected static final Log log = LogFactory.getLog(MappingUtils.class);

	/**
	 * Gets an encounter type by name
	 * @param name the encounter type name
	 * @return the encounter type
	 * @throws IncompleteMappingException if encounter type doesn't exist
	 */
	public static EncounterType getEncounterTypeByName(String name) {
		EncounterType encType = Context.getEncounterService().getEncounterType(name);
		if (encType == null) 
			throw new IncompleteMappingException("Missing '" + name + "' encounter type");
		
		return encType;
	}

	/**
	 * Gets a concept by name
	 * @param name the concept name
	 * @return the concept
	 * @throws IncompleteMappingException if concept doesn't exist
	 */
	public static Concept getConcept(String name) {
		// If name is null, return null
		if (name == null)
			return null;
		
		// If name is prefixed with @ then it's a global property
		else if (name.startsWith("@"))
			return getConceptFromProperty(name.substring(1));
		
		Concept concept = Context.getConceptService().getConcept(name);
		if (concept == null) 
			throw new IncompleteMappingException("Missing '" + name + "' concept");
		
		return concept;
	}

	/**
	 * Gets a concept from a global property
	 * @param property the property name
	 * @return the concept
	 * @throws IncompleteMappingException if global property doesn't exist
	 */
	private static Concept getConceptFromProperty(String property) {
		String conceptId = Context.getAdministrationService().getGlobalProperty(property);
		if (conceptId == null || conceptId.length() == 0) 
			throw new IncompleteMappingException("Missing '" + property + "' global property");
		
		return Context.getConceptService().getConcept(conceptId);
	}
}
