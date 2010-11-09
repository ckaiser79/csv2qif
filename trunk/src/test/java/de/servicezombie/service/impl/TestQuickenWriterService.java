package de.servicezombie.service.impl;

import java.io.StringWriter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.Test;

import de.servicezombie.csv2qif.service.impl.QuickenWriterService;
import de.servicezombie.csv2qif.vo.QuickenTransaction;

public class TestQuickenWriterService  {

	private static final Log log = LogFactory.getLog(TestQuickenWriterService.class);
	
	@Test
	public void testBehaviour() throws Exception {
		QuickenWriterService svc = new QuickenWriterService();
		
		List<QuickenTransaction> records = new LinkedList<QuickenTransaction>();
		
		QuickenTransaction item = new QuickenTransaction();
		QuickenTransaction item2;
		
		item.setText("foobar");
		item.setAccountNo(123456);
		item.setBankNo(987654);
		item.setCategory("MY:String");
		item.setCurrency("EUR");
		item.setPartnerName("Mr Foobar");
		item.setValueDate(new Date());
		records.add(item);
				
		item2 = item.clone();
		item2.setText("number 2");
		records.add(item2);
		
		item2 = item.clone();
		item2.setText("number 3");
		records.add(item2);
		
		
		StringWriter target = new StringWriter();
		svc.writeQuickenFile(target, records.iterator());
		
		log.info("\n" + target);
	}
}
