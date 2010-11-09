package de.servicezombie.csv2qif.util;

import de.servicezombie.csv2qif.vo.QuickenTransaction;
import de.servicezombie.csv2qif.vo.TransformationRuleDto;

/**
 * agents are object which change the state of given objects. Similar to visitors.
 * @author CK
 *
 */
public interface PostProcessAgent {
	
	/**
	 * update transaction if required
	 * @return true if no further checks should be done 
	 */
	boolean visit(QuickenTransaction transaction);
	void setTransactionRule(TransformationRuleDto ruleDto);
}
