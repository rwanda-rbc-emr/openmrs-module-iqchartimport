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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Drug;
import org.openmrs.Location;
import org.openmrs.PatientIdentifierType;
import org.openmrs.Program;
import org.openmrs.api.context.Context;
import org.openmrs.module.iqchartimport.IncompleteMappingException;
import org.openmrs.module.iqchartimport.Constants;
import org.openmrs.module.iqchartimport.DrugMapping;
import org.openmrs.module.iqchartimport.MappingUtils;
import org.openmrs.module.iqchartimport.Mappings;
import org.openmrs.module.iqchartimport.Utils;
import org.openmrs.module.iqchartimport.iq.IQChartDatabase;
import org.openmrs.module.iqchartimport.iq.IQChartSession;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * Controller for mappings page
 */
@Controller("iqChartImportMappingsController")
@RequestMapping("/module/iqchartimport/mappings")
@SessionAttributes({"mappings"})
public class MappingsController {

	protected static final Log log = LogFactory.getLog(MappingsController.class);
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	public String showPage(HttpServletRequest request, ModelMap model) throws IOException {
		Utils.checkSuperUser();
		
		IQChartDatabase database = IQChartDatabase.load(request.getSession(), Constants.SESSION_ATTR_DATABASE);
		
		// If database is loaded then show drug mappings
		if (database != null) {
			IQChartSession session = new IQChartSession(database);
			
			List<String> regimens = session.getStdRegimens();
			Map<String, Concept[]> conceptMappings = new TreeMap<String, Concept[]>();
			Map<String, String> conceptErrors = new TreeMap<String, String>();
			
			for (String regimen : regimens) {
				try {
					Integer[] conceptIds = DrugMapping.getRegimenConceptIds(regimen);
					conceptMappings.put(regimen, getConcepts(conceptIds));
				}
				catch (IncompleteMappingException ex) {	
					conceptMappings.put(regimen, null);
					conceptErrors.put(regimen, ex.getMessage());
				}
			}
			
			model.put("regimens", regimens);
			model.put("conceptMappings", conceptMappings);
			model.put("conceptErrors", conceptErrors);
			
			session.close();
		}
		
		List<PatientIdentifierType> identifierTypes = Context.getPatientService().getAllPatientIdentifierTypes();
		List<Program> programs = Context.getProgramWorkflowService().getAllPrograms();
		List<Location> locations = Context.getLocationService().getAllLocations();
		
		String provList = Context.getAdministrationService().getGlobalProperty(Constants.PROP_ADDRESS_ALL_PROVINCES);
		String[] allProvinces = provList != null ? provList.split(",") : new String[]{};
		
		model.put("identifierTypes", identifierTypes);
		model.put("allProvinces", allProvinces);
		model.put("programs", programs);
		model.put("locations", locations);
		model.put("mappings", Mappings.getInstance());
		model.put("encounterProvider", MappingUtils.getEncounterProvider());
		
		return "/module/iqchartimport/mappings";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String handleSubmit(HttpServletRequest request, @ModelAttribute("mappings") Mappings mappings, @RequestParam(required = false) Boolean createProvider) {
		Utils.checkSuperUser();
		
		if (createProvider != null && createProvider) {
			MappingUtils.createEncounterProvider();
			request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Provider created");
		}
		else {
			mappings.save();
			request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Mappings saved");
		}
		
		return "redirect:mappings.form";
	}
	
	protected static Concept[] getConcepts(Integer[] conceptIds) {
		Concept[] concepts = new Concept[conceptIds.length];
		for (int c = 0; c < conceptIds.length; ++c)
			concepts[c] = Context.getConceptService().getConcept(conceptIds[c]);
		return concepts;
	}
	
	protected static Drug[] getDrugs(Integer[] drugIds) {
		Drug[] drugs = new Drug[drugIds.length];
		for (int d = 0; d < drugIds.length; ++d)
			drugs[d] = Context.getConceptService().getDrug(drugIds[d]);
		return drugs;
	}
}
