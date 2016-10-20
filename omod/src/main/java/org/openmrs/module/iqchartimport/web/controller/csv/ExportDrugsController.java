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
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.iqchartimport.DrugMapping;
import org.openmrs.module.iqchartimport.IncompleteMappingException;
import org.openmrs.module.iqchartimport.Mappings;
import org.openmrs.module.iqchartimport.iq.IQChartDatabase;
import org.openmrs.module.iqchartimport.iq.IQChartSession;
import org.openmrs.module.iqchartimport.util.Utils;
import org.openmrs.util.OpenmrsUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Mappings download CSV controller
 */
@Controller("iqChartImportExportDrugsController")
@RequestMapping("/module/iqchartimport/exportDrugs")
public class ExportDrugsController {

	@RequestMapping(method = RequestMethod.GET)
	public void getMappings(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Utils.checkSuperUser();
		
		StringBuilder csv = new StringBuilder();
		csv.append("IQChart,OpenMRS Concepts\n");
		
		IQChartDatabase iqDatabase = IQChartDatabase.getInstance();
		IQChartSession session = new IQChartSession(iqDatabase);
		
		for (String iqDrug : session.getAllDrugs()) {
			String conceptNamesStr = "";
			try {
				List<Integer> conceptIds = DrugMapping.getConcepts(iqDrug);
				List<String> conceptNames = getConceptNames(conceptIds);
				conceptNamesStr = OpenmrsUtil.join(conceptNames, ",");
			}
			catch (IncompleteMappingException ex) {}
			
			csv.append("\"" + iqDrug + "\",\"" + conceptNamesStr + "\"\n");
		}
		
		// Filename based on location
		int locationId = Mappings.getInstance().getSiteLocationId();
		Location location = (locationId > 0) ? Context.getLocationService().getLocation(locationId) : null;
		String locationName = (location != null) ? location.getName().replace(" ", "_") : "Unknown_Location";
		String filename = locationName + "-drugs.csv";
		
		session.close();
		
		response.setHeader("Content-Disposition", "attachment;filename=" + filename);
		response.setContentType("application/csv");		
		response.getWriter().write(csv.toString());
	}
	
	/**
	 * Converts a list of concept IDs to concept names
	 * @param conceptIds the concept IDs
	 * @return the concept names
	 */
	protected static List<String> getConceptNames(List<Integer> conceptIds) {
		List<String> names = new ArrayList<String>();
		
		for (Integer conceptId : conceptIds) {
			Concept concept = Context.getConceptService().getConcept(conceptId);
			String name = concept != null ? concept.getName().getName() : "<Invalid>";
			names.add(name);
		}
		
		return names;
	}
}
