package de.servicezombie.csv2qif.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.servicezombie.csv2qif.def.ErrorCodes;
import de.servicezombie.csv2qif.vo.QuickenTransaction;

public class VbSauerlandQuickenTransformer implements Transformer<QuickenTransaction> {
	
	private static final Log log = LogFactory.getLog(VbSauerlandQuickenTransformer.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy"); 

	public QuickenTransaction transform(Object from) {
		QuickenTransaction qt = new QuickenTransaction();
		String buf;
		
		String[] source = (String[]) from;
		qt.setAccountNo(Integer.valueOf(source[0]));
		
		// make number look english
		buf = source[19].replace(".", "");
		buf = buf.replace(",", ".");		
		qt.setAmount(Double.valueOf(buf));
		
		//qt.setBankNo(Integer.valueOf(source[]));
		
		try {
			Date dt = sdf.parse(source[1]);
			qt.setBookingDate(dt);
		}
		catch(ParseException e) {
			throw new ErrorCodeAwareException(e, ErrorCodes.INVALID_DATE, source[1]);
		}
		
		qt.setCategory(null);
		qt.setCurrency(source[21]);
		qt.setPartnerName(source[3]);
		qt.setText(source[5] + " " + source[6]);
		
		try {
			Date dt = sdf.parse(source[2]);
			qt.setValueDate(dt);
		}
		catch(ParseException e) {
			throw new ErrorCodeAwareException(e, ErrorCodes.INVALID_DATE, source[2]);
		}
	
		if(log.isDebugEnabled()) {
			log.debug("<< qt = " + qt);
		}
		return qt;		
	}

}
