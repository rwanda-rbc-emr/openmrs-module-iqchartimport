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

import org.openmrs.module.iqchartimport.iq.IQDatabase;

/**
 * Simple engine class to run a single import task at a time
 */
public class ImportEngine {

	private static ImportTask task;
	private static Thread thread;
	
	/**
	 * Creates and starts a new import task
	 * @param database the database to import
	 * @return true if successful, else false if a task is already running
	 */
	public static synchronized boolean startImport(IQDatabase database) {
		if (task != null && !task.isComplete())
			return false;
		
		task = new ImportTask(database);
		thread = new Thread(task);
		thread.start();
		return true;
	}
	
	/**
	 * Gets the currently running or completed task if there is one
	 * @return the task or null
	 */
	public static synchronized ImportTask getCurrentTask() {
		return task;
	}
}
