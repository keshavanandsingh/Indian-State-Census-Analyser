package com.indianstatecensusanalyser;

public class CSVException extends Exception {
	enum ExceptionType {
		INCORRECT_STATE;
	}

	ExceptionType type;

	public CSVException(String message, ExceptionType type) {
		super(message);
		this.type = type;
	}
}
