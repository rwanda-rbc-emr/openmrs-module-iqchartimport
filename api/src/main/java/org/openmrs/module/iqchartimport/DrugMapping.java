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
import java.util.List;
import java.util.Map;

import org.openmrs.util.OpenmrsUtil;

/**
 * Drug mapping functions
 */
public class DrugMapping {
	
	private static Map<String, List<Integer>> mappings = new HashMap<String, List<Integer>>();

	static {
		/**
		 * ARV regimens
		 */
		/*setRegimenDrugIds("AZT / 3TC / EFV 600", new Integer[] {Drugs.LAMIVUDINE_ZIDOVUDINE_150_300, Drugs.EFAVIRENZ_600});
		setRegimenDrugIds("AZT / 3TC / EFV 800", new Integer[] {Drugs.LAMIVUDINE_ZIDOVUDINE_150_300, Drugs.EFAVIRENZ_600, Drugs.EFAVIRENZ_200});
		regimenMappings.put("AZT/3TC/NVP", new Regimen(Drugs.LAMIVUDINE_ZIDOVUDINE_NEVIRAPINE_150_300_200));
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
		adultRegimens.put("AZT/3TC/EFV", new Regimen(Drugs.LAMIVUDINE_ZIDOVUDINE_150_300, Drugs.EFAVIRENZ_600));
		adultRegimens.put("ABC/3TC/KALETRA", new Regimen(Drugs.ABACAVIR_300, Drugs.LAMIVUDINE_150, Drugs.LOPINAVIR_RITONAVIR_200_50));
		adultRegimens.put("TDF/3TC/ABC", new Regimen(Drugs.TENOFOVIR_LAMIVUDINE_300_300, Drugs.ABACAVIR_300));
		adultRegimens.put("AZT/TDF/3TC/KALETRA", new Regimen(Drugs.ZIDOVUDINE_300, Drugs.TENOFOVIR_LAMIVUDINE_300_300, Drugs.LOPINAVIR_RITONAVIR_200_50));
		adultRegimens.put("ABC/3TC/EFV", new Regimen(Drugs.ABACAVIR_300, Drugs.LAMIVUDINE_150, Drugs.EFAVIRENZ_600));
		adultRegimens.put("D4T20/3TC/NVP", new Regimen(Drugs.STAVUDINE_20, Drugs.LAMIVUDINE_10_ORAL, Drugs.NEVIRAPINE_10_ORAL));
		adultRegimens.put("D4T12/3TC/NVP", new Regimen(Drugs.LAMIVUDINE_STAVUDINE_NEVIRAPINE_60_12_100));
		adultRegimens.put("D4T6/3TC/NVP", new Regimen(Drugs.LAMIVUDINE_STAVUDINE_NEVIRAPINE_30_6_50));
		*/
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
			String regimen = tokens[0].trim();
			String idsStr = tokens[1].trim();
			List<Integer> drugIds = OpenmrsUtil.delimitedStringToIntegerList(idsStr, ",");
			
			mappings.put(regimen, drugIds);
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
	 * Gets a regimen of OpenMRS drugs from an IQChart regimen/drug name
	 * @param regimen the regimen, e.g. "AZT / D4T / EFV 600"
	 * @return the drugs ids
	 * @throws IncompleteMappingException if a drug can't be found
	 */
	public static List<Integer> getDrugIds(String name) {
		if (mappings.containsKey(name))
			return mappings.get(name);
		else
			throw new IncompleteMappingException("Unrecognized regimen: '" + name + "'");			
	}
	
	/**
	 * Maps a regimen of OpenMRS drugs to an IQChart regimen/drug name
	 * @param name the regimen name, e.g. "AZT / D4T / EFV 600"
	 * @param regimen the drugs ids
	 */
	public static void setDrugIds(String name, List<Integer> drugIds) {
		mappings.put(name, drugIds);			
	}
}
