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
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Test cases for DrugUtils class
 */
public class DrugMappingTest extends BaseModuleContextSensitiveTest {

	protected static final Log log = LogFactory.getLog(DrugMappingTest.class);
	
	@Test
	public void getRegimenComponents() {	
		List<String> components1 = DrugMapping.getRegimenComponents("AZT / 3TC / EFV 600");
		assertEquals(3, components1.size());
		assertEquals("AZT", components1.get(0));
		assertEquals("3TC", components1.get(1));
		assertEquals("EFV600", components1.get(2));
		
		List<String> components2 = DrugMapping.getRegimenComponents("ABC/3TC/EFV");
		assertEquals(3, components2.size());
		assertEquals("ABC", components2.get(0));
		assertEquals("3TC", components2.get(1));
		assertEquals("EFV", components2.get(2));
	}
}
