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
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.module.iqchartimport.Constants;
import org.openmrs.module.iqchartimport.EntityBuilder;
import org.openmrs.module.iqchartimport.IncompleteMappingException;
import org.openmrs.module.iqchartimport.Utils;
import org.openmrs.module.iqchartimport.iq.IQChartDatabase;
import org.openmrs.module.iqchartimport.iq.IQChartSession;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Encounter obs dialog controller
 */
@Controller("iqChartImportObservationsController")
@RequestMapping("/module/iqchartimport/observations")
public class ObservationsController {
	
	protected static final Log log = LogFactory.getLog(ObservationsController.class);
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	public String showPage(HttpServletRequest request, @RequestParam("tracnetID") Integer tracnetID, @RequestParam("timestamp") Long timestamp, ModelMap model) throws IOException {
		Utils.checkSuperUser();
		
		IQChartDatabase database = IQChartDatabase.load(request.getSession(), Constants.SESSION_ATTR_DATABASE);
		if (database == null)
			return "redirect:upload.form";
		
		model.put("database", database);
		IQChartSession session = new IQChartSession(database);
			
		try {
			EntityBuilder builder = new EntityBuilder(session);
			Patient patient = builder.getPatient(tracnetID);
			List<Encounter> encounters = builder.getPatientEncounters(patient, tracnetID);
			
			// Find encounter on requested date
			Date encDate = new Date(timestamp);
			Encounter encounter = null;
			for (Encounter enc : encounters) {
				if (enc.getEncounterDatetime().equals(encDate))
					encounter = enc;
			}
			
			model.put("observations", encounter.getAllObs());
			
			return "/module/iqchartimport/observations";
		}
		catch (IncompleteMappingException ex) {
			String message = ex.getMessage() != null ? ex.getMessage() : "Incomplete entity mappings";
			request.getSession().setAttribute(WebConstants.OPENMRS_ERROR_ATTR, message);
			return "redirect:mappings.form";
		}
		finally {
			session.close();
		}
	}
}
