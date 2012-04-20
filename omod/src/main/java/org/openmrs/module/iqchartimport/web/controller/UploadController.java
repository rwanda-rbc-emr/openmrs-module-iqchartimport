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

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.iqchartimport.Constants;
import org.openmrs.module.iqchartimport.Utils;
import org.openmrs.module.iqchartimport.iq.IQChartDatabase;
import org.openmrs.module.iqchartimport.iq.IQChartSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

/**
 * Upload page controller
 */
@Controller("iqChartImportUploadController")
@RequestMapping("/module/iqchartimport/upload")
public class UploadController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(HttpServletRequest request, ModelMap model) {
		Utils.checkSuperUser();
		
		HttpSession session = request.getSession();
		IQChartDatabase database = IQChartDatabase.load(session, Constants.SESSION_ATTR_DATABASE);
		
		if (database != null) {
			model.put("database", database);
			
			try {
				IQChartSession iqSession = new IQChartSession(database);			
				model.put("patientCount", iqSession.getNumPatients());
				
				iqSession.close();
			} catch (IOException e) {
				model.put("parseerror", "Unable to parse database file");
			}
		}
		
		return "/module/iqchartimport/upload";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.POST)
	public String handleSubmit(HttpServletRequest request, ModelMap model) {
		Utils.checkSuperUser();
		
		DefaultMultipartHttpServletRequest multipart = (DefaultMultipartHttpServletRequest)request;
		MultipartFile uploadFile = multipart.getFile("mdbfile");
		
		request.getSession().removeAttribute(Constants.SESSION_ATTR_DATABASE);
		
		// Process uploaded database if there is one
		if (uploadFile != null) {		
			try {
				// Copy uploaded MDB to temp file
				File tempMDBFile = File.createTempFile(uploadFile.getOriginalFilename(), ".temp");
				uploadFile.transferTo(tempMDBFile);
				
				// Store upload in session
				IQChartDatabase upload = new IQChartDatabase(uploadFile.getOriginalFilename(), tempMDBFile.getAbsolutePath());
				upload.save(request.getSession(), Constants.SESSION_ATTR_DATABASE);
				
			} catch (IOException e) {
				model.put("uploaderror", "Unable to upload database file");
			}
		}
		
		return showForm(request, model);
	}
}
