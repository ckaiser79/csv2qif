package de.servicezombie.service.impl;

import java.io.StringReader;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.Test;

import de.servicezombie.core.util.IterableIterator;
import de.servicezombie.csv2qif.service.impl.CsvReaderService;
import de.servicezombie.csv2qif.vo.CsvConfigProvider;


public class TestCsvReaderService {

	private static final Log log = LogFactory.getLog(TestCsvReaderService.class);
	
	@Test(enabled = false)
	public void testBehaviour() throws Exception {
		CsvReaderService svc = new CsvReaderService();
		
		CsvConfigProvider config = new CsvConfigProvider();
		config.setFieldSeparator(',');
		config.setQuoteChar('\'');
		config.setSkipLines(1);
		
		String data = "123,'456',789','abc,def'\n123,'456',789,'abc,def'\n";
		StringReader reader = new StringReader(data );
		Iterator<String[]> iter = svc.loadCsv(reader , config);
		
		for(String[] rec : new IterableIterator<String[]>(iter)) {			
			StringBuilder sb = new StringBuilder("| ");
			for(String s : rec) {
				sb.append(s).append(" | ");
			}
			sb.append("\n");
			log.info(rec.length + "\n" + sb);
		}
	}
	
	@Test(enabled = true)
	public void testBehaviourNoQuoteChar() throws Exception {
		CsvReaderService svc = new CsvReaderService();
		
		CsvConfigProvider config = new CsvConfigProvider();
		config.setFieldSeparator(',');
		config.setQuoteChar(null);
		config.setSkipLines(1);
		
		String data = "123,'456',789','abc,def'\n123,'456',789,'abc,def'\n";
		StringReader reader = new StringReader(data );
		Iterator<String[]> iter = svc.loadCsv(reader , config);
		
		for(String[] rec : new IterableIterator<String[]>(iter)) {			
			StringBuilder sb = new StringBuilder("| ");
			for(String s : rec) {
				sb.append(s).append(" | ");
			}
			sb.append("\n");
			log.info(rec.length + "\n" + sb);
		}
	}
}
