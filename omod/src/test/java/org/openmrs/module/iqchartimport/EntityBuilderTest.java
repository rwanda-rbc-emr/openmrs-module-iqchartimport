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

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifierType;
import org.openmrs.module.iqchartimport.iq.IQChartDatabase;
import org.openmrs.module.iqchartimport.iq.IQChartSession;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Test class for EntityBuilder
 */
public class EntityBuilderTest extends BaseModuleContextSensitiveTest {

	protected static final Log log = LogFactory.getLog(EntityBuilderTest.class);

	private File tempZipFile, tempMdbFile;
	private IQChartSession session;
	private EntityBuilder builder;
	
	@Before
	public void setup() throws Exception {
		
		executeDataSet("TestingDataset.xml");
		
		// Extract embedded test database
		tempZipFile = TestingUtils.copyResource("/HIVData.mdb.zip");
		tempMdbFile = TestingUtils.extractZipEntry(tempZipFile, "HIVData.mdb");
		
	
		IQChartDatabase database = new IQChartDatabase("HIVData.mdb", tempMdbFile.getAbsolutePath());
		session = new IQChartSession(database);
		builder = new EntityBuilder(session);
	}
	
	@After
	public void cleanup() throws IOException {
		// Delete temporary files
		tempMdbFile.delete();
		tempZipFile.delete();
		
		session.close();
	}
	
	@Test
	public void getTRACnetIDType_shouldCreateNewIfNotMapped() {
		PatientIdentifierType tracnetIdType = builder.getTRACnetIDType();
		Assert.assertNull(tracnetIdType.getId());
		Assert.assertEquals(Constants.NEW_TRACNET_ID_TYPE_NAME, tracnetIdType.getName());
	}
	
	@Test
	public void getTRACnetIDType_shouldReturnExistingIfMapped() {
		Mappings.getInstance().setTracnetIDTypeId(1);
		
		PatientIdentifierType tracnetIdType = builder.getTRACnetIDType();
		Assert.assertEquals(new Integer(1), tracnetIdType.getId());
		Assert.assertEquals("OpenMRS Identification Number", tracnetIdType.getName());
	}
	
	@Test
	public void getPatient() {
		PatientIdentifierType tracnetIDType = builder.getTRACnetIDType();
		
		Patient patient = builder.getPatient(235001);
		Assert.assertNull(patient.getPatientId());
		Assert.assertEquals(1, patient.getIdentifiers().size());
		Assert.assertEquals("235001", patient.getPatientIdentifier().getIdentifier());
		Assert.assertEquals(tracnetIDType, patient.getPatientIdentifier().getIdentifierType());
		Assert.assertEquals(1, patient.getNames().size());
		Assert.assertEquals("Jane", patient.getPersonName().getGivenName());
		Assert.assertEquals("Doe", patient.getPersonName().getFamilyName());
		
		Assert.assertNull(patient.getPersonAddress().getAddress1()); // Umudugudu
		Assert.assertNull(patient.getPersonAddress().getAddress2()); // Not used
		Assert.assertEquals("Unknown", patient.getPersonAddress().getNeighborhoodCell()); // Cell
		Assert.assertEquals("Unknown", patient.getPersonAddress().getCityVillage()); // Sector
		Assert.assertEquals("Unknown", patient.getPersonAddress().getCountyDistrict()); // District
		Assert.assertNull(patient.getPersonAddress().getStateProvince()); // Province
		Assert.assertNull(patient.getPersonAddress().getCountry()); // Country	
	}
}
