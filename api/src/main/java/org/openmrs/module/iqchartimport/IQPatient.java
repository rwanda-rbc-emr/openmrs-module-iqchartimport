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

import java.util.Date;

/**
 * A patient in an IQChart database
 */
public class IQPatient {
	
	public static final int SEXCODE_MALE = 0;
	public static final int SEXCODE_FEMALE = 1;
	
	public static final int STATUSCODE_ACTIVE = 0;
	public static final int STATUSCODE_EXITED = 1;
	
	public static final int EXITCODE_OTHER = 0;
	public static final int EXITCODE_TRANSFERRED = 1;
	public static final int EXITCODE_DECEASED = 2;
	public static final int EXITCODE_LOST = 3;
	
	private int tracnetID;
	private String hospitalID;
	private String firstName;
	private String lastName;
	private Date dob;
	private Boolean dobEstimated;
	private Byte sexCode;
	private Byte statusCode;
	private Date enrollDate;
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
	
	public String getHospitalID() {
		return hospitalID;
	}

	public void setHospitalID(String hospitalID) {
		this.hospitalID = hospitalID;
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

	public Byte getSexCode() {
		return sexCode;
	}

	public void setSexCode(Byte sexCode) {
		this.sexCode = sexCode;
	}

	public Byte getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Byte statusCode) {
		this.statusCode = statusCode;
	}

	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
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
