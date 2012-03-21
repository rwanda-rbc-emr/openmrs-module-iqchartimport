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

package org.openmrs.module.iqchartimport;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.api.PatientService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.iqchartimport.iq.IQDatabase;
import org.openmrs.module.iqchartimport.iq.IQChartSession;

/**
 * A database import task
 */
public class ImportTask implements Runnable {

	protected static final Log log = LogFactory.getLog(ImportTask.class);
			
	private UserContext userContext;
	private IQDatabase database;
	private boolean complete = false;
	private int totalPatients = 0;
	private int patientsImported = 0;
	private Exception exception;
	
	/**
	 * Creates a new import task
	 * @param database the database to import from
	 */
	protected ImportTask(IQDatabase database) {
		this.userContext = Context.getUserContext();
		this.database = database;
	}
	
	@Override
	public void run() {
		IQChartSession session = null;
		
		try {
			session = new IQChartSession(database);
			
			Context.openSession();
			Context.setUserContext(userContext);
			
			doImport(session);
		}
		catch (Exception ex) {
			exception = ex;
			
			System.err.println(ex);
		}
		finally {
			Context.clearSession();
			try {
				if (session != null)
					session.close();
			} catch (IOException e) {
			}
			complete = true;
		}
	}
	
	/**
	 * Does the actual import work
	 * @param session the session
	 */
	private void doImport(IQChartSession session) {
		PatientService patientSvc = Context.getPatientService();
		EntityBuilder builder = new EntityBuilder(session);
		
		List<Patient> patients = builder.getPatients();
		totalPatients = patients.size();
		
		// Import each patient
		for (Patient patient : patients) {
			patientSvc.savePatient(patient);
			
			++patientsImported;
		}
	}
	
	/**
	 * Gets the import progress
	 * @return the progress (0...1)
	 */
	public float getProgress() {
		return patientsImported / (float)totalPatients;
	}
	
	/**
	 * Gets if task is complete
	 * @return true if task is complete
	 */
	public boolean isComplete() {
		return complete;
	}

	/**
	 * Gets the exception caused by this task, if any
	 * @return the exception
	 */
	public Exception getException() {
		return exception;
	}
}
