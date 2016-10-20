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

package org.openmrs.module.iqchartimport.web.controller.csv;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.iqchartimport.EntityBuilder;
import org.openmrs.module.iqchartimport.Mappings;
import org.openmrs.module.iqchartimport.task.ImportIssue;
import org.openmrs.module.iqchartimport.task.ImportTask;
import org.openmrs.module.iqchartimport.task.TaskEngine;
import org.openmrs.module.iqchartimport.util.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Import status AJAX controller
 */
@Controller("iqChartImportExportIssuesController")
@RequestMapping("/module/iqchartimport/exportIssues")
public class ExportIssuesController {

	@RequestMapping(method = RequestMethod.GET)
	public void getProgress(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Utils.checkSuperUser();
		
		ImportTask task = TaskEngine.getCurrentTask();
		StringBuilder csv = new StringBuilder();
		csv.append("TRACnet ID,Issue\n");
		
		if (task != null) {
			EntityBuilder builder = task.getEntityBuilder();
			
			for (ImportIssue issue : task.getIssues()) {
				String tracnetID = issue.getPatient().getPatientIdentifier(builder.getTRACnetIDType()).getIdentifier();
				csv.append(tracnetID + "," + issue.getMessage() + "\n");
			}
		}
		
		// Filename based on location
		int locationId = Mappings.getInstance().getSiteLocationId();
		Location location = (locationId > 0) ? Context.getLocationService().getLocation(locationId) : null;
		String locationName = (location != null) ? location.getName().replace(" ", "_") : "Unknown_Location";
		String filename = locationName + "-issues.csv";
				
		response.setHeader("Content-Disposition", "attachment;filename=" + filename);
		response.setContentType("application/csv");		
		response.getWriter().write(csv.toString());
	}
}
