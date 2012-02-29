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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.iqchartimport.Constants;
import org.openmrs.module.iqchartimport.IQChartSession;
import org.openmrs.module.iqchartimport.IQChartDatabase;
import org.openmrs.module.iqchartimport.IQPatient;
import org.openmrs.module.iqchartimport.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Patients page controller
 */
@Controller("iqChartImportPatientController")
@RequestMapping("/module/iqchartimport/patient")
public class PatientController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	public String showPage(HttpServletRequest request, @RequestParam("tracnetID") Integer tracnetID, ModelMap model) throws IOException {
		Utils.checkSuperUser();
		
		IQChartDatabase database = IQChartDatabase.load(request.getSession(), Constants.SESSION_ATTR_DATABASE);
		if (database == null)
			return "redirect:upload.form";
		
		model.put("database", database);

		IQChartSession iqChartDB = new IQChartSession(database);	
		IQPatient patient = iqChartDB.getPatient(tracnetID);
			
		model.put("patient", patient);
		model.put("patientObs", iqChartDB.getPatientObs(patient));
			
		iqChartDB.close();
		
		return "/module/iqchartimport/patient";
	}
}
