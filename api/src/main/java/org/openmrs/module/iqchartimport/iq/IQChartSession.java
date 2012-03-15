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

package org.openmrs.module.iqchartimport.iq;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.iqchartimport.iq.code.ExitCode;
import org.openmrs.module.iqchartimport.iq.code.HIVStatusCode;
import org.openmrs.module.iqchartimport.iq.code.MaritalCode;
import org.openmrs.module.iqchartimport.iq.code.ModeCode;
import org.openmrs.module.iqchartimport.iq.code.SexCode;
import org.openmrs.module.iqchartimport.iq.code.StatusCode;
import org.openmrs.module.iqchartimport.iq.code.TransferCode;
import org.openmrs.module.iqchartimport.iq.obs.BaseIQObs;
import org.openmrs.module.iqchartimport.iq.obs.IQCD4Obs;
import org.openmrs.module.iqchartimport.iq.obs.IQHeightObs;
import org.openmrs.module.iqchartimport.iq.obs.IQWeightObs;

import com.healthmarketscience.jackcess.Cursor;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.Table;

/**
 * Provides access to data in an IQChart database
 */
public class IQChartSession {

	protected static final Log log = LogFactory.getLog(IQChartSession.class);
	
	/**
	 * Table names
	 */
	private static final String TABLE_PATIENT = "mst_ptn";
	private static final String TABLE_CD4 = "dtl_CD4";
	private static final String TABLE_HEIGHT = "dtl_height";
	private static final String TABLE_WEIGHT = "dtl_weight";

	/**
	 * Key names
	 */
	private static final String PATIENT_KEY = "TRACNetID";
	
	private Database database;
	private Map<Integer, IQPatient> patientCache = new HashMap<Integer, IQPatient>();
	
	/**
	 * Opens the given file as an IQChart database
	 * @param path the MDB file path to open
	 * @throws IOException if unable to open file
	 */
	public IQChartSession(IQChartDatabase database) throws IOException {
		this.database = Database.open(new File(database.getPath()));
	}
	
	/**
	 * Gets the number of patients
	 * @return the number or -1 if error occurred
	 */
	public int getNumPatients() {
		try {
			Table patTable = database.getTable(TABLE_PATIENT);
			return patTable.getRowCount();
		} catch (IOException e) {
			return -1;
		}
	}
	
	/**
	 * Loads all patients from the database
	 * @return the patients or null if error occurred
	 */
	public List<IQPatient> getPatients() {
		List<IQPatient> patients = new ArrayList<IQPatient>();
		try {
			Table table = database.getTable(TABLE_PATIENT);
			for (Map<String, Object> row : table) {
				IQPatient patient = patientFromRow(row);
				patients.add(patient);
			}
			return patients;
			
		} catch (IOException e) {		
		}
		return null;
	}
	
	/**
	 * Gets a patient by TracNet ID 
	 * @param tracnetID the TracNet ID
	 * @return the patient or null
	 */
	public IQPatient getPatient(int tracnetID) {
		// Check cache first
		if (patientCache.containsKey(tracnetID))
			return patientCache.get(tracnetID);
		
		try {
			Table table = database.getTable(TABLE_PATIENT);
			Map<String, Object> row = Cursor.findRow(table, Collections.singletonMap("TRACNetID", (Object)new Integer(tracnetID)));
			if (row != null)
				return patientFromRow(row);
			
		} catch (IOException e) {
		}
		return null;
	}
	
	/**
	 * Gets all the obs for the given patient
	 * @param patient the patient
	 * @return the obs
	 */
	public List<BaseIQObs> getPatientObs(IQPatient patient) {
		List<BaseIQObs> allObs = new ArrayList<BaseIQObs>();
		allObs.addAll(getPatientCD4Obs(patient));
		allObs.addAll(getPatientHeightObs(patient));
		allObs.addAll(getPatientWeightObs(patient));
		
		Collections.sort(allObs);
		return allObs;
	}
	
	/**
	 * Gets all the CD4 obs for the given patient
	 * @param patient the patient
	 * @return the obs
	 */
	public List<IQCD4Obs> getPatientCD4Obs(IQPatient patient) {
		List<IQCD4Obs> obslist = new ArrayList<IQCD4Obs>();
		try {
			Table table = database.getTable(TABLE_CD4);
			
			for (Map<String, Object> row : table) {
				if ((Integer)row.get("TRACNetID") == patient.getTracnetID()) {
					IQCD4Obs obs = new IQCD4Obs((Date)row.get("date"), (Short)row.get("CD4count"));
					obs.setTestType((String)row.get("TestType"));
					obslist.add(obs);
				}
			}
			return obslist;
			
		} catch (IOException e) {		
		}
		return null;
	}
	
	/**
	 * Gets all the height obs for the given patient
	 * @param patient the patient
	 * @return the obs
	 */
	public List<IQHeightObs> getPatientHeightObs(IQPatient patient) {
		List<IQHeightObs> obslist = new ArrayList<IQHeightObs>();
		try {
			Table table = database.getTable(TABLE_HEIGHT);
			
			for (Map<String, Object> row : table) {
				if ((Integer)row.get(PATIENT_KEY) == patient.getTracnetID()) {
					obslist.add(new IQHeightObs((Date)row.get("date"), (Short)row.get("Height")));
				}
			}
			return obslist;
			
		} catch (IOException e) {		
		}
		return null;
	}
	
	/**
	 * Gets all the weight obs for the given patient
	 * @param patient the patient
	 * @return the obs
	 */
	public List<IQWeightObs> getPatientWeightObs(IQPatient patient) {
		List<IQWeightObs> obslist = new ArrayList<IQWeightObs>();
		try {
			Table table = database.getTable(TABLE_WEIGHT);
			
			for (Map<String, Object> row : table) {
				if ((Integer)row.get("TRACNetID") == patient.getTracnetID()) {
					obslist.add(new IQWeightObs((Date)row.get("date"), (Short)row.get("weight")));
				}
			}
			return obslist;
			
		} catch (IOException e) {		
		}
		return null;
	}
	
	/**
	 * Gets a patient from a row
	 * @param row the row
	 * @return the patient
	 */
	protected IQPatient patientFromRow(Map<String, Object> row) {
		int tracnetID = (Integer)row.get("TRACNetID");				
		IQPatient patient = new IQPatient(tracnetID);
		
		patient.setLastName((String)row.get("lastName"));
		patient.setFirstName((String)row.get("firstName"));
		patient.setHospitalID((String)row.get("hospitalID"));
		patient.setEnrollDate((Date)row.get("enrollDate"));
		patient.setSexCode(SexCode.fromByte((Byte)row.get("sexCode")));
		patient.setDob((Date)row.get("DOB"));
		patient.setDobEstimated((Boolean)row.get("dobEstimated"));
		patient.setCellule((String)row.get("cellule"));
		patient.setSector((String)row.get("sector"));
		patient.setDistrict((String)row.get("district"));
		patient.setMaritalStatusCode(MaritalCode.fromByte((Byte)row.get("maritalStatusCode")));
		patient.setHIVStatusPartCode(HIVStatusCode.fromByte((Byte)row.get("HIVStatusPartCode")));
		patient.setModeCode(ModeCode.fromByte((Byte)row.get("ModeCode")));
		patient.setModeAdmissionOther((String)row.get("ModeAdmissionOther"));
		patient.setTransferCode(TransferCode.fromByte((Byte)row.get("NewTransfer")));
		patient.setARVStartDate((Date)row.get("ARVStartDate"));
		patient.setStatusCode(StatusCode.fromByte((Byte)row.get("StatusCode")));
		patient.setExitDate((Date)row.get("ExitDate"));
		patient.setExitCode(ExitCode.fromByte((Byte)row.get("ExitCode")));
		patient.setExitReasonOther((String)row.get("ExitReasonOther"));
		
		// Store in cache
		patientCache.put(tracnetID, patient);
		
		return patient;
	}
	
	/**
	 * Closes the database
	 * @throws IOException
	 */
	public void close() throws IOException {
		database.close();
	}
}
