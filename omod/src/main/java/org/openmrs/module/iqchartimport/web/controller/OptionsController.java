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

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.PatientIdentifierType;
import org.openmrs.api.context.Context;
import org.openmrs.module.iqchartimport.Options;
import org.openmrs.module.iqchartimport.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controller for module options page
 */
@Controller
@RequestMapping("/module/iqchartimport/options")
public class OptionsController {

	protected static final Log log = LogFactory.getLog(OptionsController.class);
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	public String showPage(HttpServletRequest request, ModelMap model) throws IOException {
		Utils.checkSuperUser();
		
		List<PatientIdentifierType> idTypes = Context.getPatientService().getAllPatientIdentifierTypes(false);
		
		model.put("idTypes", idTypes);
		model.put("options", Options.getInstance());
		
		return "/module/iqchartimport/options";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String handleSubmit(@ModelAttribute("options") Options options) {
		options.save();
		
		return "redirect:upload.form";
	}
}
