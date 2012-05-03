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

package org.openmrs.module.iqchartimport.iq;

import org.openmrs.module.iqchartimport.PIHDictionary;

/**
 * ARV drugs used by IQChart
 */
public enum IQDrug {

	_3TC (PIHDictionary.Drugs.LAMIVUDINE_ORAL_10, PIHDictionary.Drugs.LAMIVUDINE_150),
	ABC (PIHDictionary.Drugs.ABACAVIR_SYRUP_20, PIHDictionary.Drugs.ABACAVIR_300),
	AZT (PIHDictionary.Drugs.ZIDOVUDINE_SYRUP_10, null),
	D4T (null, null),
	D4T30 (PIHDictionary.Drugs.STAVUDINE_30, PIHDictionary.Drugs.STAVUDINE_30),
	D4T40 (PIHDictionary.Drugs.STAVUDINE_40, PIHDictionary.Drugs.STAVUDINE_40),
	D4T12 (null, null),
	D4T20 (PIHDictionary.Drugs.STAVUDINE_20, PIHDictionary.Drugs.STAVUDINE_20),
	D4T6 (null, null),
	DDI (null, null), // DIDANOSINE
	EFV (null, null),
	EFV600 (PIHDictionary.Drugs.EFAVIRENZ_600, PIHDictionary.Drugs.EFAVIRENZ_600),
	EFV800 (null, null),
	KALETRA (null, null), //LOPINAVIR AND RITONAVIR
	LPVr (null, null), //LOPINAVIR AND RITONAVIR
	NVP (PIHDictionary.Drugs.NEVIRAPINE_ORAL_10, PIHDictionary.Drugs.NEVIRAPINE_200),
	TDF (PIHDictionary.Drugs.TENOFOVIR_300, PIHDictionary.Drugs.TENOFOVIR_300);
	
	private Integer mappedPedsDrugId;
	private Integer mappedAdultDrugId;
	
	/**
	 * Constructs new drug mapping
	 * @param mappedPedsDrugId the mapped drug id for pediatric patients 
	 * @param mappedAdultDrugId the mapped drug id for adult patients
	 */
	IQDrug(Integer mappedPedsDrugId, Integer mappedAdultDrugId) {
		this.mappedPedsDrugId = mappedPedsDrugId;
		this.mappedAdultDrugId = mappedAdultDrugId;
	}

	/**
	 * Gets the mapped drug id for pediatric patients
	 * @return the drug id
	 */
	public Integer getMappedPedsDrugId() {
		return mappedPedsDrugId;
	}

	/**
	 * Gets the mapped drug id for adult patients
	 * @return the drug id
	 */
	public Integer getMappedAdultDrugId() {
		return mappedAdultDrugId;
	}
}
