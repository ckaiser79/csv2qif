package de.servicezombie.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.Test;

import de.servicezombie.csv2qif.service.impl.CsvSourceConverterService;
import de.servicezombie.csv2qif.util.VbSauerlandQuickenTransformer;
import de.servicezombie.csv2qif.vo.QuickenTransaction;

public class TestCsvSourceConverterService {

	private static final Log log = LogFactory.getLog(TestCsvSourceConverterService.class);
	
	@Test
	public void testBehaviour() throws Exception {
		CsvSourceConverterService svc = new CsvSourceConverterService();
		svc.setSourceTransformer(new VbSauerlandQuickenTransformer());
		svc.setPostProcessTransformer(null);
		
		List<String[]> data = new ArrayList<String[]>();

		data.add(new String[] { "301038600", "12.07.2010", "10.07.2010", "GA NR40220098 BLZ46660022 0", "\"GAA-Auszahlung\"",
				"10.07/07.13UHR Jibi Markt", "EUR     250,00 GEB.EUR 0,00", "", "", "", "", "", "", "", "", "", "", "", "",
				"-250,00", "2.407,70", "EUR" });
		data.add(new String[] { "301038600", "12.07.2010", "10.07.2010", "GA NR40220098 BLZ46660022 0", "\"GAA-Auszahlung\"",
				"10.07/07.13UHR Jibi Markt", "EUR     250,00 GEB.EUR 0,00", "", "", "", "", "", "", "", "", "", "", "", "",
				"-250,00", "2.407,70", "EUR" });
		data.add(new String[] { "301038600", "12.07.2010", "10.07.2010", "GA NR40220098 BLZ46660022 0", "\"GAA-Auszahlung\"",
				"10.07/07.13UHR Jibi Markt", "EUR     250,00 GEB.EUR 0,00", "", "", "", "", "", "", "", "", "", "", "", "",
				"-250,00", "2.407,70", "EUR" });

		Iterator<QuickenTransaction> iter = svc.convertToQuicken(data.iterator());
		while(iter.hasNext()) {
			QuickenTransaction qt = iter.next();
			log.info(qt);			
		}
	}
}
