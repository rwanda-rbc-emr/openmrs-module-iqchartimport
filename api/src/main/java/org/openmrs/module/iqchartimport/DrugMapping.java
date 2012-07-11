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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.util.OpenmrsUtil;

/**
 * Drug mapping functions
 */
public class DrugMapping {
	
	protected static final Log log = LogFactory.getLog(DrugMapping.class);
	
	private static Map<String, Integer> components = new HashMap<String, Integer>();
	private static Map<String, List<Integer>> mappings = new HashMap<String, List<Integer>>();

	static {
		/**
		 * ARV drugs	
		 */
		components.put("ABC", Dictionary.ABACAVIR);
		components.put("DDI", Dictionary.DIDANOSINE);
		components.put("EFV", Dictionary.EFAVIRENZ);
		components.put("IDV", Dictionary.INDINAVIR);
		components.put("3TC", Dictionary.LAMIVUDINE);
		components.put("LPVr", Dictionary.LOPINAVIR_AND_RITONAVIR);
		components.put("LPV/r", Dictionary.LOPINAVIR_AND_RITONAVIR);
		components.put("KALETRA", Dictionary.LOPINAVIR_AND_RITONAVIR);
		components.put("NFV", Dictionary.NELFINAVIR);
		components.put("NVP", Dictionary.NEVIRAPINE);
		components.put("RTV", Dictionary.RITONAVIR);
		components.put("D4T", Dictionary.STAVUDINE);
		components.put("TDF", Dictionary.TENOFOVIR);
		components.put("AZT", Dictionary.ZIDOVUDINE);
		
		/**
		 * Drugs used for TB	
		 */
		components.put("Bactrim", Dictionary.TRIMETHOPRIM_AND_SULFAMETHOXAZOLE);
		components.put("Fluconazol", Dictionary.FLUCONAZOLE); // IQChart uses mispelling
		components.put("Fluconazole", Dictionary.FLUCONAZOLE);
		components.put("Dapsone", Dictionary.DAPSONE);
	}
	
	/**
	 *
	 */
	public static void guess(List<String> names) {		
		// Try to make a mapping for each given drug/regimen
		// by breaking it down into components
		for (String name : names) {
			
			// Components can be separated by '/' '\' or '+'
			String[] comps = name.split("[\\\\/+]");
			
			List<Integer> conceptIds = new ArrayList<Integer>();
			
			log.info("Parsing regimen: " + name);
			
			// Lookup each component in the mappings
			for (String component : comps) {
				log.info(" > Parsing regimen component: " + component);
				
				component = trimEndNumerals(component.trim());
				if (components.containsKey(component))
					conceptIds.add(components.get(component));
				else
					continue;
			}
			
			mappings.put(name, conceptIds);
		}		
	}
	
	/**
	 * Clears all drug mappings
	 */
	public static void clear() {
		mappings.clear();
	}
	
	/**
	 * Loads drug mappings
	 */
	public static void load() {
		clear();
		Mappings.getInstance().load();
		
		String mappingsStr = Mappings.getInstance().getDrugMappings();
		for (String line : mappingsStr.split("\\\n")) {
			line = line.trim();
			if (line.length() == 0)
				continue;
			
			String[] tokens = line.split("\\|");
			if (tokens.length != 2)
				continue;
			
			String name = tokens[0].trim();
			String idsStr = tokens[1].trim();
			List<Integer> conceptIds = OpenmrsUtil.delimitedStringToIntegerList(idsStr, ",");
			
			mappings.put(name, conceptIds);
		}
	}
	
	/**
	 * Saves drug mappings
	 */
	public static void save() {
		StringBuilder mappingsStr = new StringBuilder();
		
		for (String iqDrug : mappings.keySet()) {
			String idsStr = OpenmrsUtil.join(mappings.get(iqDrug), ",");
			mappingsStr.append(iqDrug + "|" + idsStr + "\n");
		}
		
		Mappings.getInstance().setDrugMappings(mappingsStr.toString());
		Mappings.getInstance().save();
	}
	
	/**
	 * Gets a list of OpenMRS concepts from an IQChart regimen/drug name
	 * @param regimen the regimen, e.g. "AZT / D4T / EFV 600"
	 * @return the concept ids
	 * @throws IncompleteMappingException if a drug/regimen can't be found
	 */
	public static List<Integer> getConcepts(String name) {
		name = name.trim();
		
		// Try complete regimen/drug name
		if (mappings.containsKey(name))
			return mappings.get(name);
		
		// Try breaking down into components
		if (name.contains("\\") || name.contains("+")) {
			String[] components = name.split("[\\\\+]");
			
			List<Integer> conceptIds = new ArrayList<Integer>();
			
			// Lookup each component in the mappings
			for (String component : components) {
				component = component.trim();
				if (component.length() == 0)
					continue;
				
				component = trimEndNumerals(component);		
				conceptIds.addAll(getConcepts(component));
			}
		}

		throw new IncompleteMappingException("Unrecognized drug/regimen: '" + name + "'");			
	}
	
	/**
	 * Maps an IQChart regimen/drug name to an OpenMRS drug concept
	 * @param name the regimen name, e.g. "AZT / D4T / EFV 600"
	 * @param regimen the concept ids
	 */
	public static void setConcept(String name, Integer conceptId) {
		name = name.trim();
		mappings.put(name, Collections.singletonList(conceptId));			
	}
	
	/**
	 * Maps an IQChart regimen/drug name to a regimen of OpenMRS concepts
	 * @param name the regimen name, e.g. "AZT / D4T / EFV 600"
	 * @param regimen the concept ids
	 */
	public static void setConceptList(String name, List<Integer> conceptIds) {
		name = name.trim();
		mappings.put(name, conceptIds);			
	}
	
	/**
	 * Trims numeral characters from the end of a string
	 * @param input the input sting	
	 * @return the input without trailing numerals	
	 */
	protected static String trimEndNumerals(String input) {
		if (input == null || input.length() == 0)
			return input;

		int last = input.length() - 1;	
		while (Character.isDigit(input.charAt(last)) || Character.isWhitespace(input.charAt(last)))	
			--last;
		
		return input.substring(0, last + 1);	
	}
}
