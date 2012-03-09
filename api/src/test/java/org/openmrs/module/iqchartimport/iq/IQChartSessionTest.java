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

import java.io.File;
import java.util.GregorianCalendar;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.iqchartimport.TestingUtils;
import org.openmrs.module.iqchartimport.iq.IQChartDatabase;
import org.openmrs.module.iqchartimport.iq.IQChartSession;
import org.openmrs.module.iqchartimport.iq.code.ExitCode;
import org.openmrs.module.iqchartimport.iq.code.HIVStatusCode;
import org.openmrs.module.iqchartimport.iq.code.MaritalCode;
import org.openmrs.module.iqchartimport.iq.code.ModeCode;
import org.openmrs.module.iqchartimport.iq.code.SexCode;
import org.openmrs.module.iqchartimport.iq.code.StatusCode;

/**
 * Test class for IQChartSession
 */
public class IQChartSessionTest {
	
	protected static final Log log = LogFactory.getLog(IQChartSessionTest.class);

	private File tempZipFile, tempMdbFile;
	private IQChartSession session;
	
	@Before
	public void setup() throws Exception {
		// Extract embedded test database
		tempZipFile = TestingUtils.copyResource("/HIVData.mdb.zip");
		tempMdbFile = TestingUtils.extractZipEntry(tempZipFile, "HIVData.mdb");
			
		IQChartDatabase database = new IQChartDatabase("HIVData.mdb", tempMdbFile.getAbsolutePath());
		session = new IQChartSession(database);
	}
	
	@After
	public void cleanup() {
		// Delete temporary files
		tempMdbFile.delete();
		tempZipFile.delete();
	}
	
	@Test
	public void getNumPatients() {
		Assert.assertEquals(2601, session.getNumPatients());
	}
	
	@Test
	public void getPatients() {
		List<IQPatient> patients = session.getPatients();
		Assert.assertEquals(2601, patients.size());
	}
	
	@Test
	public void getPatient() {
		IQPatient patient = session.getPatient(235001);
		Assert.assertEquals(235001, patient.getTracnetID());
		Assert.assertEquals("Jane", patient.getFirstName());
		Assert.assertEquals("Doe", patient.getLastName());
		Assert.assertEquals("001", patient.getHospitalID());
		Assert.assertEquals(new GregorianCalendar(2004, GregorianCalendar.SEPTEMBER, 29).getTime(), patient.getEnrollDate());
		Assert.assertEquals(SexCode.FEMALE, patient.getSexCode());
		Assert.assertEquals(new GregorianCalendar(1972, GregorianCalendar.JANUARY, 1).getTime(), patient.getDob());
		Assert.assertTrue(patient.isDobEstimated());
		Assert.assertEquals("Unknown", patient.getCellule());
		Assert.assertEquals("Unknown", patient.getSector());
		Assert.assertEquals("Unknown", patient.getDistrict());
		Assert.assertEquals(MaritalCode.MARRIED, patient.getMaritalStatusCode());
		Assert.assertEquals(HIVStatusCode.UNKNOWN, patient.getHIVStatusPartCode());
		Assert.assertEquals(ModeCode.VCT, patient.getModeCode());
		Assert.assertEquals("", patient.getModeAdmissionOther());
		Assert.assertFalse(patient.isNewTransfer());
		Assert.assertEquals(new GregorianCalendar(2007, GregorianCalendar.AUGUST, 28).getTime(), patient.getARVStartDate());
		Assert.assertEquals(StatusCode.ACTIVE, patient.getStatusCode());
		Assert.assertNull(patient.getExitDate());
		Assert.assertNull(patient.getExitCode());
		Assert.assertNull(patient.getExitReasonOther());
		
		patient = session.getPatient(185568);
		Assert.assertEquals(185568, patient.getTracnetID());
		Assert.assertEquals("Jane", patient.getFirstName());
		Assert.assertEquals("Doe", patient.getLastName());
		Assert.assertEquals("2040", patient.getHospitalID());
		Assert.assertEquals(new GregorianCalendar(2007, GregorianCalendar.DECEMBER, 24).getTime(), patient.getEnrollDate());
		Assert.assertEquals(SexCode.FEMALE, patient.getSexCode());
		Assert.assertEquals(new GregorianCalendar(1987, GregorianCalendar.FEBRUARY, 16).getTime(), patient.getDob());
		Assert.assertFalse(patient.isDobEstimated());
		Assert.assertEquals("Unknown", patient.getCellule());
		Assert.assertEquals("Unknown", patient.getSector());
		Assert.assertEquals("Unknown", patient.getDistrict());
		Assert.assertEquals(MaritalCode.LIVINGWITHPARTNER, patient.getMaritalStatusCode());
		Assert.assertNull(patient.getHIVStatusPartCode());
		Assert.assertEquals(ModeCode.VCT, patient.getModeCode());
		Assert.assertEquals("", patient.getModeAdmissionOther());
		Assert.assertTrue(patient.isNewTransfer());
		Assert.assertEquals(new GregorianCalendar(2006, GregorianCalendar.AUGUST, 1).getTime(), patient.getARVStartDate());
		Assert.assertEquals(StatusCode.EXITED, patient.getStatusCode());
		Assert.assertEquals(new GregorianCalendar(2009, GregorianCalendar.AUGUST, 14).getTime(), patient.getExitDate());
		Assert.assertEquals(ExitCode.TRANSFERRED, patient.getExitCode());
		Assert.assertNull(patient.getExitReasonOther());
	}
}
