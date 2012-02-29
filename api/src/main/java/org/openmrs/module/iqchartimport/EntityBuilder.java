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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PersonAddress;
import org.openmrs.PersonName;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;

/**
 * Builder which creates OpenMRS entities from IQChart objects equivalents
 */
public class EntityBuilder {

	protected static final Log log = LogFactory.getLog(EntityBuilder.class);
	
	private IQChartSession session;
	
	/**
	 * Creates a new entity builder
	 * @param session the IQChart session to load data from
	 */
	public EntityBuilder(IQChartSession session) {
		this.session = session;
	}
	
	/**
	 * Gets the TRACnetID identifier type to use for imported patients. Depending on module
	 * options this will load existing identifier type or create a new one.
	 * @return the identifier type
	 * @throws IncompleteMappingException if mapping is not configured properly
	 */
	public PatientIdentifierType getTRACnetIDType() {
		int tracnetIDTypeId = Mapping.getInstance().getTracnetIDTypeId();
		if (tracnetIDTypeId > 0) {
			return Context.getPatientService().getPatientIdentifierType(tracnetIDTypeId);	
		}
		else if (tracnetIDTypeId == 0) {
			PatientIdentifierType tracnetIDType = new PatientIdentifierType();
			tracnetIDType.setName(Constants.TRACNET_ID_TYPE_NAME); // TODO get TRACnet ID specification
			//tracnetIDType.setFormat("%d");
			return tracnetIDType;
		}
		else {
			throw new IncompleteMappingException();
		}
	}
	
	/**
	 * Gets a patient by TRACnet ID
	 * @param tracnetID the TRACnet ID
	 * @return the patient
	 */
	public Patient getPatient(int tracnetID) {
		IQPatient iqPatient = session.getPatient(tracnetID);
		return iqPatient != null ? convertPatient(iqPatient) : null;
	}
	
	/**
	 * Gets OpenMRS patient objects for each patient in IQChart 
	 * @return the patients
	 * @throws APIException if module is not configured properly
	 */
	public List<Patient> getPatients() {
		List<Patient> patients = new ArrayList<Patient>();
		
		for (IQPatient iqPatient : session.getPatients()) {
			patients.add(convertPatient(iqPatient));
		}
		
		return patients;
	}
	
	/**
	 * Converts an IQChart patient to an OpenMRS patient
	 * @param iqPatient the IQChart patient
	 * @return the OpenMRS patient
	 * @throws IncompleteMappingException if mapping is not configured properly
	 */
	private Patient convertPatient(IQPatient iqPatient) {
		// Get TRACnet ID identifier type
		PatientIdentifierType tracnetIDType = getTRACnetIDType();
		
		Patient patient = new Patient();
		
		// Create TRACnet identifier
		PatientIdentifier tracnetID = new PatientIdentifier();
		tracnetID.setIdentifier("" + iqPatient.getTracnetID());
		tracnetID.setIdentifierType(tracnetIDType);
		tracnetID.setPreferred(true);
		patient.addIdentifier(tracnetID);
		
		// Create name object
		PersonName name = new PersonName();
		name.setGivenName(iqPatient.getFirstName());
		name.setFamilyName(iqPatient.getLastName());
		patient.addName(name);
		
		// Create address object
		PersonAddress address = new PersonAddress();
		address.setAddress1(iqPatient.getCellule()); // TODO mapping
		address.setAddress2(iqPatient.getSector());
		address.setCountyDistrict(iqPatient.getDistrict());
		patient.addAddress(address);
		
		// Set patient gender
		if (iqPatient.getSexCode() != null) {
			if (iqPatient.getSexCode() == IQPatient.SEXCODE_MALE)
				patient.setGender("M");
			else if (iqPatient.getSexCode() == IQPatient.SEXCODE_FEMALE)
				patient.setGender("F");
		}
		
		// Set patient birth date
		patient.setBirthdate(iqPatient.getDob());
		patient.setBirthdateEstimated(iqPatient.getDobEstimated());
		
		if (iqPatient.getExitCode() != null && iqPatient.getExitCode() == IQPatient.EXITCODE_DECEASED)
			patient.setDead(true);
		
		return patient;
	}
}