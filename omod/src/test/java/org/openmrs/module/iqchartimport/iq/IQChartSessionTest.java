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

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.iqchartimport.TestUtils;
import org.openmrs.module.iqchartimport.iq.code.ExitCode;
import org.openmrs.module.iqchartimport.iq.code.HIVStatusPartCode;
import org.openmrs.module.iqchartimport.iq.code.MaritalCode;
import org.openmrs.module.iqchartimport.iq.code.ModeCode;
import org.openmrs.module.iqchartimport.iq.code.SexCode;
import org.openmrs.module.iqchartimport.iq.code.StatusCode;
import org.openmrs.module.iqchartimport.iq.code.TransferCode;
import org.openmrs.module.iqchartimport.iq.model.Pregnancy;
import org.openmrs.module.iqchartimport.iq.model.TBTreatment;

/**
 * Test class for IQChartSession
 */
public class IQChartSessionTest {
	
	protected static final Log log = LogFactory.getLog(IQChartSessionTest.class);

	private IQChartSession session;
	
	@Before
	public void setup() throws Exception {
		session = new IQChartSession(TestUtils.getDatabase());
	}
	
	@After
	public void cleanup() throws IOException {
		session.close();
	}
	
	@Test
	public void getNumPatients() {
		assertEquals(2601, session.getNumPatients());
	}
	
	@Test
	public void getPatients() {
		List<IQPatient> patients = session.getPatients();
		assertEquals(2601, patients.size());
	}
	
	@Test
	public void getPatient() {
		IQPatient patient = session.getPatient(235001);
		assertEquals(235001, patient.getTracnetID());
		assertEquals("Jane", patient.getFirstName());
		assertEquals("Doe", patient.getLastName());
		assertEquals("001", patient.getHospitalID());
		assertEquals(TestUtils.date(2004, 9, 29), patient.getEnrollDate());
		assertEquals(SexCode.FEMALE, patient.getSexCode());
		assertEquals(TestUtils.date(1972, 1, 1), patient.getDob());
		assertTrue(patient.isDobEstimated());
		assertEquals("Unknown", patient.getCellule());
		assertEquals("Unknown", patient.getSector());
		assertEquals("Unknown", patient.getDistrict());
		assertEquals(MaritalCode.MARRIED, patient.getMaritalStatusCode());
		assertEquals(HIVStatusPartCode.UNKNOWN, patient.getHIVStatusPartCode());
		assertEquals(ModeCode.VCT, patient.getModeCode());
		assertEquals("", patient.getModeAdmissionOther());
		assertEquals(TransferCode.NEW, patient.getTransferCode());
		assertEquals(TestUtils.date(2007, 8, 28), patient.getARVStartDate());
		assertEquals(StatusCode.ACTIVE, patient.getStatusCode());
		assertNull(patient.getExitDate());
		assertNull(patient.getExitCode());
		assertNull(patient.getExitReasonOther());
		
		patient = session.getPatient(185568);
		assertEquals(185568, patient.getTracnetID());
		assertEquals("Jane", patient.getFirstName());
		assertEquals("Doe", patient.getLastName());
		assertEquals("2040", patient.getHospitalID());
		assertEquals(TestUtils.date(2007, 12, 24), patient.getEnrollDate());
		assertEquals(SexCode.FEMALE, patient.getSexCode());
		assertEquals(TestUtils.date(1987, 2, 16), patient.getDob());
		assertFalse(patient.isDobEstimated());
		assertEquals("Unknown", patient.getCellule());
		assertEquals("Unknown", patient.getSector());
		assertEquals("Unknown", patient.getDistrict());
		assertEquals(MaritalCode.LIVINGWITHPARTNER, patient.getMaritalStatusCode());
		assertNull(patient.getHIVStatusPartCode());
		assertEquals(ModeCode.VCT, patient.getModeCode());
		assertEquals("", patient.getModeAdmissionOther());
		assertEquals(TransferCode.TRANSFER, patient.getTransferCode());
		assertEquals(TestUtils.date(2006, 8, 1), patient.getARVStartDate());
		assertEquals(StatusCode.EXITED, patient.getStatusCode());
		assertEquals(TestUtils.date(2009, 8, 14), patient.getExitDate());
		assertEquals(ExitCode.TRANSFERRED, patient.getExitCode());
		assertNull(patient.getExitReasonOther());
	}
	
	@Test
	public void getPatientPregnancies() {
		IQPatient patient = session.getPatient(236862);
		List<Pregnancy> pregnancies = session.getPatientPregnancies(patient);
		
		assertEquals(1, pregnancies.size());
		assertEquals(TestUtils.date(2007, 11, 27), pregnancies.get(0).getDateStart());
		assertEquals(TestUtils.date(2008, 8, 29), pregnancies.get(0).getEstDelivery());
		assertEquals(TestUtils.date(2008, 8, 24), pregnancies.get(0).getDateEnd());
	}
	
	@Test
	public void getPatientTBTreatments() {
		IQPatient patient = session.getPatient(236868);
		List<TBTreatment> tbTreatments = session.getPatientTBTreatments(patient);
		
		assertEquals(1, tbTreatments.size());
		assertEquals(TestUtils.date(2007, 8, 27), tbTreatments.get(0).getStartDate());
		assertNull(tbTreatments.get(0).getEndDate());
		
		patient = session.getPatient(236710);
		tbTreatments = session.getPatientTBTreatments(patient);
		
		assertEquals(1, tbTreatments.size());
		assertEquals(TestUtils.date(2007, 4, 19), tbTreatments.get(0).getStartDate());
		assertEquals(TestUtils.date(2007, 11, 29), tbTreatments.get(0).getEndDate());
	}
}
