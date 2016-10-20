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

package org.openmrs.module.iqchartimport.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.module.iqchartimport.DrugMapping;
import org.openmrs.module.iqchartimport.IncompleteMappingException;
import org.openmrs.module.iqchartimport.iq.IQChartDatabase;
import org.openmrs.module.iqchartimport.iq.IQChartSession;
import org.openmrs.module.iqchartimport.task.ImportTask;
import org.openmrs.module.iqchartimport.task.TaskEngine;
import org.openmrs.module.iqchartimport.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Import page controller
 */
@Controller("iqChartImportImportController")
@RequestMapping("/module/iqchartimport/import")
public class ImportController {
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(HttpServletRequest request, ModelMap model) throws IOException {
		Utils.checkSuperUser();
		
		// Get uploaded database
		IQChartDatabase database = IQChartDatabase.getInstance();
		if (database == null)
			return "redirect:upload.form";
		
		// Check drug mappings
		IQChartSession session = new IQChartSession(database);
		boolean drugMappingsComplete = checkDrugMappings(session);
		session.close();
		
		model.put("database", database);
		model.put("task", TaskEngine.getCurrentTask());
		model.put("drugMappingsComplete", drugMappingsComplete);
		
		// Developer hack - end users shouldn't be stopping imports once they've started
		if (request.getParameter("stop") != null)
			TaskEngine.stopCurrentTask();
		
		return "/module/iqchartimport/import";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String handleSubmit(HttpServletRequest request, ModelMap model) throws IOException {
		Utils.checkSuperUser();
		
		// Get uploaded database
		IQChartDatabase database = IQChartDatabase.getInstance();
		if (database == null)
			return "redirect:upload.form";
		
		// Start import if there's no running import task
		ImportTask task = TaskEngine.getCurrentTask();
		if (task == null || task.isCompleted())
			TaskEngine.startImport(database);
	
		return showForm(request, model);
	}
	
	/**
	 * Checks that all drugs in IQChart have a mapping
	 * @param session the IQChart session
	 * @return true if all drugs are mapped
	 */
	private boolean checkDrugMappings(IQChartSession session) {
		DrugMapping.load();
		
		// Check all ARV and TB drugs/regimens
		for (String iqDrug : session.getAllDrugs()) {
			try {
				DrugMapping.getConcepts(iqDrug);
			}
			catch (IncompleteMappingException ex) {
				return false;
			}
		}
		
		return true;
	}
}
