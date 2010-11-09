package de.servicezombie.csv2qif.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.servicezombie.csv2qif.vo.QuickenTransaction;
import de.servicezombie.csv2qif.vo.TransformationRuleDto;

public class QuickenRulebasedTransformer implements Transformer<QuickenTransaction> {
	
	private static final Log log = LogFactory.getLog(QuickenRulebasedTransformer.class);
	
	private List<TransformationRuleDto> rules;

	/* a type in dto object will create a diffenrent agent implementation. agent contains more
	 * complex logical checks than simple pattern matching */
	private static final Map<String, PostProcessAgent> agentMap = new HashMap<String, PostProcessAgent>();
	static {
		
		// default, if unknown implementation is requested.
		agentMap.put("default", new PatternMatchingPostProcessAgent());
		
		agentMap.put("pattern-matcher", new PatternMatchingPostProcessAgent());
		agentMap.put("if-withdraw-pattern-matcher", new WithdrawalPostProcessAgent());
		agentMap.put("if-deposit-pattern-matcher", new DepositPostProcessAgent());
		
	}
	
	/**
	 * alternative, direct setter for better testing
	 */
	public void setRules(List<TransformationRuleDto> rules) {
		this.rules = rules;		
	}
	
	public QuickenTransaction transform(Object from) {
		QuickenTransaction transaction = (QuickenTransaction) from;
		
		// if no rules defined
		if (rules == null) {
			if (log.isDebugEnabled()) {
				log.debug("transform: no rules attached. omit post processing checks.");
			}
			return transaction;
		}
		
		for(TransformationRuleDto ruleDto : rules) {
			
			PostProcessAgent agent = getAgent(ruleDto);
			
			if(agent != null) {
				boolean abortLoop = agent.visit(transaction);
				if(abortLoop) break;
			}
		}
		
		return transaction;
	}

	private PostProcessAgent getAgent(TransformationRuleDto dto) {
		
		// get requested agent class based on type parameter
		final String type = dto.getTransformerType();
		PostProcessAgent agent = agentMap.get(type);
		
		if (agent == null) {
			log.warn("type '" + type + "' is unknown. Omit checks.");
		}
		else {
			agent.setTransactionRule(dto);
		}
		
		if (log.isDebugEnabled()) {
			log.debug("getAgent: << " + agent);
		}
		return agent;
	}



}
