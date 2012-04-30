package org.openmrs.module.iqchartimport;

/**
 * Represents a component of an IQChart ARV regimen, e.g. D4T30, EFV
 */
public class RegimenComponent implements Comparable<RegimenComponent> {
	private String name;
	private String drug;
	private Double dose;
	
	/**
	 * Parses a component from the given string
	 * @param name the string
	 * @return the component
	 */
	public static RegimenComponent parse(String name) {
		RegimenComponent component = new RegimenComponent();
		component.name = name;
		
		int c = name.length();
		for (; c > 0; --c) {
			if (Character.isLetter(name.charAt(c - 1)))
				break;
		}
		component.drug = name.substring(0, c);
		String strDose = name.substring(c).trim();
		component.dose = strDose.length() > 0 ? Double.parseDouble(strDose) : null;
		return component;
	}
	
	/**
	 * Gets the name, e.g. D4T 30
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the drug abbreviation part, e.g. D4T
	 * @return the drug
	 */
	public String getDrug() {
		return drug;
	}
	
	/**
	 * Gets the dose part, e.g. 30
	 * @return the dose
	 */
	public Double getDose() {
		return dose;
	}
	
	/**
	 * Compares by name
	 */
	@Override
	public int compareTo(RegimenComponent component) {
		return name.compareTo(component.name);
	}

	/**
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		RegimenComponent comp = (RegimenComponent)obj;
		return comp != null && drug.equals(comp.drug) && dose == comp.dose;
	}

	/**
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return drug.hashCode() + ((dose != null) ? dose.hashCode() : 0);
	}
}