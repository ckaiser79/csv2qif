package de.servicezombie.csv2qif.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.servicezombie.csv2qif.def.ErrorCodes;
import de.servicezombie.csv2qif.vo.QuickenTransaction;

public class VbSauerlandQuickenTransformer implements Transformer<QuickenTransaction> {
	
	private static final Log log = LogFactory.getLog(VbSauerlandQuickenTransformer.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	private static final String SPACE = " "; 

	public QuickenTransaction transform(Object from) {
		QuickenTransaction qt = new QuickenTransaction();
		String buf;
		
		String[] source = (String[]) from;
		stripQuotations(source);
		
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
		qt.setText(buildText(source));
		
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

	private void stripQuotations(String[] source) {
		for(int i = 0; i < source.length; i++) {
			source[i] = source[i].trim();
			if (source[i].startsWith("\"") && source[i].endsWith("\"")) {
				source[i] = source[i].substring(1, source[i].length() - 1);
			}
			 
		}
	}

	private String buildText(String[] source) {
		StringBuilder sb = new StringBuilder();
		
		for(int i = 4; i < 19; i++) {
			if(!StringUtils.isEmpty(source[i])) {
				sb.append(source[i]).append(SPACE);
			}
		}
		return sb.substring(0, sb.length() - 1);
	}

}
