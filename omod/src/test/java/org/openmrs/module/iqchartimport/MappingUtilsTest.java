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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Test cases for MappingsUtils class
 */
public class MappingUtilsTest extends BaseModuleContextSensitiveTest {

	protected static final Log log = LogFactory.getLog(MappingUtilsTest.class);
	
	@Before
	public void setup() throws Exception {
		executeDataSet("TestingDataset.xml");
	}
	
	@Test
	public void getEncounterType_shouldReturnIfExists() {
		// Test by name
		assertEquals(new Integer(1), MappingUtils.getEncounterType("ADULTINITIAL").getEncounterTypeId());
		assertEquals(new Integer(2), MappingUtils.getEncounterType("ADULTRETURN").getEncounterTypeId());
	}
	
	@Test
	public void getEncounterType_shouldThrowExceptionIfNotExists() {
		try {
			MappingUtils.getEncounterType("XXXXXXX");			
			fail("MappingUtils.getEncounterType should have thrown exception");
		}
		catch (IncompleteMappingException ex) {
		}
	}
	
	@Test
	public void getConcept_shouldReturnIfExists() {
		// Test by ID
		assertEquals(new Integer(5497), MappingUtils.getConcept(5497).getConceptId());
		// Test by name
		assertEquals(new Integer(5497), MappingUtils.getConcept("CD4 COUNT").getConceptId());
		// Test by global property lookup
		assertEquals(new Integer(5497), MappingUtils.getConcept("@concept.cd4_count").getConceptId());	
	}
	
	@Test
	public void getConcept_shouldThrowExceptionIfNotExists() {
		try {
			// Test by numeric id
			MappingUtils.getConcept(123456789);			
			fail("MappingUtils.getConcept should have thrown exception");
		}
		catch (IncompleteMappingException ex) {}
		try {
			// Test by name
			MappingUtils.getConcept("XXXXXXX");			
			fail("MappingUtils.getConcept should have thrown exception");
		}
		catch (IncompleteMappingException ex) {}
		try {
			// Test by global property lookup
			MappingUtils.getConcept("@XXXXXXX");			
			fail("MappingUtils.getConcept should have thrown exception");
		}
		catch (IncompleteMappingException ex) {}
	}
}
