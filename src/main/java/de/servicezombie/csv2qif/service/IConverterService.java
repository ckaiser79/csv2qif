package de.servicezombie.csv2qif.service;

import java.util.Iterator;

import de.servicezombie.csv2qif.util.Transformer;
import de.servicezombie.csv2qif.vo.QuickenTransaction;

/**
 * Convert from a generic source format to quicken format. The implementation of this interface 
 * should do additional transformations, so that the resulting objects are ready to print  
 * @author "CK"
 *
 * @param <T> 
 */
public interface IConverterService<T> {

	Iterator<QuickenTransaction> convertToQuicken(Iterator<T> sourceRecords);
	void setSourceTransformer(Transformer<QuickenTransaction> transformer);
	
}
