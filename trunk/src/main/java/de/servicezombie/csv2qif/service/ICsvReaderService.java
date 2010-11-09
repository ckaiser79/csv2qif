package de.servicezombie.csv2qif.service;

import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import de.servicezombie.csv2qif.util.Transformer;
import de.servicezombie.csv2qif.vo.CsvConfigProvider;
import de.servicezombie.csv2qif.vo.QuickenTransaction;

public interface ICsvReaderService {
	
	Iterator<String[]> loadCsv(Reader reader, CsvConfigProvider config) throws IOException;
}
