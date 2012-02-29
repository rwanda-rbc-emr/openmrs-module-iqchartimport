package org.openmrs.module.iqchartimport.iqmodel;

import java.util.Date;

public class IQWeightObs extends BaseIQObs {
	
	private short weight;
	
	public IQWeightObs(Date date, short weight) {
		super(date);
		this.weight = weight;
	}

	public short getWeight() {
		return weight;
	}
}
