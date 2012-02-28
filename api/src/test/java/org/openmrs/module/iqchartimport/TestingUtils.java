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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Utility methods for testing
 */
public class TestingUtils {
	
	/**
	 * Copies a resource in a JAR to a temporary file
	 * @param resource the resource path
	 * @param suffix the suffix
	 * @return the temporary file
	 * @throws Exception
	 */
	public static File copyResourceToTempFile(String resource, String suffix) throws Exception {
		InputStream in;
		File resFile = new File(resource);
		if (resFile.exists())
			in = new FileInputStream(resFile);
		else
			in = TestingUtils.class.getClassLoader().getResourceAsStream(resource);
		
		File tempFile = File.createTempFile(resFile.getName(), suffix);
		OutputStream out = new FileOutputStream(tempFile);
		
		// Copy byte by byte
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0)
			out.write(buf, 0, len);
		
		in.close();
		out.close();
		return tempFile;
	}
}
