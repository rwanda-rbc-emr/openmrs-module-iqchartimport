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

import java.util.HashMap;
import java.util.Map;

/**
 * Drug mapping functions
 */
public class DrugMapping {
	
	/**
	 * Regimen of drugs
	 */
	static class Regimen {

		public Integer[] drugIds;
		
		public Regimen(Integer ... drugIds) {
			this.drugIds = drugIds;
		}
	}
	
	/**
	 * Drug mapping
	 */
	private static Map<String, Integer> conceptMap = new HashMap<String, Integer>();	
	private static Map<String, Regimen> adultRegimens = new HashMap<String, Regimen>();
	private static Map<String, Regimen> pedsRegimens = new HashMap<String, Regimen>();
	
	static {
		/**
		 * Adult ARV regimens
		 */
		adultRegimens.put("AZT/3TC/EFV600", new Regimen(Drugs.LAMIVUDINE_ZIDOVUDINE_150_300, Drugs.EFAVIRENZ_600));
		adultRegimens.put("AZT/3TC/EFV800", new Regimen(Drugs.LAMIVUDINE_ZIDOVUDINE_150_300, Drugs.EFAVIRENZ_600, Drugs.EFAVIRENZ_200));
		adultRegimens.put("AZT/3TC/NVP", new Regimen(Drugs.LAMIVUDINE_ZIDOVUDINE_NEVIRAPINE_150_300_200));
		adultRegimens.put("D4T30/3TC/EFV600", new Regimen(Drugs.LAMIVUDINE_STAVUDINE_150_30, Drugs.EFAVIRENZ_600));
		adultRegimens.put("D4T30/3TC/EFV800", new Regimen(Drugs.LAMIVUDINE_STAVUDINE_150_30, Drugs.EFAVIRENZ_600, Drugs.EFAVIRENZ_200));
		adultRegimens.put("D4T30/3TC/NVP", new Regimen(Drugs.LAMIVUDINE_STAVUDINE_NEVIRAPINE_150_30_200));
		adultRegimens.put("D4T40/3TC/EFV600", new Regimen(Drugs.LAMIVUDINE_STAVUDINE_150_40, Drugs.EFAVIRENZ_600));
		adultRegimens.put("D4T40/3TC/EFV800", new Regimen(Drugs.LAMIVUDINE_STAVUDINE_150_40, Drugs.EFAVIRENZ_600, Drugs.EFAVIRENZ_200));
		adultRegimens.put("D4T40/3TC/NVP", new Regimen(Drugs.LAMIVUDINE_STAVUDINE_NEVIRAPINE_150_40_200));
		adultRegimens.put("D4T30/3TC/ABC", new Regimen(Drugs.LAMIVUDINE_STAVUDINE_150_30, Drugs.ABACAVIR_300));
		adultRegimens.put("D4T40/3TC/ABC", new Regimen(Drugs.LAMIVUDINE_STAVUDINE_150_40, Drugs.ABACAVIR_300)); 
		adultRegimens.put("TDF/3TC/NVP", new Regimen(Drugs.TENOFOVIR_LAMIVUDINE_300_300, Drugs.NEVIRAPINE_200));
		adultRegimens.put("ABC/3TC/NVP", new Regimen(Drugs.ABACAVIR_300, Drugs.LAMIVUDINE_150, Drugs.NEVIRAPINE_200));
		adultRegimens.put("AZT/3TC/KALETRA", new Regimen(Drugs.LAMIVUDINE_ZIDOVUDINE_150_300, Drugs.LOPINAVIR_RITONAVIR_200_50));
		adultRegimens.put("D4T/DDI/LPVr", new Regimen(Drugs.STAVUDINE_20, Drugs.STAVUDINE_20, Drugs.DIDANOSINE_400, Drugs.LOPINAVIR_RITONAVIR_200_50));
		adultRegimens.put("AZT/3TC/ABC", new Regimen(Drugs.LAMIVUDINE_ZIDOVUDINE_150_300, Drugs.ABACAVIR_300));
		adultRegimens.put("D4T/3TC/KALETRA", new Regimen(Drugs.LAMIVUDINE_STAVUDINE_150_30, Drugs.LOPINAVIR_RITONAVIR_200_50));
		adultRegimens.put("TDF/3TC/EFV", new Regimen(Drugs.TENOFOVIR_LAMIVUDINE_EFAVIRENZ_300_300_600));
		adultRegimens.put("TDF/3TC/KALETRA", new Regimen(Drugs.TENOFOVIR_LAMIVUDINE_300_300, Drugs.LOPINAVIR_RITONAVIR_200_50));
		//adultRegimens.put("DDI/KALETRA", new Regimen()); // Typo missing 3TC
		//adultRegimens.put("DDI/3TC/KALETRA", new Regimen());
		adultRegimens.put("AZT/3TC/EFV", new Regimen(Drugs.LAMIVUDINE_ZIDOVUDINE_150_300, Drugs.EFAVIRENZ_600));
		adultRegimens.put("ABC/3TC/KALETRA", new Regimen(Drugs.ABACAVIR_300, Drugs.LAMIVUDINE_150, Drugs.LOPINAVIR_RITONAVIR_200_50));
		adultRegimens.put("TDF/3TC/ABC", new Regimen(Drugs.TENOFOVIR_LAMIVUDINE_300_300, Drugs.ABACAVIR_300));
		adultRegimens.put("AZT/TDF/3TC/KALETRA", new Regimen(Drugs.ZIDOVUDINE_300, Drugs.TENOFOVIR_LAMIVUDINE_300_300, Drugs.LOPINAVIR_RITONAVIR_200_50));
		adultRegimens.put("ABC/3TC/EFV", new Regimen(Drugs.ABACAVIR_300, Drugs.LAMIVUDINE_150, Drugs.EFAVIRENZ_600));
		
		/**
		 * Pediatric ARV regimens
		 */
		pedsRegimens.put("AZT/3TC/NVP", new Regimen(Drugs.LAMIVUDINE_ZIDOVUDINE_NEVIRAPINE_30_60_50));
		pedsRegimens.put("D4T20/3TC/NVP", new Regimen(Drugs.STAVUDINE_20, Drugs.LAMIVUDINE_10_ORAL, Drugs.NEVIRAPINE_10_ORAL));
		pedsRegimens.put("D4T12/3TC/NVP", new Regimen(Drugs.LAMIVUDINE_STAVUDINE_NEVIRAPINE_60_12_100));
		pedsRegimens.put("D4T6/3TC/NVP", new Regimen(Drugs.LAMIVUDINE_STAVUDINE_NEVIRAPINE_30_6_50));
		
		/**
		 * ARV drugs
		 */
		conceptMap.put("ABC", Dictionary.ABACAVIR);
		conceptMap.put("DDI", Dictionary.DIDANOSINE);
		conceptMap.put("EFV", Dictionary.EFAVIRENZ);
		conceptMap.put("IDV", Dictionary.INDINAVIR);
		conceptMap.put("3TC", Dictionary.LAMIVUDINE);
		conceptMap.put("LPVr", Dictionary.LOPINAVIR_AND_RITONAVIR);
		conceptMap.put("LPV/r", Dictionary.LOPINAVIR_AND_RITONAVIR);
		conceptMap.put("KALETRA", Dictionary.LOPINAVIR_AND_RITONAVIR);
		conceptMap.put("NFV", Dictionary.NELFINAVIR);
		conceptMap.put("NVP", Dictionary.NEVIRAPINE);
		conceptMap.put("RTV", Dictionary.RITONAVIR);
		conceptMap.put("D4T", Dictionary.STAVUDINE);
		conceptMap.put("TDF", Dictionary.TENOFOVIR);
		conceptMap.put("AZT", Dictionary.ZIDOVUDINE);
				
		/**
		 * Drugs used for TB
		 */
		conceptMap.put("Bactrim", Dictionary.TRIMETHOPRIM_AND_SULFAMETHOXAZOLE);
		conceptMap.put("Fluconazol", Dictionary.FLUCONAZOLE); // IQChart uses mispelling
		conceptMap.put("Fluconazole", Dictionary.FLUCONAZOLE);
		conceptMap.put("Dapsone", Dictionary.DAPSONE);
	}
	
	/**
	 * Gets a list of OpenMRS drugs from an IQChart regimen
	 * @param regimen the regimen, e.g. "AZT / D4T / EFV 600"
	 * @param peds true if patient is a child
	 * @param strict true if only drugs appropriate for age should be returned
	 * @return the drugs ids
	 * @throws IncompleteMappingException if a drug can't be found
	 */
	public static Integer[] getRegimenDrugIds(String regimen, boolean peds, boolean strict) {
		String regimenClean = regimen.replace(" ", "");
		
		Map<String, Regimen> regimens1 = peds ? pedsRegimens : adultRegimens;
		Map<String, Regimen> regimens2 = peds ? adultRegimens : pedsRegimens;
		
		if (regimens1.containsKey(regimenClean))
			return regimens1.get(regimenClean).drugIds;
		else if (!strict && regimens2.containsKey(regimenClean))
			return regimens2.get(regimenClean).drugIds;
		else
			throw new IncompleteMappingException("Unrecognized regimen: '" + regimen + "'");			
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
	 * Gets a list of OpenMRS drug concepts from an IQChart regimen
	 * @param regimen the regimen, e.g. "AZT / D4T / EFV 600"
	 * @return the drugs ids
	 * @throws IncompleteMappingException if a drug can't be found
	 */
	public static Integer[] getRegimenConceptIds(String regimen) {
		String[] components = regimen.split("/");
		Integer[] conceptIds = new Integer[components.length];
		
		for (int c = 0; c < components.length; ++c) {
			String component = trimEndNumerals(components[c].trim());
			if (conceptMap.containsKey(component))
				conceptIds[c] = conceptMap.get(component);
			else
				throw new IncompleteMappingException("Unrecognized regimen component: '" + component + "'");
		}
		
		return conceptIds;
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
