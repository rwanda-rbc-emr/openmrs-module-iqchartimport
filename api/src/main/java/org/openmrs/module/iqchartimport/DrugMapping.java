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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.openmrs.Drug;
import org.openmrs.api.context.Context;


/**
 * Drug mapping functions
 */
public class DrugMapping {
	
	/**
	 * Represents a component of an IQChart ARV regimen, e.g. D4T30, EFV
	 */
	public static class ARVComponent {
		private String name;
		private String drugAbbrev;
		private Integer dose;
		
		/**
		 * Parses a component from the given string
		 * @param name the string
		 * @return the component
		 */
		static ARVComponent parse(String name) {
			ARVComponent component = new ARVComponent();
			component.name = name;
			
			int c = name.length();
			for (; c > 0; --c) {
				if (Character.isLetter(name.charAt(c - 1)))
					break;
			}
			component.drugAbbrev = name.substring(0, c);
			String strDose = name.substring(c).trim();
			component.dose = strDose.length() > 0 ? Integer.parseInt(strDose) : null;
			return component;
		}
		
		/**
		 * Gets the name
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * Gets the drug abbreviation part
		 * @return the drug abbreviation
		 */
		public String getDrugAbbreviation() {
			return drugAbbrev;
		}
		
		/**
		 * Gets the dose part
		 * @return the dose
		 */
		public Integer getDose() {
			return dose;
		}
	}
	
	/**
	 * Gets a list of OpenMRS drugs from an IQChart regimen
	 * @param regimen the regimen, e.g. "AZT / D4T / EFV 600"
	 * @return the drugs
	 */
	public static List<Drug> getARVDrugs(String regimen) {
		List<ARVComponent> components = getRegimenComponents(regimen);
		List<Drug> drugs = new ArrayList<Drug>();
		
		for (ARVComponent component : components)
			drugs.add(getDrug(component));
		
		return drugs;
	}
	
	/**
	 * Gets an OpenMRS drug from an IQChart regimen component
	 * @param component the regimen component
	 * @return the drug
	 */
	public static Drug getDrug(ARVComponent component) {
		// TODO map....
		return Context.getConceptService().getDrug(1);
	}
	
	/**
	 * Gets the components from a regimen
	 * @param regimen the regimen
	 * @return the components
	 */
	public static List<ARVComponent> getRegimenComponents(String regimen) {
		return getRegimenComponents(Collections.singleton(regimen));
	}
	
	/**
	 * Gets the unique components from a list of regimens
	 * @param regimens the regimens
	 * @return the unique components
	 */
	public static List<ARVComponent> getRegimenComponents(Collection<String> regimens) {
		Set<String> names = new TreeSet<String>();
		List<ARVComponent> components = new ArrayList<ARVComponent>();
		
		for (String regimen : regimens) {
			for (String component : regimen.split("/"))
				names.add(component.trim());
		}
		
		for (String name : names)
			components.add(ARVComponent.parse(name));
		
		return components;
	}
}
