package de.servicezombie.util;

import org.testng.Assert;
import org.testng.annotations.Test;

import de.servicezombie.csv2qif.util.VbSauerlandQuickenTransformer;
import de.servicezombie.csv2qif.vo.QuickenTransaction;

public class TestVbSauerlandQuickenTransformer {
	
	private final static String[] testData = "122545678;01.05.2010;01.05.2010; PENNY SAGT DANKE  ;Einzugsermächtig.-lastschr.;  XYZXYZ;ELV1234567 ;ME1;;;;;;;;;;;;-1,10;;EUR".split(";"); 
	
	@Test
	public void testTextConcatination() throws Exception {
		VbSauerlandQuickenTransformer t = new VbSauerlandQuickenTransformer();
		QuickenTransaction qt = t.transform(testData);
		
		Assert.assertEquals(qt.getText(), "Einzugsermächtig.-lastschr. XYZXYZ ELV1234567 ME1");
	}
	
	@Test
	public void testPayee() throws Exception {
		VbSauerlandQuickenTransformer t = new VbSauerlandQuickenTransformer();
		QuickenTransaction qt = t.transform(testData);
		
		Assert.assertEquals(qt.getPartnerName(), "PENNY SAGT DANKE");
	}

}
