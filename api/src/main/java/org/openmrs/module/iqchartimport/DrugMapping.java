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

/**
 * Drug mapping functions
 */
public class DrugMapping {
	
	/**
	 * Drug mapping
	 */
	private static Map<String, Integer> conceptMap = new HashMap<String, Integer>();
	private static Map<String, Integer[]> drugMap = new HashMap<String, Integer[]>();
	
	static {
		drugMap.put("3TC", new Integer[] { PIHDictionary.Drugs.LAMIVUDINE_ORAL_10, PIHDictionary.Drugs.LAMIVUDINE_150 });
		drugMap.put("ABC", new Integer[] { PIHDictionary.Drugs.ABACAVIR_SYRUP_20, PIHDictionary.Drugs.ABACAVIR_300 });
		drugMap.put("AZT", new Integer[] { PIHDictionary.Drugs.ZIDOVUDINE_SYRUP_10 });
		drugMap.put("D4T", null);
		drugMap.put("D4T30", new Integer[] { PIHDictionary.Drugs.STAVUDINE_30 });
		drugMap.put("D4T40", new Integer[] { PIHDictionary.Drugs.STAVUDINE_40 });
		drugMap.put("D4T12", null);
		drugMap.put("D4T20", new Integer[] { PIHDictionary.Drugs.STAVUDINE_20 });
		drugMap.put("D4T6", null);
		drugMap.put("DDI", null);
		drugMap.put("EFV", null);
		drugMap.put("EFV600", new Integer[] { PIHDictionary.Drugs.EFAVIRENZ_600 });
		drugMap.put("EFV800", null);
		drugMap.put("KALETRA", null); // LOPINAVIR AND RITONAVIR
		drugMap.put("LPVr", null); // LOPINAVIR AND RITONAVIR
		drugMap.put("NVP", new Integer[] { PIHDictionary.Drugs.NEVIRAPINE_ORAL_10, PIHDictionary.Drugs.NEVIRAPINE_200 });
		drugMap.put("TDF", new Integer[] { PIHDictionary.Drugs.TENOFOVIR_300 });	
		
		/**
		 * Drugs used for TB
		 */
		conceptMap.put("Bactrim", PIHDictionary.TRIMETHOPRIM_AND_SULFAMETHOXAZOLE);
		conceptMap.put("Fluconazol", PIHDictionary.FLUCONAZOLE); // IQChart uses mispelling
		conceptMap.put("Fluconazole", PIHDictionary.FLUCONAZOLE);
		conceptMap.put("Dapsone", PIHDictionary.DAPSONE);
		conceptMap.put("AZT", PIHDictionary.ZIDOVUDINE);
	}
	
	/**
	 * Gets drug id for an IQChart regimen component
	 * @param component the regimen component
	 * @param peds true if patient is a child
	 * @return the drug id
	 * @throws IncompleteMappingException if a drug can't be found
	 */
	public static Integer getDrugId(String component, boolean peds) {
		Integer[] componentDrugIds = drugMap.get(component);
		
		if (componentDrugIds == null)
			throw new IncompleteMappingException("Missing drug mapping for " + component);
		else if (componentDrugIds.length == 2)
			return componentDrugIds[peds ? 0 : 1];
		else
			return componentDrugIds[0];
	}
	
	/**
	 * Gets a list of OpenMRS drugs from an IQChart regimen
	 * @param regimen the regimen, e.g. "AZT / D4T / EFV 600"
	 * @param peds true if patient is a child
	 * @return the drugs ids
	 * @throws IncompleteMappingException if a drug can't be found
	 */
	public static List<Integer> getRegimenDrugIds(String regimen, boolean peds) {
		List<Integer> drugIds = new ArrayList<Integer>();
		
		for (String component : getRegimenComponents(regimen)) {
			drugIds.add(getDrugId(component, peds));
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
	 * Gets components from a regimen
	 * @param regimen the regimen
	 * @return the components
	 */
	public static List<String> getRegimenComponents(String regimen) {
		List<String> components = new ArrayList<String>();
		
		for (String component : regimen.split("/"))
			components.add(component.trim().replace("/", "").replace(" ", ""));
		
		return components;
	}
	
	/**
	 * Gets all the components from a list of regimens in alphabetical order
	 * @param regimen the regimen
	 * @return the components
	 */
	public static Set<String> getRegimenComponents(List<String> regimens) {
		Set<String> components = new TreeSet<String>();
		
		for (String regimen : regimens) {
			for (String component : getRegimenComponents(regimen)) {
				components.add(component);
			}
		}
		return components;
	}
}
