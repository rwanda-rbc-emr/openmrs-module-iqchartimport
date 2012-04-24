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

import java.util.HashMap;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.EncounterType;
import org.openmrs.Location;
import org.openmrs.OrderType;
import org.openmrs.api.context.Context;

/**
 * Caching layer for loaded OpenMRS entities
 */
public class EntityCache {
	
	private Map<Integer, Location> locations = new HashMap<Integer, Location>();
	private Map<String, EncounterType> encounterTypes = new HashMap<String, EncounterType>();
	private Map<Object, Concept> concepts = new HashMap<Object, Concept>();
	private Map<Integer, OrderType> orderTypes = new HashMap<Integer, OrderType>();
	
	/**
	 * Gets a location
	 * @param identifier the identifier
	 * @return the location
	 */
	public Location getLocation(Integer identifier) {
		if (locations.containsKey(identifier))
			return locations.get(identifier);
		
		Location location = Context.getLocationService().getLocation(identifier);
		locations.put(identifier, location);
		return location;
	}
	
	/**
	 * Gets an encounter type
	 * @param identifier the identifier
	 * @return the encounter type
	 */
	public EncounterType getEncounterType(String identifier) {
		if (encounterTypes.containsKey(identifier))
			return encounterTypes.get(identifier);
		
		EncounterType encounterType = MappingUtils.getEncounterType(identifier);
		encounterTypes.put(identifier, encounterType);
		return encounterType;
	}

	/**
	 * Gets a concept
	 * @param identifier the identifier
	 * @return the concept
	 */
	public Concept getConcept(Object identifier) {
		if (concepts.containsKey(identifier))
			return concepts.get(identifier);
		
		Concept concept = MappingUtils.getConcept(identifier);
		concepts.put(identifier, concept);
		return concept;
	}
	
	/**
	 * Gets an order type
	 * @param identifier the identifier
	 * @return the order type
	 */
	public OrderType getOrderType(Integer identifier) {
		if (orderTypes.containsKey(identifier))
			return orderTypes.get(identifier);
		
		OrderType orderType = Context.getOrderService().getOrderType(identifier);
		orderTypes.put(identifier, orderType);
		return orderType;
	}
}
