package de.servicezombie.csv2qif.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.servicezombie.csv2qif.service.ICsvReaderService;
import de.servicezombie.csv2qif.vo.CsvConfigProvider;

public class CsvReaderService implements ICsvReaderService {

	private static final Log log = LogFactory.getLog(CsvReaderService.class);
	
	private class CsvIterator implements Iterator<String[]> {
		
		private BufferedReader br;
		private String line;
		private CsvConfigProvider config;
		
		public CsvIterator(BufferedReader br, CsvConfigProvider config) {
			this.br = br;
			this.config = config;
		}
		
		public boolean hasNext() {
			try {
				line = br.readLine();
			}
			catch (IOException e) {
				log.error(e.getMessage());
				line = null;
			}
			
			return line != null;
		}

		public String[] next() {			
			String[] record = parseLine();
			return record;
		}

		private String[] parseLine() {
			List<String> record = new ArrayList<String>();
			
			boolean inside = false;
			int lineLength = line.length();
			int lastPos = 0;
			
			final char quoteChar;
			if (config.isQuoteCharUsed()) {
				quoteChar = config.getQuoteChar().charValue();
			}
			else {
				quoteChar = '\0';
			}
			 
			
			for(int i = 0; i < lineLength; i++) {
				char c = line.charAt(i);
				if(c == quoteChar) {
					inside = !inside;
				}
				
				// if not in a quoted field and find next field indicator
				if(!inside && (c == config.getFieldSeparator() || i == lineLength - 1)) {
					
					// remove quote chars
					if (line.charAt(lastPos) == quoteChar) {
						lastPos++;
					}
					
					final String field;
					if (line.charAt(i - 1) == quoteChar) {
						field = line.substring(lastPos, i - 1);
					}
					else {
						field = line.substring(lastPos, i);
					}
					
					record.add(field);
					inside = false;
					lastPos = i + 1;
					
					if(log.isDebugEnabled()) {
						log.debug("extracted field <" + field + ">");
					}
				}
			}
			
			
			String[] res = new String[record.size()];
			res = record.toArray(res);
			return res;
		}

		public void remove() {
			throw new UnsupportedOperationException("cannot remove items from underlying reader");
		}
		
	}
	
	public Iterator<String[]> loadCsv(Reader reader, CsvConfigProvider config) throws IOException {
		BufferedReader br = new BufferedReader(reader);
		for(int i=0; i < config.getSkipLines(); i++) {
			br.readLine();
		}
		
		
		// stupid, but otherwise we use reader if it isd already closed
				
		List<String[]> data = new LinkedList<String[]>();
		Iterator<String[]> iter = new CsvIterator(br, config);
		while(iter.hasNext()) {
			String[] record = iter.next();
			data.add(record);
		}
		
		if (log.isDebugEnabled()) {
			log.debug("loadCsv: found " + data.size() + " transactions");
		}
		return data.iterator();		
	}

}
