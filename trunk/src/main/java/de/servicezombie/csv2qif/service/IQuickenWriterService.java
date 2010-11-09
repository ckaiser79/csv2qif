package de.servicezombie.csv2qif.service;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import de.servicezombie.csv2qif.vo.QuickenTransaction;

public interface IQuickenWriterService {
	
	void writeQuickenFile(Writer target, Iterator<QuickenTransaction> records) throws IOException;

}
