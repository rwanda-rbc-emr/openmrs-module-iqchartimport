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

import static org.junit.Assert.*;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.openmrs.module.iqchartimport.DrugMapping.ARVComponent;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Test cases for DrugUtils class
 */
public class DrugMappingTest extends BaseModuleContextSensitiveTest {

	protected static final Log log = LogFactory.getLog(DrugMappingTest.class);
	
	@Test
	public void Component_parse() {
		ARVComponent comp1 = ARVComponent.parse("D4T30");
		assertEquals("D4T30", comp1.getName());
		assertEquals("D4T", comp1.getDrugAbbreviation());
		assertEquals(new Double(30.0), comp1.getDose());
		
		ARVComponent comp2 = ARVComponent.parse("D4T 40");
		assertEquals("D4T 40", comp2.getName());
		assertEquals("D4T", comp2.getDrugAbbreviation());
		assertEquals(new Double(40.0), comp2.getDose());
		
		ARVComponent comp3 = ARVComponent.parse("D4T");
		assertEquals("D4T", comp3.getName());
		assertEquals("D4T", comp3.getDrugAbbreviation());
		assertNull(comp3.getDose());
	}
	
	@Test
	public void getRegimenComponents() {	
		List<ARVComponent> components1 = DrugMapping.getRegimenComponents("AZT / 3TC / EFV 600");
		assertEquals(3, components1.size());
		assertEquals("AZT", components1.get(0).getName());
		assertEquals("3TC", components1.get(1).getName());
		assertEquals("EFV 600", components1.get(2).getName());
		
		List<ARVComponent> components2 = DrugMapping.getRegimenComponents("ABC/3TC/EFV");
		assertEquals(3, components2.size());
		assertEquals("ABC", components2.get(0).getName());
		assertEquals("3TC", components2.get(1).getName());
		assertEquals("EFV", components2.get(2).getName());
	}
}
