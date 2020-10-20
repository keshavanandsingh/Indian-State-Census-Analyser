package com.indianstatecensusanalyser;

import com.opencsv.bean.CsvBindByName;

public class StateCensusCSV {
	@CsvBindByName(column = "State")
	public String state;

	@CsvBindByName(column = "Population")
	public int population;

	@CsvBindByName(column = "Area In Square Km")
	public int areaInSqKm;

	@CsvBindByName(column = "Density Per Square Km")
	public int densityPerSqKm;

	public StateCensusCSV() {
	}

	public StateCensusCSV(String state, int population, int areaInSqKm, int densityPerSqKm) {
		this.state = state;
		this.population = population;
		this.areaInSqKm = areaInSqKm;
		this.densityPerSqKm = densityPerSqKm;
	}

	@Override
	public String toString() {
		return "StateCensusCSV [state=" + state + ", population=" + population + ", areaInSqKm=" + areaInSqKm
				+ ", densityPerSqKm=" + densityPerSqKm + "]";
	}
}
