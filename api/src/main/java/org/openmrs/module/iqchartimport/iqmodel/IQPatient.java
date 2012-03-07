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

package org.openmrs.module.iqchartimport.iqmodel;

import java.util.Date;

/**
 * A patient in an IQChart database
 */
public class IQPatient {
	
	public static final byte SEX_MALE = 0;
	public static final byte SEX_FEMALE = 1;
	
	public static final byte MARITALSTATUS_SINGLE = 0;
	public static final byte MARITALSTATUS_MARRIED = 1;
	public static final byte MARITALSTATUS_WIDOWED = 2;
	public static final byte MARITALSTATUS_DIVORCED = 3;
	public static final byte MARITALSTATUS_LIVINGWITHPARTNER = 4;
	public static final byte MARITALSTATUS_OTHER = 5;
	
	public static final byte HIVSTATUS_NEGATIVE = 0;
	public static final byte HIVSTATUS_POSITIVE = 1;
	public static final byte HIVSTATUS_UNKNOWN = 2;
	public static final byte HIVSTATUS_UNTESTED = 3;
	
	public static final byte NEWTRANSFER_NO = 0;
	public static final byte NEWTRANSFER_YES = 1;
	
	public static final byte STATUS_EXITED = 0;
	public static final byte STATUS_ACTIVE = 1;
	
	public static final byte EXIT_OTHER = 0;
	public static final byte EXIT_TRANSFERRED = 1;
	public static final byte EXIT_DECEASED = 2;
	public static final byte EXIT_LOST = 3;
	
	private int tracnetID;
	private String firstName;
	private String lastName;
	private String hospitalID;
	private Date enrollDate;
	private Byte sexCode;
	private Date dob;
	private Boolean dobEstimated;
	private String cellule;
	private String sector;
	private String district;
	private Byte maritalStatusCode;
	private Byte hivStatusPartCode;
	private Byte modeCode;
	private String modeAdmissionOther;
	private Byte newTransfer;
	private Date arvStartDate;
	private Byte statusCode;
	private Date exitDate;
	private Byte exitCode;
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
	
	public Byte getSexCode() {
		return sexCode;
	}

	public void setSexCode(Byte sexCode) {
		this.sexCode = sexCode;
	}
	
	public Date getDob() {
		return dob;
	}
	
	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Boolean getDobEstimated() {
		return dobEstimated;
	}

	public void setDobEstimated(Boolean dobEstimated) {
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
	
	public Byte getMaritalStatusCode() {
		return maritalStatusCode;
	}

	public void setMaritalStatusCode(Byte maritalStatusCode) {
		this.maritalStatusCode = maritalStatusCode;
	}

	public Byte getHIVStatusPartCode() {
		return hivStatusPartCode;
	}

	public void setHIVStatusPartCode(Byte hivStatusPartCode) {
		this.hivStatusPartCode = hivStatusPartCode;
	}

	public Byte getModeCode() {
		return modeCode;
	}

	public void setModeCode(Byte modeCode) {
		this.modeCode = modeCode;
	}

	public String getModeAdmissionOther() {
		return modeAdmissionOther;
	}

	public void setModeAdmissionOther(String modeAdmissionOther) {
		this.modeAdmissionOther = modeAdmissionOther;
	}

	public Byte getNewTransfer() {
		return newTransfer;
	}

	public void setNewTransfer(Byte newTransfer) {
		this.newTransfer = newTransfer;
	}

	public Date getARVStartDate() {
		return arvStartDate;
	}

	public void setARVStartDate(Date arvStartDate) {
		this.arvStartDate = arvStartDate;
	}

	public Byte getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Byte statusCode) {
		this.statusCode = statusCode;
	}

	public Date getExitDate() {
		return exitDate;
	}

	public void setExitDate(Date exitDate) {
		this.exitDate = exitDate;
	}

	public Byte getExitCode() {
		return exitCode;
	}

	public void setExitCode(Byte exitCode) {
		this.exitCode = exitCode;
	}

	public String getExitReasonOther() {
		return exitReasonOther;
	}

	public void setExitReasonOther(String exitReasonOther) {
		this.exitReasonOther = exitReasonOther;
	}
}
