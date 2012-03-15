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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PatientIdentifier;
import org.openmrs.PatientIdentifierType;
import org.openmrs.PatientProgram;
import org.openmrs.PersonAddress;
import org.openmrs.PersonName;
import org.openmrs.Program;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.iqchartimport.iq.IQChartSession;
import org.openmrs.module.iqchartimport.iq.IQPatient;
import org.openmrs.module.iqchartimport.iq.code.ExitCode;
import org.openmrs.module.iqchartimport.iq.code.SexCode;
import org.openmrs.module.iqchartimport.iq.code.StatusCode;
import org.openmrs.module.iqchartimport.iq.obs.BaseIQObs;
import org.openmrs.module.iqchartimport.iq.obs.IQCD4Obs;
import org.openmrs.module.iqchartimport.iq.obs.IQHeightObs;
import org.openmrs.module.iqchartimport.iq.obs.IQWeightObs;

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
	 * @throws IncompleteMappingException if mappings are not configured properly
	 */
	public PatientIdentifierType getTRACnetIDType() {
		int tracnetIDTypeId = Mappings.getInstance().getTracnetIDTypeId();
		if (tracnetIDTypeId > 0) {
			return Context.getPatientService().getPatientIdentifierType(tracnetIDTypeId);	
		}
		else if (tracnetIDTypeId == 0) {
			PatientIdentifierType tracnetIDType = new PatientIdentifierType();
			tracnetIDType.setName(Constants.NEW_TRACNET_ID_TYPE_NAME);
			tracnetIDType.setDescription(Constants.NEW_TRACNET_ID_TYPE_NAME);
			tracnetIDType.setFormat(Constants.NEW_TRACNET_ID_TYPE_FORMAT);
			tracnetIDType.setFormatDescription(Constants.NEW_TRACNET_ID_TYPE_FORMAT_DESC);
			return tracnetIDType;
		}
		else {
			throw new IncompleteMappingException();
		}
	}
	
	/**
	 * Gets the HIV program to use for imported patients
	 * @return the HIV program
	 * @throws IncompleteMappingException if mappings are not configured properly
	 */
	public Program getHIVProgram() {
		int hivProgramId = Mappings.getInstance().getHivProgramId();
		if (hivProgramId > 0) {
			return Context.getProgramWorkflowService().getProgram(hivProgramId);	
		}
		else {
			throw new IncompleteMappingException();
		}
	}
	
	/**
	 * Gets the location for imported encounters
	 * @return the location
	 * @throws IncompleteMappingException if mappings are not configured properly
	 */
	public Location getEncounterLocation() {
		int encounterLocationId = Mappings.getInstance().getEncounterLocationId();
		if (encounterLocationId > 0) {
			return Context.getLocationService().getLocation(encounterLocationId);	
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
	 * Gets the patient's programs
	 * @param tracnetID the patient TRACnet ID
	 * @return the patient programs
	 */
	public List<PatientProgram> getPatientPrograms(int tracnetID) {		
		List<PatientProgram> patientPrograms = new ArrayList<PatientProgram>();
		IQPatient iqPatient = session.getPatient(tracnetID);
		
		if (iqPatient.getEnrollDate() != null) {
			// Get HIV program to use
			Program hivProgram = getHIVProgram();
			
			PatientProgram hivPatientProgram = new PatientProgram();
			hivPatientProgram.setProgram(hivProgram);
			hivPatientProgram.setDateEnrolled(iqPatient.getEnrollDate());
			hivPatientProgram.setDateCompleted(iqPatient.getExitDate());
			patientPrograms.add(hivPatientProgram);
		}
		
		return patientPrograms;
	}
	
	public List<Encounter> getPatientEncounters(Patient patient, int tracnetID) {
		IQPatient iqPatient = session.getPatient(tracnetID);
		Map<Date, Encounter> encounters = new TreeMap<Date, Encounter>();
		
		makePatientInitialEncounter(patient, tracnetID, encounters);
		makePatientObsEncounters(patient, tracnetID, encounters);
		
		if (iqPatient.getExitDate() != null)
			makePatientExitEncounter(patient, tracnetID, encounters);
		
		// Create sorted list
		List<Encounter> sorted = new ArrayList<Encounter>();
		for (Date date : encounters.keySet())
			sorted.add(encounters.get(date));
				
		return sorted;
	}
	
	/**
	 * Makes the initial encounter for a patient
	 * @param patient the patient
	 * @param tracnetID the patient TRACnet ID
	 * @param encounters the existing encounters
	 */
	public void makePatientInitialEncounter(Patient patient, int tracnetID, Map<Date, Encounter> encounters) {	
		IQPatient iqPatient = session.getPatient(tracnetID);
		
		if (iqPatient.getEnrollDate() != null) {
			encounterForDate(patient, iqPatient.getEnrollDate(), encounters);
		}
	}
	
	/**
	 * Makes the obs encounters for a patient
	 * @param patient the patient
	 * @param tracnetID the patient TRACnet ID
	 * @param encounters the existing encounters
	 */
	public void makePatientObsEncounters(Patient patient, int tracnetID, Map<Date, Encounter> encounters) {
		IQPatient iqPatient = session.getPatient(tracnetID);
		List<BaseIQObs> iqObss = session.getPatientObs(iqPatient);
		
		for (BaseIQObs iqObs : iqObss) {
			Encounter encounter = encounterForDate(patient, iqObs.getDate(), encounters);		
			Concept concept = null;
			Double value = null;
			
			if (iqObs instanceof IQHeightObs) {
				concept = Utils.getConceptFromProperty("concept.height");
				value = (double)((IQHeightObs)iqObs).getHeight();		
			}
			else if (iqObs instanceof IQWeightObs) {
				concept = Utils.getConceptFromProperty("concept.weight");
				value = (double)((IQWeightObs)iqObs).getWeight();		
			}
			else if (iqObs instanceof IQCD4Obs) {
				concept = Utils.getConceptFromProperty("concept.cd4_count");
				value = (double)((IQCD4Obs)iqObs).getCd4Count();		
			}
			
			Obs obs = new Obs();
			obs.setPerson(patient);
			obs.setLocation(getEncounterLocation());
			obs.setObsDatetime(iqObs.getDate());
			obs.setConcept(concept);
			obs.setValueNumeric(value);
			encounter.addObs(obs);
		}
	}
	
	/**
	 * Makes the exit encounter for a patient
	 * @param patient the patient
	 * @param tracnetID the patient TRACnet ID
	 * @param encounters the existing encounters
	 */
	public void makePatientExitEncounter(Patient patient, int tracnetID, Map<Date, Encounter> encounters) {
		IQPatient iqPatient = session.getPatient(tracnetID);
		
		Obs obs = getPatientExitReasonObs(patient, tracnetID);
		if (obs != null) {
			Encounter encounter = encounterForDate(patient, iqPatient.getExitDate(), encounters);
			encounter.addObs(obs);
		}
	}
	
	/**
	 * Gets the exit reason obs for the given patient
	 * @param patient the patient
	 * @param tracnetID the TRACnet ID
	 * @return
	 */
	public Obs getPatientExitReasonObs(Patient patient, int tracnetID) {
		IQPatient iqPatient = session.getPatient(tracnetID);
		
		if (iqPatient.getStatusCode() != null && iqPatient.getStatusCode() == StatusCode.EXITED && iqPatient.getExitCode() != null) {
			// Make exit reason obs
			Concept reasonConcept = Utils.getConceptFromProperty("concept.reasonExitedCare");
			
			// Map exit code to cause concept
			Concept causeConcept = null;	
			if (iqPatient.getExitCode() == ExitCode.DECEASED) {
				causeConcept = Utils.getConceptFromProperty("concept.patientDied");
			}
			else if (iqPatient.getExitCode() == ExitCode.TRANSFERRED) {
				// TODO load concepts from mappings?
				causeConcept = Utils.getConceptByName("PATIENT TRANSFERRED OUT");
			}
			else if (iqPatient.getExitCode() == ExitCode.LOST) {
				causeConcept = Utils.getConceptByName("PATIENT DEFAULTED");
			}
			else if (iqPatient.getExitCode() == ExitCode.STOPPED_BY_PATIENT) {
				causeConcept = Utils.getConceptByName("PATIENT REFUSED");
			}
			// TODO mappings for remaining codes
			
			if (causeConcept != null) {
				Obs obsExit = new Obs();
				obsExit.setPerson(patient);
				obsExit.setConcept(reasonConcept);
				obsExit.setLocation(getEncounterLocation());
				obsExit.setObsDatetime(iqPatient.getExitDate());
				obsExit.setValueCoded(causeConcept);
				return obsExit;
			}
		}
		
		return null;
	}
	
	/**
	 * Converts an IQChart patient to an OpenMRS patient
	 * @param iqPatient the IQChart patient
	 * @return the OpenMRS patient
	 * @throws IncompleteMappingException if mapping is not configured properly
	 */
	protected Patient convertPatient(IQPatient iqPatient) {
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
		
		// Create address object using AddressHierarchRwanda mappings
		String province = Mappings.getInstance().getAddressProvince();
		String country = Context.getAdministrationService().getGlobalProperty(Constants.PROP_ADDRESS_COUNTRY);
		PersonAddress address = new PersonAddress();
		address.setNeighborhoodCell(iqPatient.getCellule());
		address.setCityVillage(iqPatient.getSector());
		address.setCountyDistrict(iqPatient.getDistrict());
		address.setStateProvince(province);
		address.setCountry(country);
		patient.addAddress(address);
		
		// Set patient gender
		if (iqPatient.getSexCode() != null) {
			if (iqPatient.getSexCode() == SexCode.MALE)
				patient.setGender("M");
			else if (iqPatient.getSexCode() == SexCode.FEMALE)
				patient.setGender("F");
		}
		
		// Set patient birth date
		patient.setBirthdate(iqPatient.getDob());
		patient.setBirthdateEstimated(iqPatient.isDobEstimated());
		
		// Set living/dead
		if (iqPatient.getExitCode() != null && iqPatient.getExitCode() == ExitCode.DECEASED)
			patient.setDead(true);
		
		return patient;
	}
	
	/**
	 * Gets the encounter type to use for imported obs
	 * @param patient the patient
	 * @param date the encounter date
	 * @param initial whether this is an initial encounter
	 * @return the encounter type
	 * @throws IncompleteMappingException if mappings are not configured properly
	 */
	protected EncounterType getEncounterType(Patient patient, Date date, boolean isInitial) {
		// Calc patient age at time of encounter
		int age = patient.getAge(date);
		boolean isPediatric = (age < Constants.ADULT_START_AGE);
		
		if (isInitial && isPediatric)
			return Context.getEncounterService().getEncounterType("PEDSINITIAL");
		else if (isInitial && !isPediatric)
			return Context.getEncounterService().getEncounterType("ADULTINITIAL");
		else if (!isInitial && isPediatric)
			return Context.getEncounterService().getEncounterType("PEDSRETURN");
		else
			return Context.getEncounterService().getEncounterType("ADULTRETURN");
	}
	
	/**
	 * Gets an encounter for the given day - if one exists it is returned
	 * @param patient the patient
	 * @param date the day
	 * @param encounters the existing encounters
	 * @return the encounter
	 */
	protected Encounter encounterForDate(Patient patient, Date date, Map<Date, Encounter> encounters) {
		// Look for existing encounter on that date
		if (encounters.containsKey(date)) 
			return encounters.get(date);
		
		// If no existing, then this will be an initial encounter
		boolean isInitial = encounters.isEmpty();
		
		// Create new one
		Encounter encounter = new Encounter();
		encounter.setEncounterType(getEncounterType(patient, date, isInitial));
		encounter.setLocation(getEncounterLocation());
		encounter.setEncounterDatetime(date);
		encounter.setPatient(patient);
		
		// Store in encounter map and return
		encounters.put(date, encounter);
		return encounter;
	}
}