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

package org.openmrs.module.iqchartimport.iq.model;

import java.util.Date;

import org.openmrs.module.iqchartimport.iq.code.ChangeCode;

/**
 * Patient drug regimen
 */
public class Regimen {

	private String regimen;
	private Date startDate;
	private Date endDate;
	private ChangeCode changeCode;
	private String otherDetails;
	
	/**
	 * @param regimen
	 * @param startDate
	 * @param endDate
	 * @param changeCode
	 * @param otherDetails
	 */
	public Regimen(String regimen, Date startDate, Date endDate, ChangeCode changeCode, String otherDetails) {
		this.regimen = regimen;
		this.startDate = startDate;
		this.endDate = endDate;
		this.changeCode = changeCode;
		this.otherDetails = otherDetails;
	}

	public String getRegimen() {
		return regimen;
	}

	public void setRegimen(String regimen) {
		this.regimen = regimen;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public ChangeCode getChangeCode() {
		return changeCode;
	}

	public void setChangeCode(ChangeCode changeCode) {
		this.changeCode = changeCode;
	}
	
	public String getOtherDetails() {
		return otherDetails;
	}

	public void setOtherDetails(String otherDetails) {
		this.otherDetails = otherDetails;
	}
}
