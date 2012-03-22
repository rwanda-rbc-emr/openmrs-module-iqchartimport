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

import org.openmrs.module.iqchartimport.iq.code.HospReasonCode;

/**
 * Patient hospitalization
 */
public class Hospitalization {

	private String facility;
	private Date dateAdm;
	private Date dateDisch;
	private HospReasonCode reason;
	
	/**
	 * @param facility
	 * @param dateAdm
	 * @param dateDisch
	 * @param reason
	 */
	public Hospitalization(String facility, Date dateAdm, Date dateDisch, HospReasonCode reason) {
		this.facility = facility;
		this.dateAdm = dateAdm;
		this.dateDisch = dateDisch;
		this.reason = reason;
	}

	public String getFacility() {
		return facility;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}

	public Date getDateAdm() {
		return dateAdm;
	}

	public void setDateAdm(Date dateAdm) {
		this.dateAdm = dateAdm;
	}

	public Date getDateDisch() {
		return dateDisch;
	}

	public void setDateDisch(Date dateDisch) {
		this.dateDisch = dateDisch;
	}

	public HospReasonCode getReason() {
		return reason;
	}

	public void setReason(HospReasonCode reason) {
		this.reason = reason;
	}
}
