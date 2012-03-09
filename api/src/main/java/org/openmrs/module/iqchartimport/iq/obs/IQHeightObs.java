package org.openmrs.module.iqchartimport.iq.obs;

import java.util.Date;

public class IQHeightObs extends BaseIQObs {

	private short height;
	
	public IQHeightObs(Date date, short height) {
		super(date);
		this.height = height;
	}

	public short getHeight() {
		return height;
	}
}
