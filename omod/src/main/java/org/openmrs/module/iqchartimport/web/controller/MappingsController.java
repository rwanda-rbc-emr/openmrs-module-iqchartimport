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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptClass;
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
	
	protected static List<String> getAllIQChartDrugs(IQChartSession session) {
		Set<String> iqARVDrugs = session.getStdRegimens(true);
		Set<String> iqTBDrugs = session.getStdTBDrugs();
		Set<String> allIQDrugSet = new TreeSet<String>();
		allIQDrugSet.addAll(iqARVDrugs);
		allIQDrugSet.addAll(iqTBDrugs);
		return new ArrayList<String>(allIQDrugSet);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	public String showPage(HttpServletRequest request, ModelMap model) throws IOException {
		Utils.checkSuperUser();
		
		IQChartDatabase database = IQChartDatabase.load(request.getSession(), Constants.SESSION_ATTR_DATABASE);
		
		// If database is loaded then show drug mappings
		if (database != null) {
			IQChartSession session = new IQChartSession(database);
			
			// Get set of all ARV and TB drugs/regimens
			List<String> iqDrugs = getAllIQChartDrugs(session);
			
			ConceptClass drugClass = Context.getConceptService().getConceptClassByName("Drug");
			List<Concept> drugConcepts = Context.getConceptService().getConceptsByClass(drugClass);
			Map<String, List<Integer>> drugMappings = new HashMap<String, List<Integer>>();
			
			DrugMapping.load();
			
			for (String iqDrug : iqDrugs) {
				try {
					List<Integer> conceptIds = DrugMapping.getConcepts(iqDrug);
					drugMappings.put(iqDrug, conceptIds);
				}
				catch (IncompleteMappingException ex) {}
			}
			
			// Store the IQChart drug list in the session
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("iqDrugs", iqDrugs);
				
			model.put("drugConcepts", drugConcepts);
			model.put("iqDrugs", iqDrugs);
			model.put("drugMappings", drugMappings);
			
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
	public String handleSubmit(HttpServletRequest request, @ModelAttribute("mappings") Mappings mappings, @RequestParam(required = false) Boolean createProvider) throws IOException {
		Utils.checkSuperUser();
		
		if (createProvider != null && createProvider) {
			MappingUtils.createEncounterProvider();
			request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Provider created");
		}
		else if (request.getParameter("siteLocationId") != null) {
			handleEntityMappingsSave(request, mappings);
		}
		else if ("1".equals(request.getParameter("guessDrugs"))) {
			handleDrugMappingsGuess(request);
		}
		else
			handleDrugMappingsSave(request);
		
		return "redirect:mappings.form";
	}

	/**
	 * Handles a save request on the entities form
	 * @param request
	 * @param mappings
	 */
	private void handleEntityMappingsSave(HttpServletRequest request, Mappings mappings) {
		mappings.save();
		request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Entity mappings saved");
	}
	
	/**
	 * Handles a guess request on the drugs form
	 * @param request
	 * @throws IOException
	 */
	private void handleDrugMappingsGuess(HttpServletRequest request) throws IOException {
		DrugMapping.clear();
		
		IQChartDatabase database = IQChartDatabase.load(request.getSession(), Constants.SESSION_ATTR_DATABASE);
		IQChartSession session = new IQChartSession(database);
		
		// Get set of all ARV and TB drugs/regimens
		List<String> iqDrugs = getAllIQChartDrugs(session);
		DrugMapping.guess(iqDrugs);
		
		session.close();
		
		DrugMapping.save();
		request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Drug mappings guessed");
	}
	
	/**
	 * Handles a save request on the drugs form
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	private void handleDrugMappingsSave(HttpServletRequest request) {
		DrugMapping.clear();

		HttpSession httpSession = request.getSession();	
		List<String> iqDrugs = (List<String>)httpSession.getAttribute("iqDrugs");
		
		for (String param : (Set<String>)request.getParameterMap().keySet()) {
			if (param.startsWith("drugs-")) {
				// Get index and lookup up IQChart drug list to get drug name
				int iqDrugID = Integer.parseInt(param.substring(6));
				String iqDrug = iqDrugs.get(iqDrugID);
				
				// Get OpenMRS drug concept ids
				String[] conceptStrIds = request.getParameterValues(param);
				List<Integer> conceptIds = new ArrayList<Integer>(0);
				for (String conceptStrId : conceptStrIds) {
					int drugConceptId = Integer.parseInt(conceptStrId);
					conceptIds.add(drugConceptId);
				}
				
				DrugMapping.setConceptList(iqDrug, conceptIds);
			}
		}
		
		DrugMapping.save();
		request.getSession().setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Drug mappings saved");
	}
}
