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

/**
 * Common PIH dictionary concepts
 */
public class PIHDictionary {

	public static final int YES = 1065;
	public static final int NO = 1066;
	public static final int UNKNOWN = 1067;
	public static final int NOT_APPLICABLE = 1175;
	public static final int POSITIVE = 703;
	public static final int NEGATIVE = 664;
	public static final int TRANSFER_IN = 2536;
	public static final int OTHER_NON_CODED = 5622;
	
	/**
	 * ARV drug concepts
	 */
	public static final int ABACAVIR = 814;
	public static final int DIDANOSINE = 796;
	public static final int EFAVIRENZ = 633;
	public static final int INDINAVIR = 749;
	public static final int LAMIVUDINE = 628;
	public static final int LOPINAVIR_AND_RITONAVIR = 794; // KALETRA
	public static final int NELFINAVIR = 635;
	public static final int NEVIRAPINE = 631;
	public static final int RITONAVIR = 795;
	public static final int STAVUDINE = 625;
	public static final int TENOFOVIR = 802;
	public static final int ZIDOVUDINE = 797;
	
	/**
	 * TB drug concepts
	 */	
	public static final int TRIMETHOPRIM_AND_SULFAMETHOXAZOLE = 916; // COTRIMOXAZOLE or BACTRIM
	public static final int FLUCONAZOLE = 747;
	public static final int DAPSONE = 92;
	
	/**
	 * Concept drugs
	 */
	public static class Drugs {
		/**
		 * Adult ARVs
		 */
		public static final int ABACAVIR_300 = 426;
		public static final int DIDANOSINE_100 = 456;
		public static final int DIDANOSINE_200 = 457;
		public static final int DIDANOSINE_250 = 420;
		public static final int DIDANOSINE_400 = 421;
		public static final int EFAVIRENZ_600 = 424;
		public static final int INDINAVIR_400 = 446;
		public static final int LAMIVUDINE_150 = 425;
		public static final int NEVIRAPINE_200 = 427;
		public static final int RITONAVIR_100 =	444;
		public static final int TENOFOVIR_300 = 438;
		public static final int ZIDOVUDINE_100 = 463;	
		public static final int ZIDOVUDINE_300 = 419;
		public static final int ZIDOVUDINE_250 = 464;	
		public static final int NELFINAVIR_50 = 451;
		public static final int NELFINAVIR_250 = 458;	
		
		/**
		 * Pediatric ARVs
		 */
		public static final int ABACAVIR_SYRUP_20 = 448;
		public static final int DIDANOSINE_SYRUP_10 = 459;
		public static final int DIDANOSINE_25 = 460;
		public static final int DIDANOSINE_50 = 461;
		public static final int EFAVIRENZ_50 = 422;
		public static final int EFAVIRENZ_100 = 462;
		public static final int EFAVIRENZ_200 = 423;
		public static final int EFAVIRENZ_SYRUP_30 = 449;
		public static final int LAMIVUDINE_ORAL_10 = 450;
		public static final int NEVIRAPINE_ORAL_10 = 452;
		public static final int STAVUDINE_SYRUP_1 = 453;
		public static final int STAVUDINE_15 = 434;
		public static final int STAVUDINE_20 = 435;
		public static final int STAVUDINE_30 = 436;
		public static final int STAVUDINE_40 = 437;
		public static final int ZIDOVUDINE_SYRUP_10 = 447;
	}
}
