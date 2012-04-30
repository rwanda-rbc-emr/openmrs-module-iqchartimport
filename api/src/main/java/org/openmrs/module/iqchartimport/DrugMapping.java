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
import java.util.Set;
import java.util.TreeSet;

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
	 * Gets a list of OpenMRS drugs from an IQChart regimen
	 * @param regimen the regimen, e.g. "AZT / D4T / EFV 600"
	 * @return the drugs ids
	 * @throws IncompleteMappingException if a drug can't be found
	 */
	public static List<Integer> getRegimenDrugIds(String regimen) {
		List<RegimenComponent> components = getRegimenComponents(regimen);
		List<Integer> drugIds = new ArrayList<Integer>();
		
		for (RegimenComponent component : components) {
			Integer drugId = getDrugId(component);
			if (drugId == null) {
				String drugInfo = "Concept ID=" + conceptMap.get(component.getDrug()) + " dose=" + component.getDose();
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
	public static Integer getDrugId(RegimenComponent component) {
		Integer conceptId = conceptMap.get(component.getDrug());
		Double dosage = component.getDose();
		return Context.getService(IQChartImportService.class).getDrugIdByConceptAndDosage(conceptId, dosage);
	}
	
	/**
	 * Gets the components from a regimen
	 * @param regimen the regimen
	 * @return the components
	 */
	public static List<RegimenComponent> getRegimenComponents(String regimen) {
		List<RegimenComponent> components = new ArrayList<RegimenComponent>();
		
		for (String comp : regimen.split("/")) {
			RegimenComponent component = RegimenComponent.parse(comp.trim());
			components.add(component);
		}
		return components;
	}
	
	/**
	 * Gets all the components from a list of regimens in alphabetical order
	 * @param regimen the regimen
	 * @return the components
	 */
	public static Set<RegimenComponent> getRegimenComponents(List<String> regimens) {
		Set<RegimenComponent> components = new TreeSet<RegimenComponent>();
		
		for (String regimen : regimens) {
			for (RegimenComponent component : getRegimenComponents(regimen)) {
				components.add(component);
			}
		}
		return components;
	}
}
