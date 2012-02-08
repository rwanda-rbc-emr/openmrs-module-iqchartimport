package org.openmrs.module.iqchartimport.obs;

import java.util.Date;

public class IQWeightObs extends IQObs {
	
	private short weight;
	
	public IQWeightObs(Date date, short weight) {
		super(date);
		this.weight = weight;
	}

	public short getWeight() {
		return weight;
	}
}
