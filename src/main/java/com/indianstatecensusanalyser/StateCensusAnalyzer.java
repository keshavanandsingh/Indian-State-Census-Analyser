package com.indianstatecensusanalyser;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.StreamSupport;
import com.google.gson.Gson;

public class StateCensusAnalyzer {
	public Path csvFilePath;
	List<StateCensusCSV> stateCensusCSVList;
	List<CSVStates> stateCodeCSVList;

	public StateCensusAnalyzer(Path csvFilePath) {
		this.csvFilePath = csvFilePath;
	}

	public int readStateCensusCSVData() throws StateCensusAnalyzerException {
		try (Reader reader = Files.newBufferedReader(csvFilePath)) {
			ICSVBuilder<StateCensusCSV> csvBuilder = CSVBuilderFactory.createCSVBuilder();
			stateCensusCSVList = csvBuilder.getCSVList(reader, StateCensusCSV.class);
			String[] expectedHeader = { "State", "Population", "Area In Square Km", "Density Per Square Km" };
			if (isWrongDelimiter(expectedHeader, csvFilePath)) {
				throw new StateCensusAnalyzerException("Invalid delimiter",
						StateCensusAnalyzerException.ExceptionType.INCORRECT_DELIMITER);
			}
			if (isWrongHeader(expectedHeader, csvFilePath)) {
				throw new StateCensusAnalyzerException("Invalid CSV header",
						StateCensusAnalyzerException.ExceptionType.INCORRECT_CSV_HEADER);
			}
			return stateCensusCSVList.size();
		} catch (IOException | CSVException e) {
			throw new StateCensusAnalyzerException("Invalid path entered",
					StateCensusAnalyzerException.ExceptionType.INCORRECT_PATH);
		}
	}

	public int readStateCodeCSVData() throws StateCensusAnalyzerException {
		try (Reader reader = Files.newBufferedReader(csvFilePath)) {
			ICSVBuilder<CSVStates> csvBuilder = CSVBuilderFactory.createCSVBuilder();
			stateCodeCSVList = csvBuilder.getCSVList(reader, CSVStates.class);
			String[] expectedHeader = { "State Name", "State Code" };
			if (isWrongDelimiter(expectedHeader, csvFilePath)) {
				throw new StateCensusAnalyzerException("Invalid delimiter",
						StateCensusAnalyzerException.ExceptionType.INCORRECT_DELIMITER);
			}
			if (isWrongHeader(expectedHeader, csvFilePath)) {
				throw new StateCensusAnalyzerException("Invalid CSV header",
						StateCensusAnalyzerException.ExceptionType.INCORRECT_CSV_HEADER);
			}
			return stateCodeCSVList.size();
		} catch (IOException | CSVException e) {
			throw new StateCensusAnalyzerException("Invalid path entered",
					StateCensusAnalyzerException.ExceptionType.INCORRECT_PATH);
		}
	}

	private <E> int getCount(Iterator<E> csvIterator) {
		Iterable<E> csvIterable = () -> csvIterator;
		int noOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
		return noOfEnteries;
	}

	public boolean isWrongHeader(String[] expectedHeader, Path csvFilePath) throws StateCensusAnalyzerException {
		boolean isWrongHeader = false;
		try (BufferedReader br = Files.newBufferedReader(csvFilePath)) {
			String headerRow = br.readLine();
			String[] header = headerRow.split(",");
			if (!Arrays.equals(expectedHeader, header))
				isWrongHeader = true;
			return isWrongHeader;
		} catch (IOException e) {
			throw new StateCensusAnalyzerException("Invalid path entered",
					StateCensusAnalyzerException.ExceptionType.INCORRECT_PATH);
		}
	}

	public boolean isWrongDelimiter(String[] expectedHeader, Path csvFilePath) throws StateCensusAnalyzerException {
		boolean isWrongDelimiter = false;
		try (BufferedReader br = Files.newBufferedReader(csvFilePath)) {
			String headerRow = br.readLine();
			String[] header = headerRow.split(",");
			if (header.length < expectedHeader.length)
				isWrongDelimiter = true;
			return isWrongDelimiter;
		} catch (IOException e) {
			throw new StateCensusAnalyzerException("Invalid path entered",
					StateCensusAnalyzerException.ExceptionType.INCORRECT_PATH);
		}
	}

	public String getStateWiseStateCensusSortedData() throws StateCensusAnalyzerException {
		if (stateCensusCSVList == null || stateCensusCSVList.size() == 0)
			throw new StateCensusAnalyzerException("No CSV Data Found",
					StateCensusAnalyzerException.ExceptionType.NO_DATA);
		Comparator<StateCensusCSV> stateCensusComparator = Comparator.comparing(stateCensus -> stateCensus.state);
		this.sortAscending(stateCensusCSVList, stateCensusComparator);
		String sortedStateCensusJson = new Gson().toJson(stateCensusCSVList);
		return sortedStateCensusJson;
	}

	public String getDensityWiseStateCensusSortedData() throws StateCensusAnalyzerException {
		if (stateCensusCSVList == null || stateCensusCSVList.size() == 0)
			throw new StateCensusAnalyzerException("No CSV Data Found",
					StateCensusAnalyzerException.ExceptionType.NO_DATA);
		Comparator<StateCensusCSV> stateCensusComparator = Comparator
				.comparing(stateCensus -> stateCensus.densityPerSqKm);
		this.sortDescending(stateCensusCSVList, stateCensusComparator);
		String sortedStateCensusJson = new Gson().toJson(stateCensusCSVList);
		return sortedStateCensusJson;
	}

	public String getPopulationWiseStateCensusSortedData(Path stateCensusJsonFileSorted)
			throws StateCensusAnalyzerException {
		if (stateCensusCSVList == null || stateCensusCSVList.size() == 0)
			throw new StateCensusAnalyzerException("No CSV Data Found",
					StateCensusAnalyzerException.ExceptionType.NO_DATA);
		Comparator<StateCensusCSV> stateCensusComparator = Comparator.comparing(stateCensus -> stateCensus.population);
		this.sortDescending(stateCensusCSVList, stateCensusComparator);
		String sortedStateCensusJson = new Gson().toJson(stateCensusCSVList);
		writeSortedStateCensusCSVData(sortedStateCensusJson, stateCensusJsonFileSorted);
		return sortedStateCensusJson;
	}

	public String getAreaWiseStateCensusSortedData(Path stateCensusJsonFileSorted) throws StateCensusAnalyzerException {
		if (stateCensusCSVList == null || stateCensusCSVList.size() == 0)
			throw new StateCensusAnalyzerException("No CSV Data Found",
					StateCensusAnalyzerException.ExceptionType.NO_DATA);
		Comparator<StateCensusCSV> stateCensusComparator = Comparator.comparing(stateCensus -> stateCensus.areaInSqKm);
		this.sortDescending(stateCensusCSVList, stateCensusComparator);
		String sortedStateCensusJson = new Gson().toJson(stateCensusCSVList);
		writeSortedStateCensusCSVData(sortedStateCensusJson, stateCensusJsonFileSorted);
		return sortedStateCensusJson;
	}

	public String getStateCodeWiseStateCodeSortedData() throws StateCensusAnalyzerException {
		if (stateCodeCSVList == null || stateCodeCSVList.size() == 0)
			throw new StateCensusAnalyzerException("No CSV Data Found",
					StateCensusAnalyzerException.ExceptionType.NO_DATA);
		Comparator<CSVStates> stateCodeComparator = Comparator.comparing(stateCode -> stateCode.stateCode);
		this.sortAscending(stateCodeCSVList, stateCodeComparator);
		String sortedStateCodeJson = new Gson().toJson(stateCodeCSVList);
		return sortedStateCodeJson;
	}

	private <E> void sortAscending(List<E> csvList, Comparator<E> comparator) {
		for (int i = 0; i < csvList.size() - 1; i++) {
			for (int j = 0; j < csvList.size() - i - 1; j++) {
				E census1 = csvList.get(j);
				E census2 = csvList.get(j + 1);
				if (comparator.compare(census1, census2) > 0) {
					csvList.set(j, census2);
					csvList.set(j + 1, census1);
				}
			}
		}
	}

	private <E> void sortDescending(List<E> csvList, Comparator<E> comparator) {
		for (int i = 0; i < csvList.size() - 1; i++) {
			for (int j = 0; j < csvList.size() - i - 1; j++) {
				E census1 = csvList.get(j);
				E census2 = csvList.get(j + 1);
				if (comparator.compare(census1, census2) < 0) {
					csvList.set(j, census2);
					csvList.set(j + 1, census1);
				}
			}
		}
	}

	public void writeSortedStateCensusCSVData(String sortedJson, Path csvFilePathForSortedJson)
			throws StateCensusAnalyzerException {
		try (Writer writer = Files.newBufferedWriter(csvFilePathForSortedJson)) {
			Gson gson = new Gson();
			String json = gson.toJson(stateCensusCSVList);
			writer.write(json);
		} catch (IOException e) {
			throw new StateCensusAnalyzerException("Invalid path entered",
					StateCensusAnalyzerException.ExceptionType.INCORRECT_PATH);
		}
	}
}