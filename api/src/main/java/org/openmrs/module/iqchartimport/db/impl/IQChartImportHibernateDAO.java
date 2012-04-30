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

package org.openmrs.module.iqchartimport.db.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openmrs.module.iqchartimport.db.IQChartImportDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Hibernate implementation of the module data object access interface
 */
@Repository
public class IQChartImportHibernateDAO implements IQChartImportDAO {
	
	protected static final Log log = LogFactory.getLog(IQChartImportHibernateDAO.class);
	
	/**
	 * Hibernate session factory
	 */
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * @see IQChartImportDAO#getDrugIdByConceptAndDosage(int, Double)
	 */
	@Override
	public Integer getDrugIdByConceptAndDosage(int conceptId, Double dosage) {
		Session session = sessionFactory.getCurrentSession();
		String dose = (dosage != null) ? dosage.toString() : "NULL"; 
		String sql = "SELECT drug_id FROM drug WHERE concept_id = " + conceptId + " AND dose_strength = " + dose;
		return (Integer)session.createSQLQuery(sql).uniqueResult();
	}
}
