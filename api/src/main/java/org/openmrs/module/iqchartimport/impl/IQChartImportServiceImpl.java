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

package org.openmrs.module.iqchartimport.impl;

import java.util.List;

import org.openmrs.Drug;
import org.openmrs.api.context.Context;
import org.openmrs.module.iqchartimport.IQChartImportService;
import org.openmrs.module.iqchartimport.db.IQChartImportDAO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Implementation of the module service
 */
public class IQChartImportServiceImpl implements IQChartImportService {

	@Autowired
	private IQChartImportDAO dao;
	
	/**
	 * @see IQChartImportService#getARVDrugs()
	 */
	@Override
	public List<Drug> getARVDrugs() {
		String conceptIds = Context.getAdministrationService().getGlobalProperty("camerwa.arvConceptIdList");
		return dao.getDrugsFromConcepts(conceptIds);
	}
}
