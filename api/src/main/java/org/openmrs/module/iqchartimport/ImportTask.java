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
import org.openmrs.Encounter;
import org.openmrs.Patient;
import org.openmrs.PatientProgram;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.iqchartimport.iq.IQChartDatabase;
import org.openmrs.module.iqchartimport.iq.IQChartSession;

/**
 * A database import task
 */
public class ImportTask implements Runnable {

	protected static final Log log = LogFactory.getLog(ImportTask.class);
			
	private UserContext userContext;
	private IQChartDatabase database;
	private boolean full = false;
	private boolean complete = false;
	private int totalPatients = 0;
	private int patientsImported = 0;
	private int encountersImported = 0;
	private Exception exception;
	
	/**
	 * Creates a new import task
	 * @param database the database to import from
	 * @param full true for full import, false for just patients
	 */
	protected ImportTask(IQChartDatabase database, boolean full) {
		this.userContext = Context.getUserContext();
		this.database = database;
		this.full = full;
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
		EntityBuilder builder = new EntityBuilder(session);
		
		List<Patient> patients = builder.getPatients();
		totalPatients = patients.size();
		
		// Import each patient
		for (Patient patient : patients) {
			// Get TRACnet ID
			int tracnetID = Integer.parseInt(patient.getPatientIdentifier().getIdentifier());
			
			// Look for patients with same TRACnet ID
			List<Patient> matchPatients = Context.getPatientService().getPatients(null, tracnetID + "", null, true);
			
			if (matchPatients.size() > 0) {
				// TODO log duplicate
				continue;
			}
			
			// Save patient to database
			Context.getPatientService().savePatient(patient);
			
			if (full) {			
				// Import patient programs
				for (PatientProgram patientProgram : builder.getPatientPrograms(patient, tracnetID)) {
					
					Context.getProgramWorkflowService().savePatientProgram(patientProgram);
				}
				
				// Import patient encounters
				for (Encounter encounter : builder.getPatientEncounters(patient, tracnetID)) {
					
					Context.getEncounterService().saveEncounter(encounter);
					
					++encountersImported;
				}
			}
			
			++patientsImported;
		}
	}
	
	/**
	 * Gets if task is complete
	 * @return true if task is complete
	 */
	public boolean isComplete() {
		return complete;
	}
	
	/**
	 * Gets the import progress
	 * @return the progress (0...1)
	 */
	public float getProgress() {
		return patientsImported / (float)totalPatients;
	}
	
	/**
	 * Gets the number of patients imported
	 * @return the number
	 */
	public int getPatientsImported() {
		return patientsImported;
	}

	/**
	 * Gets the number of encounters imported
	 * @return the number
	 */
	public int getEncountersImported() {
		return encountersImported;
	}

	/**
	 * Gets the exception caused by this task, if any
	 * @return the exception
	 */
	public Exception getException() {
		return exception;
	}
}
