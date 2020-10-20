package com.indianstatecensusanalyser;

import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public interface ICSVBuilder<E> {

	public Iterator<E> getCSVIterator(Reader reader, Class<E> csvClass) throws CSVException;

	public List<E> getCSVList(Reader reader, Class<E> csvClass) throws CSVException;

}