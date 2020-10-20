package com.indianstatecensusanalyser;

import static org.junit.Assert.assertEquals;

import java.nio.file.Paths;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.gson.Gson;
import com.indianstatecensusanalyser.constants.Constant;

public class SateCensusAnalyzerTest {

	@Test
	public void givenStateCensusCSVFile_ShouldReturnNumberOfRecords() {
		try {
			StateCensusAnalyzer censusAnalyzer = new StateCensusAnalyzer(Paths.get(Constant.STATE_CENSUS_CSV_FILE_PATH));
			int noOfEntries = censusAnalyzer.readStateCensusCSVData();
			assertEquals(5, noOfEntries);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void givenStateCensusCSVFile_WhenPathIncorrect_ShouldThrowException() {
		try {
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(StateCensusAnalyzerException.class);
			StateCensusAnalyzer censusAnalyzer = new StateCensusAnalyzer(Paths.get(Constant.WRONG_STATE_CENSUS_CSV_FILE_PATH));
			censusAnalyzer.readStateCensusCSVData();
		} catch (StateCensusAnalyzerException e) {
			assertEquals(StateCensusAnalyzerException.ExceptionType.INCORRECT_PATH, e.type);
		}
	}

	@Test
	public void givenStateCensusCSVFile_WhenStateIncorrect_ShouldThrowException() {
		try {
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(StateCensusAnalyzerException.class);
			StateCensusAnalyzer censusAnalyzer = new StateCensusAnalyzer(Paths.get(Constant.STATE_CENSUS_CSV_FILE_PATH));
			censusAnalyzer.readStateCensusCSVData();
		} catch (StateCensusAnalyzerException e) {
			assertEquals(StateCensusAnalyzerException.ExceptionType.INCORRECT_STATE, e.type);
		}
	}

	@Test
	public void givenStateCensusCSVFile_WhenIncorrectDelimeter_ShouldThrowException() {
		try {
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(StateCensusAnalyzerException.class);
			StateCensusAnalyzer censusAnalyzer = new StateCensusAnalyzer(
					Paths.get(Constant.STATE_CENSUS_CSV_FILE_WRONG_DELIMITER_PATH));
			censusAnalyzer.readStateCensusCSVData();
		} catch (StateCensusAnalyzerException e) {
			assertEquals(StateCensusAnalyzerException.ExceptionType.INCORRECT_DELIMITER, e.type);
		}
	}

	@Test
	public void givenStateCensusCSVFile_WhenIncorrectCSVHeader_ShouldThrowException() {
		try {
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(StateCensusAnalyzerException.class);
			StateCensusAnalyzer censusAnalyzer = new StateCensusAnalyzer(
					Paths.get(Constant.STATE_CENSUS_CSV_FILE_WRONG_HEADER_PATH));
			censusAnalyzer.readStateCensusCSVData();
		} catch (StateCensusAnalyzerException e) {
			assertEquals(StateCensusAnalyzerException.ExceptionType.INCORRECT_CSV_HEADER, e.type);
		}
	}

	@Test
	public void givenStateCensusCSVData_WhenSortedByState_ShouldReturnSortedResult() {
		try {
			StateCensusAnalyzer censusAnalyzer = new StateCensusAnalyzer(Paths.get(Constant.STATE_CENSUS_CSV_FILE_PATH));
			censusAnalyzer.readStateCensusCSVData();
			String stateCensusDataSortedByState = censusAnalyzer.getStateWiseStateCensusSortedData();
			StateCensusCSV[] stateCensusDataArray = new Gson().fromJson(stateCensusDataSortedByState,
					StateCensusCSV[].class);
			assertEquals("Jammu and Kashmir", stateCensusDataArray[0].state);
			assertEquals("Uttarakhand", stateCensusDataArray[4].state);
		} catch (StateCensusAnalyzerException e) {
		}
	}

	@Test
	public void givenStateCensusCSVData_WhenSortedByPopulation_ShouldReturnSortedResult() {
		try {
			StateCensusAnalyzer censusAnalyzer = new StateCensusAnalyzer(Paths.get(Constant.STATE_CENSUS_CSV_FILE_PATH));
			censusAnalyzer.readStateCensusCSVData();
			String stateCensusDataSortedByPopulation = censusAnalyzer
					.getPopulationWiseStateCensusSortedData(Paths.get(Constant.STATE_CENSUS_JSON_FILE_SORTED_BY_POPULATION));
			StateCensusCSV[] stateCensusDataArray = new Gson().fromJson(stateCensusDataSortedByPopulation,
					StateCensusCSV[].class);
			assertEquals("Uttar Pradesh", stateCensusDataArray[0].state);
			assertEquals("Uttarakhand", stateCensusDataArray[4].state);
			assertEquals(5, stateCensusDataArray.length);
		} catch (StateCensusAnalyzerException e) {
		}
	}

	@Test
	public void givenStateCensusCSVData_WhenSortedByDensity_ShouldReturnSortedResult() {
		try {
			StateCensusAnalyzer censusAnalyzer = new StateCensusAnalyzer(Paths.get(Constant.STATE_CENSUS_CSV_FILE_PATH));
			censusAnalyzer.readStateCensusCSVData();
			String stateCensusDataSortedByDensity = censusAnalyzer.getDensityWiseStateCensusSortedData();
			StateCensusCSV[] stateCensusDataArray = new Gson().fromJson(stateCensusDataSortedByDensity,
					StateCensusCSV[].class);
			assertEquals("Uttar Pradesh", stateCensusDataArray[0].state);
			assertEquals("Jammu and Kashmir", stateCensusDataArray[4].state);
		} catch (StateCensusAnalyzerException e) {
		}
	}

	@Test
	public void givenStateCensusCSVData_WhenSortedByArea_ShouldReturnSortedResult() {
		try {
			StateCensusAnalyzer censusAnalyzer = new StateCensusAnalyzer(Paths.get(Constant.STATE_CENSUS_CSV_FILE_PATH));
			censusAnalyzer.readStateCensusCSVData();
			String stateCensusDataSortedByArea = censusAnalyzer
					.getAreaWiseStateCensusSortedData(Paths.get(Constant.STATE_CENSUS_JSON_FILE_SORTED_BY_AREA));
			StateCensusCSV[] stateCensusDataArray = new Gson().fromJson(stateCensusDataSortedByArea,
					StateCensusCSV[].class);
			assertEquals("Madhya Pradesh", stateCensusDataArray[0].state);
			assertEquals("Uttarakhand", stateCensusDataArray[4].state);
			assertEquals(5, stateCensusDataArray.length);
		} catch (StateCensusAnalyzerException e) {
		}
	}

	private static final String STATE_CODE_CSV_FILE_PATH = "C:\\Users\\HP\\eclipse-workspace\\IndianStateCensusAnalyser\\StateCodeCSV.csv";
	private static final String WRONG_STATE_CODE_CSV_FILE_PATH = "C:\\Users\\HP\\eclipse-workspace\\IndianStateCensusAnalyzer\\StateCodeCSV.csv";
	private static final String STATE_CODE_CSV_FILE_WRONG_DELIMITER_PATH = "C:\\Users\\HP\\eclipse-workspace\\IndianStateCensusAnalyser\\StateCodeCSVInvalidDelimiter.csv";
	private static final String STATE_CODE_CSV_FILE_WRONG_HEADER_PATH = "C:\\Users\\HP\\eclipse-workspace\\IndianStateCensusAnalyser\\StateCodeCSVInvalidHeader.csv";

	@Test
	public void givenStateCodeCSVFile_ShouldReturnNumberOfRecords() {
		try {
			StateCensusAnalyzer censusAnalyzer = new StateCensusAnalyzer(Paths.get(STATE_CODE_CSV_FILE_PATH));
			int noOfEntries = censusAnalyzer.readStateCodeCSVData();
			assertEquals(7, noOfEntries);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void givenStateCodeCSVFile_WhenPathIncorrect_ShouldThrowException() {
		try {
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(StateCensusAnalyzerException.class);
			StateCensusAnalyzer censusAnalyzer = new StateCensusAnalyzer(Paths.get(WRONG_STATE_CODE_CSV_FILE_PATH));
			censusAnalyzer.readStateCodeCSVData();
		} catch (StateCensusAnalyzerException e) {
			assertEquals(StateCensusAnalyzerException.ExceptionType.INCORRECT_PATH, e.type);
		}
	}

	@Test
	public void givenStateCodeCSVFile_WhenStateIncorrect_ShouldThrowException() {
		try {
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(StateCensusAnalyzerException.class);
			StateCensusAnalyzer censusAnalyzer = new StateCensusAnalyzer(Paths.get(STATE_CODE_CSV_FILE_PATH));
			censusAnalyzer.readStateCodeCSVData();
		} catch (StateCensusAnalyzerException e) {
			assertEquals(StateCensusAnalyzerException.ExceptionType.INCORRECT_STATE, e.type);
		}
	}

	@Test
	public void givenStateCodeCSVFile_WhenIncorrectDelimeter_ShouldThrowException() {
		try {
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(StateCensusAnalyzerException.class);
			StateCensusAnalyzer censusAnalyzer = new StateCensusAnalyzer(
					Paths.get(STATE_CODE_CSV_FILE_WRONG_DELIMITER_PATH));
			censusAnalyzer.readStateCodeCSVData();
		} catch (StateCensusAnalyzerException e) {
			assertEquals(StateCensusAnalyzerException.ExceptionType.INCORRECT_DELIMITER, e.type);
		}
	}

	@Test
	public void givenStateCodeCSVFile_WhenIncorrectCSVHeader_ShouldThrowException() {
		try {
			ExpectedException exceptionRule = ExpectedException.none();
			exceptionRule.expect(StateCensusAnalyzerException.class);
			StateCensusAnalyzer censusAnalyzer = new StateCensusAnalyzer(
					Paths.get(STATE_CODE_CSV_FILE_WRONG_HEADER_PATH));
			censusAnalyzer.readStateCodeCSVData();
		} catch (StateCensusAnalyzerException e) {
			assertEquals(StateCensusAnalyzerException.ExceptionType.INCORRECT_CSV_HEADER, e.type);
		}
	}

	@Test
	public void givenStateCodeCSVData_WhenSortedByStateCode_ShouldReturnSortedResult() {
		try {
			StateCensusAnalyzer censusAnalyzer = new StateCensusAnalyzer(Paths.get(STATE_CODE_CSV_FILE_PATH));
			censusAnalyzer.readStateCodeCSVData();
			String stateCodeDataSortedByStateCode = censusAnalyzer.getStateCodeWiseStateCodeSortedData();
			CSVStates[] stateCodeDataArray = new Gson().fromJson(stateCodeDataSortedByStateCode, CSVStates[].class);
			assertEquals("HP", stateCodeDataArray[0].stateCode);
			assertEquals("UP", stateCodeDataArray[6].stateCode);
		} catch (StateCensusAnalyzerException e) {
		}
	}
}
