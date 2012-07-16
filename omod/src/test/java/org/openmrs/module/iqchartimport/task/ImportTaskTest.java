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

package org.openmrs.module.iqchartimport.task;

import static junit.framework.Assert.*;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.Patient;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.module.iqchartimport.TestUtils;
import org.openmrs.module.iqchartimport.task.ImportTask;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Test class for ImportTask
 */
//Disabled until all required concepts are in test data 
@Ignore
public class ImportTaskTest extends BaseModuleContextSensitiveTest {

	protected static final Log log = LogFactory.getLog(ImportTaskTest.class);
	
	// IDs of patients from test DB to check
	private int[] testIDs = { 235001, 185568 };
	
	@Before
	public void setup() throws Exception {	
		executeDataSet("TestingDataset.xml");	
	}
	
	@Test
	public void startImport() throws InterruptedException, IOException {
		ImportTask task = new ImportTask(TestUtils.getDatabase(), false);
		
		// Get number of existing patients
		PatientService patientSvc = Context.getPatientService();
		int initialPatients = patientSvc.getAllPatients().size();
		
		// Run task in new thread
		Thread thread = new Thread(task);
		thread.start();
		
		// Wait for task to finish
		int progBarTicks = 0;
		while (!task.isCompleted()) {
			Thread.sleep(1000);
			progBarTicks = TestUtils.progressBar(task.getProgress(), progBarTicks);
		}
		
		if (task.getException() != null)
			task.getException().printStackTrace();
		
		// Check no exeception
		assertNull(task.getException());
		
		// Check number of patients added to DB
		assertEquals(2601, task.getImportedPatients());
		assertEquals(2601, patientSvc.getAllPatients().size() - initialPatients);
		
		// Check we can find the test patients
		for (int testID : testIDs) {
			List<Patient> matchPatients = patientSvc.getPatients(null, testID + "", null, true);
			assertEquals(1, matchPatients.size());
		}
	}
}