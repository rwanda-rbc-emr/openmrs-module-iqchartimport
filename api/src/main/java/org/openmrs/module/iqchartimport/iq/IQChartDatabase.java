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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An IQChart MDB database file - singleton
 */
public class IQChartDatabase {
	
	protected static final Log log = LogFactory.getLog(IQChartDatabase.class);
	
	protected static IQChartDatabase instance;
	
	private String name;
	private String path;
	
	/**
	 * @param name
	 * @param path
	 */
	protected IQChartDatabase(String name, String path) {
		this.name = name;
		this.path = path;
	}

	/**
	 * Gets the database name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the path of the database file
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Gets the singleton instance
	 * @return
	 */
	public static IQChartDatabase getInstance() {
		return instance;
	}
	
	/**
	 * Creates the singleton instance
	 * @param name
	 * @param path
	 */
	public static void createInstance(String name, String path) {
		instance = new IQChartDatabase(name, path);
	}
	
	/**
	 * Clears the singleton instance
	 */
	public static void clearInstance() {
		instance = null;
	}
}
