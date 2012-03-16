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

import java.util.Date;

import org.openmrs.module.iqchartimport.iq.code.ExitCode;
import org.openmrs.module.iqchartimport.iq.code.HIVStatusPartCode;
import org.openmrs.module.iqchartimport.iq.code.MaritalCode;
import org.openmrs.module.iqchartimport.iq.code.ModeCode;
import org.openmrs.module.iqchartimport.iq.code.SexCode;
import org.openmrs.module.iqchartimport.iq.code.StatusCode;
import org.openmrs.module.iqchartimport.iq.code.TransferCode;

/**
 * A patient in an IQChart database
 */
public class IQPatient {
	
	private int tracnetID;
	private String firstName;
	private String lastName;
	private String hospitalID;
	private Date enrollDate;
	private SexCode sexCode;
	private Date dob;
	private boolean dobEstimated;
	private String cellule;
	private String sector;
	private String district;
	private MaritalCode maritalStatusCode;
	private HIVStatusPartCode hivStatusPartCode;
	private ModeCode modeCode;
	private String modeAdmissionOther;
	private TransferCode transferCode;
	private Date arvStartDate;
	private StatusCode statusCode;
	private Date exitDate;
	private ExitCode exitCode;
	private String exitReasonOther;
	
	/**
	 * Constructs new patient
	 * @param tracnetID the TracNet ID
	 */
	public IQPatient(int tracnetID) {
		this.tracnetID = tracnetID;
	}
	
	/**
	 * Gets the TracNet ID
	 * @return the ID
	 */
	public int getTracnetID() {
		return tracnetID;
	}
	
	/**
	 * Sets the TracNet ID
	 * @param tracnetID the ID
	 */
	public void setTracnetID(int tracnetID) {
		this.tracnetID = tracnetID;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getHospitalID() {
		return hospitalID;
	}

	public void setHospitalID(String hospitalID) {
		this.hospitalID = hospitalID;
	}
	
	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}
	
	public SexCode getSexCode() {
		return sexCode;
	}

	public void setSexCode(SexCode sexCode) {
		this.sexCode = sexCode;
	}
	
	public Date getDob() {
		return dob;
	}
	
	public void setDob(Date dob) {
		this.dob = dob;
	}

	public boolean isDobEstimated() {
		return dobEstimated;
	}

	public void setDobEstimated(boolean dobEstimated) {
		this.dobEstimated = dobEstimated;
	}

	public String getCellule() {
		return cellule;
	}

	public void setCellule(String cellule) {
		this.cellule = cellule;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
	
	public MaritalCode getMaritalStatusCode() {
		return maritalStatusCode;
	}

	public void setMaritalStatusCode(MaritalCode maritalStatusCode) {
		this.maritalStatusCode = maritalStatusCode;
	}

	public HIVStatusPartCode getHIVStatusPartCode() {
		return hivStatusPartCode;
	}

	public void setHIVStatusPartCode(HIVStatusPartCode hivStatusPartCode) {
		this.hivStatusPartCode = hivStatusPartCode;
	}

	public ModeCode getModeCode() {
		return modeCode;
	}

	public void setModeCode(ModeCode modeCode) {
		this.modeCode = modeCode;
	}

	public String getModeAdmissionOther() {
		return modeAdmissionOther;
	}

	public void setModeAdmissionOther(String modeAdmissionOther) {
		this.modeAdmissionOther = modeAdmissionOther;
	}

	public TransferCode getTransferCode() {
		return transferCode;
	}

	public void setTransferCode(TransferCode transferCode) {
		this.transferCode = transferCode;
	}

	public Date getARVStartDate() {
		return arvStartDate;
	}

	public void setARVStartDate(Date arvStartDate) {
		this.arvStartDate = arvStartDate;
	}

	public StatusCode getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(StatusCode statusCode) {
		this.statusCode = statusCode;
	}

	public Date getExitDate() {
		return exitDate;
	}

	public void setExitDate(Date exitDate) {
		this.exitDate = exitDate;
	}

	public ExitCode getExitCode() {
		return exitCode;
	}

	public void setExitCode(ExitCode exitCode) {
		this.exitCode = exitCode;
	}

	public String getExitReasonOther() {
		return exitReasonOther;
	}

	public void setExitReasonOther(String exitReasonOther) {
		this.exitReasonOther = exitReasonOther;
	}
}
