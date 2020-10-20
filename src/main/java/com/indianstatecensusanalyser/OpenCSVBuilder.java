package com.indianstatecensusanalyser;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class OpenCSVBuilder<E> implements ICSVBuilder<E> {

	@Override
	public Iterator<E> getCSVIterator(Reader reader, Class<E> csvClass) throws CSVException {
		return this.getCSVBean(reader, csvClass).iterator();
	}

	@Override
	public List<E> getCSVList(Reader reader, Class<E> csvClass) throws CSVException {
		return this.getCSVBean(reader, csvClass).parse();
	}

	private CsvToBean<E> getCSVBean(Reader reader, Class<E> csvClass) throws CSVException {
		try {
			CsvToBeanBuilder<E> builder = new CsvToBeanBuilder<E>(reader);
			CsvToBean<E> csvToBean = builder.withType(csvClass).withIgnoreLeadingWhiteSpace(true).build();
			return csvToBean;
		} catch (IllegalStateException e) {
			throw new CSVException("Invalid state present", CSVException.ExceptionType.INCORRECT_STATE);
		}
	}
}
