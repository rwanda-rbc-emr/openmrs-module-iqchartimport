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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openmrs.api.context.Context;


/**
 * Drug mapping functions
 */
public class DrugMapping {
	
	/**
	 * TB drug mapping
	 */
	private static Map<String, Integer> conceptMap = new HashMap<String, Integer>();
	static {
		/**
		 * ARV drugs
		 */
		conceptMap.put("ABC", PIHDictionary.ABACAVIR);
		conceptMap.put("DDI", PIHDictionary.DIDANOSINE);
		conceptMap.put("EFV", PIHDictionary.EFAVIRENZ);
		conceptMap.put("IDV", PIHDictionary.INDINAVIR);
		conceptMap.put("3TC", PIHDictionary.LAMIVUDINE);
		conceptMap.put("LPVr", PIHDictionary.LOPINAVIR_AND_RITONAVIR);
		conceptMap.put("LPV/r", PIHDictionary.LOPINAVIR_AND_RITONAVIR);
		conceptMap.put("KALETRA", PIHDictionary.LOPINAVIR_AND_RITONAVIR);
		conceptMap.put("NFV", PIHDictionary.NELFINAVIR);
		conceptMap.put("NVP", PIHDictionary.NEVIRAPINE);
		conceptMap.put("RTV", PIHDictionary.RITONAVIR);
		conceptMap.put("D4T", PIHDictionary.STAVUDINE);
		conceptMap.put("TDF", PIHDictionary.TENOFOVIR);
		conceptMap.put("AZT", PIHDictionary.ZIDOVUDINE);
		
		/**
		 * Other drugs
		 */
		conceptMap.put("Bactrim", PIHDictionary.TRIMETHOPRIM_AND_SULFAMETHOXAZOLE);
		conceptMap.put("Fluconazol", PIHDictionary.FLUCONAZOLE); // IQChart uses mispelling
		conceptMap.put("Fluconazole", PIHDictionary.FLUCONAZOLE);
		conceptMap.put("Dapsone", PIHDictionary.DAPSONE);
	}
	
	/**
	 * Represents a component of an IQChart ARV regimen, e.g. D4T30, EFV
	 */
	protected static class ARVComponent {
		private String name;
		private String drugAbbrev;
		private Double dose;
		
		/**
		 * Parses a component from the given string
		 * @param name the string
		 * @return the component
		 */
		protected static ARVComponent parse(String name) {
			ARVComponent component = new ARVComponent();
			component.name = name;
			
			int c = name.length();
			for (; c > 0; --c) {
				if (Character.isLetter(name.charAt(c - 1)))
					break;
			}
			component.drugAbbrev = name.substring(0, c);
			String strDose = name.substring(c).trim();
			component.dose = strDose.length() > 0 ? Double.parseDouble(strDose) : null;
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
		public Double getDose() {
			return dose;
		}
	}
	
	/**
	 * Gets a list of OpenMRS drugs from an IQChart regimen
	 * @param regimen the regimen, e.g. "AZT / D4T / EFV 600"
	 * @return the drugs ids
	 * @throws IncompleteMappingException if a drug can't be found
	 */
	public static List<Integer> getRegimenDrugIds(String regimen) {
		List<ARVComponent> components = getRegimenComponents(regimen);
		List<Integer> drugIds = new ArrayList<Integer>();
		
		for (ARVComponent component : components) {
			Integer drugId = getDrugId(component);
			if (drugId == null) {
				String drugInfo = "Concept ID=" + conceptMap.get(component.getDrugAbbreviation()) + " dose=" + component.getDose();
				throw new IncompleteMappingException("Missing drug: " + component.getName() + " (" + drugInfo + ")");
			}
			drugIds.add(drugId);
		}
		
		return drugIds;
	}
	
	/**
	 * Gets an OpenMRS drug concept ID from an IQChart drug name
	 * @param component the drug name
	 * @return the drug concept id or null
	 */
	public static Integer getDrugConceptId(String drug) {
		return conceptMap.get(drug);
	}
	
	/**
	 * Gets an OpenMRS drug from an IQChart regimen component
	 * @param component the regimen component
	 * @return the drug id or null
	 */
	protected static Integer getDrugId(ARVComponent component) {
		Integer conceptId = conceptMap.get(component.getDrugAbbreviation());
		Double dosage = component.getDose();
		return Context.getService(IQChartImportService.class).getDrugIdByConceptAndDosage(conceptId, dosage);
	}
	
	/**
	 * Gets the components from a regimen
	 * @param regimen the regimen
	 * @return the components
	 */
	protected static List<ARVComponent> getRegimenComponents(String regimen) {
		List<ARVComponent> components = new ArrayList<ARVComponent>();
		
		for (String comp : regimen.split("/")) {
			ARVComponent component = ARVComponent.parse(comp.trim());
			components.add(component);
		}
		return components;
	}
}
