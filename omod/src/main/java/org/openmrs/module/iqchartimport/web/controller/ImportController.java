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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.iqchartimport.Constants;
import org.openmrs.module.iqchartimport.Utils;
import org.openmrs.module.iqchartimport.iq.IQChartDatabase;
import org.openmrs.module.iqchartimport.task.TaskEngine;
import org.openmrs.module.iqchartimport.task.ImportTask;
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
	
	protected static final Log log = LogFactory.getLog(ImportController.class);
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(HttpServletRequest request, ModelMap model) {
		Utils.checkSuperUser();
		
		// Get uploaded database
		IQChartDatabase database = IQChartDatabase.load(request.getSession(), Constants.SESSION_ATTR_DATABASE);
		if (database == null)
			return "redirect:upload.form";
		
		model.put("task", TaskEngine.getCurrentTask());
		
		// Temporary!!!
		if (request.getParameter("stop") != null)
			TaskEngine.stopCurrentTask();
		
		return "/module/iqchartimport/import";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String handleSubmit(HttpServletRequest request, ModelMap model) {
		Utils.checkSuperUser();
		
		// Get uploaded database
		IQChartDatabase database = IQChartDatabase.load(request.getSession(), Constants.SESSION_ATTR_DATABASE);
		if (database == null)
			return "redirect:upload.form";
		
		// Start import if there's no running import task
		ImportTask task = TaskEngine.getCurrentTask();
		if (task == null || task.isCompleted())
			TaskEngine.startImport(database, true);
	
		return showForm(request, model);
	}
}
