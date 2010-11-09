package de.servicezombie.csv2qif.util;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.servicezombie.csv2qif.def.ErrorCodes;
import de.servicezombie.csv2qif.vo.QuickenTransaction;
import de.servicezombie.csv2qif.vo.TransformationRuleDto;

public class PatternMatchingPostProcessAgent implements PostProcessAgent {

	private static final Log log = LogFactory.getLog(PatternMatchingPostProcessAgent.class);
	
	protected TransformationRuleDto transactionRule;

	public boolean visit(QuickenTransaction transaction) {
		boolean matched = false;
		
		try {

			final String patternField = transactionRule.getPatternField();
			final String patternFieldValue = BeanUtils.getProperty(transaction, patternField);

			if (patternFieldValue != null) {
				Matcher matcher = transactionRule.getPattern().matcher(patternFieldValue);
				
				matched = matcher.matches();

				if (log.isDebugEnabled()) {
					log.debug("visit: matches pattern " + transactionRule.getPattern() + " on '" + patternFieldValue + "' -> " + matched);					
				}
				
				if (matched) {

					final String result = transactionRule.getResult();
					final String resultField = transactionRule.getResultField();
					
					if (log.isDebugEnabled()) {
						log.debug("visit: update " + resultField +  " w/ '" + result + "'");
					}

					BeanUtils.setProperty(transaction, resultField, result);
				}
			}
			else {
				// NOOP, false
			}

			return matched; 
		}
		catch (IllegalAccessException e) {
			throw new ErrorCodeAwareException(e, ErrorCodes.INVALID_XML_CONFIGURATION, e.getMessage());
		}
		catch (InvocationTargetException e) {
			throw new ErrorCodeAwareException(e.getCause(), ErrorCodes.INVALID_XML_CONFIGURATION, e.getCause().getMessage());

		}
		catch (NoSuchMethodException e) {
			throw new ErrorCodeAwareException(e, ErrorCodes.INVALID_XML_CONFIGURATION, e.getMessage());
		}
	}

	public void setTransactionRule(TransformationRuleDto ruleDto) {
		this.transactionRule = ruleDto;
	}

}
