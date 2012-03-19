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

import static junit.framework.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.EncounterType;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PatientProgram;
import org.openmrs.module.iqchartimport.iq.IQChartDatabase;
import org.openmrs.module.iqchartimport.iq.IQChartSession;
import org.openmrs.module.iqchartimport.iq.IQPatient;
import org.openmrs.module.iqchartimport.iq.code.ExitCode;
import org.openmrs.module.iqchartimport.iq.code.SexCode;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Test class for EntityBuilder
 */
public class EntityBuilderTest extends BaseModuleContextSensitiveTest {

	protected static final Log log = LogFactory.getLog(EntityBuilderTest.class);

	private File tempZipFile, tempMdbFile;
	private IQChartSession session;
	private EntityBuilder builder;
	
	// IDs of patients from test DB to check
	private int[] testIDs = { 235001, 185568 };
	
	@Before
	public void setup() throws Exception {
		
		executeDataSet("TestingDataset.xml");
		
		// Extract embedded test database
		tempZipFile = TestUtils.copyResource("/HIVData.mdb.zip");
		tempMdbFile = TestUtils.extractZipEntry(tempZipFile, "HIVData.mdb");
		
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
		TestUtils.setGlobalProperty(Constants.PROP_TRACNET_ID_TYPE_ID, 1);
		Mappings.getInstance().load();
		
		PatientIdentifierType tracnetIdType = builder.getTRACnetIDType();
		assertEquals(new Integer(1), tracnetIdType.getId());
		assertEquals("Test ID Type", tracnetIdType.getName());
	}
	
	@Test
	public void getPatient() {	
		for (int tracnetID : testIDs ) {
			IQPatient iqPatient = session.getPatient(tracnetID);
			Patient patient = builder.getPatient(tracnetID);
			
			assertNull(patient.getPatientId());
			
			// Check ID
			assertEquals(1, patient.getIdentifiers().size());
			assertEquals(iqPatient.getTracnetID() + "", patient.getPatientIdentifier().getIdentifier());
			PatientIdentifierType tracnetIDType = builder.getTRACnetIDType();
			assertEquals(tracnetIDType, patient.getPatientIdentifier().getIdentifierType());
			
			// Check name
			assertEquals(1, patient.getNames().size());
			assertEquals(iqPatient.getFirstName(), patient.getPersonName().getGivenName());
			assertNull(patient.getPersonName().getMiddleName());
			assertEquals(iqPatient.getLastName(), patient.getPersonName().getFamilyName());
			
			// Check address
			assertNull(patient.getPersonAddress().getAddress1()); // Umudugudu
			assertNull(patient.getPersonAddress().getAddress2()); // Not used
			assertEquals(iqPatient.getCellule(), patient.getPersonAddress().getNeighborhoodCell());
			assertEquals(iqPatient.getSector(), patient.getPersonAddress().getCityVillage()); 
			assertEquals(iqPatient.getDistrict(), patient.getPersonAddress().getCountyDistrict());
			assertEquals("Testern", patient.getPersonAddress().getStateProvince()); // Province
			assertEquals("Testland", patient.getPersonAddress().getCountry()); // Country	
			
			// Check gender
			assertEquals(iqPatient.getSexCode() == SexCode.MALE ? "M" : "F", patient.getGender());
			
			// Check birth date
			assertEquals(iqPatient.getDob(), patient.getBirthdate());
			assertEquals(new Boolean(iqPatient.isDobEstimated()), patient.isBirthdateEstimated());
			
			// Check living/dead
			Boolean isDead = iqPatient.getExitCode() != null && iqPatient.getExitCode() == ExitCode.DECEASED;
			assertEquals(isDead, patient.isDead());
		}
	}
	
	@Test
	public void getPatientPrograms() {	
		for (int tracnetID : testIDs ) {
			IQPatient iqPatient = session.getPatient(tracnetID);
			List<PatientProgram> programs = builder.getPatientPrograms(tracnetID);
			
			// Check for single program
			assertEquals(1, programs.size());
			assertEquals(new Integer(1), programs.get(0).getProgram().getProgramId());
			assertEquals("Test program", programs.get(0).getProgram().getName());
			assertEquals(iqPatient.getEnrollDate(), programs.get(0).getDateEnrolled());
			assertEquals(iqPatient.getExitDate(), programs.get(0).getDateCompleted());
		}
	}
	
	@Test
	public void getEncounterType() {	
		Patient patient = new Patient();
		patient.setBirthdate(TestUtils.date(1980, 6, 1));
		patient.setBirthdateEstimated(false);
		
		EncounterType type = builder.getEncounterType(patient, TestUtils.date(1990, 1, 1), true);
		assertEquals("PEDSINITIAL", type.getName());
		type = builder.getEncounterType(patient, TestUtils.date(1992, 1, 1), false);
		assertEquals("PEDSRETURN", type.getName());
		type = builder.getEncounterType(patient, TestUtils.date(1995, 1, 1), false);
		assertEquals("PEDSRETURN", type.getName());
		type = builder.getEncounterType(patient, TestUtils.date(1995, 12, 1), false);
		assertEquals("ADULTRETURN", type.getName());
		type = builder.getEncounterType(patient, TestUtils.date(1996, 1, 1), true);
		assertEquals("ADULTINITIAL", type.getName());
	}
}
