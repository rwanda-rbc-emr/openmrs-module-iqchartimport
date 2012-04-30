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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Test class for EntityBuilder
 */
public class IQChartImportServiceTest extends BaseModuleContextSensitiveTest {

	protected static final Log log = LogFactory.getLog(IQChartImportServiceTest.class);

	@Before
	public void setup() throws Exception {
		executeDataSet("TestingDataset.xml");
	}
	
	@Test
	public void getDrugIdByConceptAndDosage() {
		IQChartImportService svc = Context.getService(IQChartImportService.class);
		assertEquals(new Integer(361), svc.getDrugIdByConceptAndDosage(747, 200.0));
		assertNull(svc.getDrugIdByConceptAndDosage(747, 300.0));
		assertNull(svc.getDrugIdByConceptAndDosage(746, 200.0));
		assertNull(svc.getDrugIdByConceptAndDosage(746, null));
	}
}
