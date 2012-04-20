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

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Drug;
import org.openmrs.module.iqchartimport.db.IQChartImportDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Hibernate implementation of the module data object access interface
 */
@Repository
public class IQChartImportHibernateDAO implements IQChartImportDAO {
	
	/**
	 * Hibernate session factory
	 */
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * @see IQChartImportDAO#getDrugsFromConcepts(String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Drug> getDrugsFromConcepts(String conceptIds) {
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(Drug.class).add(
				Restrictions.sqlRestriction("concept_id IN (" + conceptIds + ")")
		).list();
	}
}
