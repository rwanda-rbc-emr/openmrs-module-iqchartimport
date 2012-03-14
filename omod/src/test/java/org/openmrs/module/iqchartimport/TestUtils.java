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
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;

/**
 * Utility methods for testing
 */
public class TestUtils {
	
	/**
	 * Creates a date object
	 * @param year the year
	 * @param month the month (1..12)
	 * @param day the date (1..31)
	 * @return
	 */
	public static Date date(int year, int month, int day) {
		return new GregorianCalendar(year, month - 1, day).getTime();
	}
	
	/**
	 * Creates and saves a global property
	 * @param name the property name
	 * @param value the property value
	 * @return the property object
	 */
	public static GlobalProperty setGlobalProperty(String name, Object value) {
		GlobalProperty property = Context.getAdministrationService().getGlobalPropertyObject(name);
		String val = value != null ? value.toString() : null;
				
		if (property == null)
			property = new GlobalProperty(name, val);
		else
			property.setPropertyValue(val);
		
		return Context.getAdministrationService().saveGlobalProperty(property);
	}
	
	/**
	 * Extracts a resource to a temporary file for testing
	 * @param path the database resource path
	 * @return the temporary file
	 * @throws Exception
	 */
	public static File copyResource(String path) throws IOException {
		InputStream in = TestUtils.class.getResourceAsStream(path);
		if (in == null)
			throw new IOException("Unable to open resource: " + path);

		File tempFile = File.createTempFile("temp", "." + getExtension(path));
		
		copyStream(in, new FileOutputStream(tempFile));
		
		in.close();
		return tempFile;
	}
	
	/**
	 * Extracts an item from a zip file into a temporary file
	 * @param zipFile the zip file
	 * @param entryName the name of the item to extract
	 * @return the item temp file
	 * @throws ZipException
	 * @throws IOException
	 */
	public static File extractZipEntry(File zipFile, String entryName) throws ZipException, IOException {
		ZipFile zip = new ZipFile(zipFile);
		ZipEntry entry = zip.getEntry(entryName);
		InputStream in = zip.getInputStream(entry);
		
		File tempFile = File.createTempFile("temp", "." + getExtension(entryName));
		
		copyStream(in, new FileOutputStream(tempFile));
		
		in.close();
		return tempFile;
	}
	
	/**
	 * Gets the extension of a file path
	 * @param path the file path
	 * @return the extension
	 */
	public static String getExtension(String path) {
		int index = path.lastIndexOf('.');
		return index >= 0 ? path.substring(index + 1) : null;
	}
	
	/**
	 * Copies all data from one stream to another
	 * @param in the input stream
	 * @param out the output stream
	 * @throws IOException
	 */
	private static void copyStream(InputStream in, OutputStream out) throws IOException {
		byte[] buf = new byte[256];
		int len;
		while ((len = in.read(buf)) >= 0)
			out.write(buf, 0, len);
		out.close();
	}
}
