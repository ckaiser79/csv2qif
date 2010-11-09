package de.servicezombie.csv2qif.service.impl;

import java.util.Iterator;

import com.google.inject.Inject;
import com.google.inject.ProvidedBy;
import com.google.inject.Provides;
import com.google.inject.name.Named;

import de.servicezombie.csv2qif.service.IConverterService;
import de.servicezombie.csv2qif.util.Transformer;
import de.servicezombie.csv2qif.vo.QuickenTransaction;

public class CsvSourceConverterService implements IConverterService<String[]> {
	
	private class QuickenTransactionIterator implements Iterator<QuickenTransaction> {

		private Iterator<String[]> iterator;
		
		public QuickenTransactionIterator(Iterator<String[]> inner) {
			this.iterator = inner;
		}
		
		public boolean hasNext() {
			return iterator != null && iterator.hasNext();
		}

		public QuickenTransaction next() {
			
			String[] currentSource = iterator.next();
			
			QuickenTransaction qt = (QuickenTransaction) sourceTransformer.transform(currentSource);
			
			/* CK rules are optional (7 Nov 2010) */
			if(postProcessTransformer != null) {
				qt = (QuickenTransaction) postProcessTransformer.transform(qt);
			}			
			return qt;
		}

		public void remove() {
			throw new UnsupportedOperationException("remove is not implemented");
		}
		
	}

	private Transformer<QuickenTransaction> sourceTransformer;
	private Transformer<QuickenTransaction> postProcessTransformer;

	// set by configuration file
	@Inject	
	public void setSourceTransformer(@Named("source-transformer") Transformer<QuickenTransaction> sourceTransformer) {
		this.sourceTransformer = sourceTransformer;
	}
	
	@Inject	
	public void setPostProcessTransformer(@Named("post-process-transformer") Transformer<QuickenTransaction> rulebasedTransformer) {
		this.postProcessTransformer = rulebasedTransformer;
	}
	
	public Iterator<QuickenTransaction> convertToQuicken(Iterator<String[]> sourceRecords) {
		return new QuickenTransactionIterator(sourceRecords);		
	}

}
