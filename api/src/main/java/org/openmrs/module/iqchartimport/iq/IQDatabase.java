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

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An IQChart MDB database file
 */
public class IQDatabase {
	
	protected static final Log log = LogFactory.getLog(IQDatabase.class);
	
	private String name;
	private String path;
	
	/**
	 * @param name
	 * @param path
	 */
	public IQDatabase(String name, String path) {
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
	 * Loads database as string attribute in session
	 * @param session the session
	 * @param key the attribute key
	 * @return the upload object or null
	 */
	public static IQDatabase load(HttpSession session, String key) {
		String val = (String)session.getAttribute(key);
		if (val != null) {
			String[] tokens = val.split("\\|");
			if (tokens.length == 2)
				return new IQDatabase(tokens[0], tokens[1]);
		}
		return null;
	}
	
	/**
	 * Saves datebase as string attribute in session
	 * @param session the session
	 * @param key the attribute key
	 */
	public void save(HttpSession session, String key) {
		session.setAttribute(key, name + "|" + path);
	}
}
