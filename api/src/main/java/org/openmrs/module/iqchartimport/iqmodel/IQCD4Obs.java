package org.openmrs.module.iqchartimport.iqmodel;

import java.util.Date;

public class IQCD4Obs extends BaseIQObs {

	private short cd4Count;
	private String testType;
	
	public IQCD4Obs(Date date, short cd4Count) {
		super(date);
		this.cd4Count = cd4Count;
	}
	
	public short getCd4Count() {
		return cd4Count;
	}
	
	public String getTestType() {
		return testType;
	}
	
	public void setTestType(String testType) {
		this.testType = testType;
	}
}
