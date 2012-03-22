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

/**
 * A patient's pregnancy
 */
public class Pregnancy {

	private Date dateStart;
	private Date estDelivery;
	private Date dateEnd;
	
	/**
	 * Constructs a pregnancy
	 * @param dateStart the start date
	 * @param estDelivery the estimated delivery date
	 * @param dateEnd the actual end date
	 */
	public Pregnancy(Date dateStart, Date estDelivery, Date dateEnd) {
		this.dateStart = dateStart;
		this.estDelivery = estDelivery;
		this.dateEnd = dateEnd;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getEstDelivery() {
		return estDelivery;
	}

	public void setEstDelivery(Date estDelivery) {
		this.estDelivery = estDelivery;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
}
