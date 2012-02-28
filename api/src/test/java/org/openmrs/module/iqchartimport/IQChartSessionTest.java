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

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for IQChartSession
 */
public class IQChartSessionTest {
	
	protected static final Log log = LogFactory.getLog(IQChartSessionTest.class);

	private File tempDBFile;
	private IQChartSession session;
	
	@Before
	public void setup() throws Exception {
		tempDBFile = TestingUtils.copyResourceToTempFile("test.mdb", ".mdb");
		log.info("Copied database to temp file: " + tempDBFile.getAbsolutePath());
		
		IQChartDatabase database = new IQChartDatabase("test.mdb", tempDBFile.getAbsolutePath());
		session = new IQChartSession(database);
	}
	
	@After
	public void cleanup() {
		//tempDBFile.delete();
	}
	
	@Test
	public void getNumPatients() {
		Assert.assertEquals(2601, session.getNumPatients());
	}
}
