package de.servicezombie.csv2qif.service.impl;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import de.servicezombie.core.util.IterableIterator;
import de.servicezombie.csv2qif.def.QuickenSpecCodes;
import de.servicezombie.csv2qif.service.IQuickenWriterService;
import de.servicezombie.csv2qif.vo.QuickenTransaction;

public class QuickenWriterService implements IQuickenWriterService, QuickenSpecCodes {

	private static final char NL = '\n';
	private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

	public void writeQuickenFile(Writer target, Iterator<QuickenTransaction> records) throws IOException {
		target.append("!Type:Bank").append(NL);

		for (QuickenTransaction record : new IterableIterator<QuickenTransaction>(records)) {
			target.append(toQuickenString(record));
		}
	}

	private String toQuickenString(QuickenTransaction record) {

		StringBuilder sb = new StringBuilder();

		if (record.getCategory() != null) {
			sb.append(CATEGORY).append(record.getCategory()).append(NL);
		}
		if (record.getText() != null) {
			sb.append(MEMO).append(record.getText()).append(NL);
		}

		sb.append(ACCOUNT_NO).append(record.getAccountNo()).append(NL);
		if(record.getBookingDate() != null) {
			sb.append(BOOKING_DATE).append(sdf.format(record.getBookingDate())).append(NL);
		}
		sb.append(PAYEE).append(record.getPartnerName()).append(NL);
		sb.append(AMOUNT).append(record.getAmount()).append(NL);
		sb.append(END_OF_RECORD).append(NL);

		return sb.toString();

	}

}
