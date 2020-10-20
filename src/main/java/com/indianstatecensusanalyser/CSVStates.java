package com.indianstatecensusanalyser;

import com.opencsv.bean.CsvBindByName;

public class CSVStates {

	@CsvBindByName(column = "State Name")
	public String stateName;

	@CsvBindByName(column = "State Code")
	public String stateCode;

	public CSVStates() {
	}

	public CSVStates(String stateName, String stateCode) {
		this.stateName = stateName;
		this.stateCode = stateCode;
	}

	@Override
	public String toString() {
		return "CSVStates [stateName=" + stateName + ", stateCode=" + stateCode + "]";
	}

}
