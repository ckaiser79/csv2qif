package de.servicezombie.csv2qif.util;

import de.servicezombie.csv2qif.vo.QuickenTransaction;

public class WithdrawalPostProcessAgent extends PatternMatchingPostProcessAgent {

	public boolean visit(QuickenTransaction transaction) {
		Double amount = transaction.getAmount();
		if (amount != null && amount.doubleValue() < 0) {
			return super.visit(transaction);
		}
		else {
			return false;
		}
	}

}
