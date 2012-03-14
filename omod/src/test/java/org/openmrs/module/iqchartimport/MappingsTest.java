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

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Test cases for Mappings class
 */
public class MappingsTest extends BaseModuleContextSensitiveTest {

	protected static final Log log = LogFactory.getLog(MappingsTest.class);
	
	@Before
	public void setup() throws Exception {
		// Load data set that includes some global properties
		executeDataSet("TestingDataset.xml");
	}
	
	@Test
	public void load() {
		TestUtils.setGlobalProperty(Constants.PROP_ADDRESS_PROVINCE, "Prov123");
		
		Mappings.getInstance().load();
		
		Assert.assertEquals("Prov123", Mappings.getInstance().getAddressProvince());
	}
}
