package de.servicezombie.csv2qif.bootstrap;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

import de.servicezombie.csv2qif.service.IConfigurationReaderService;
import de.servicezombie.csv2qif.util.QuickenRulebasedTransformer;
import de.servicezombie.csv2qif.vo.TransformationRuleDto;

public class PatternMatchingRulesProvider implements Provider<QuickenRulebasedTransformer> {

	private static final Log log = LogFactory.getLog(PatternMatchingRulesProvider.class);
	
	IConfigurationReaderService configuration;

	@Inject
	public void setConfigurationReaderService(IConfigurationReaderService configuration) {
		this.configuration = configuration;
	}
	
	public QuickenRulebasedTransformer get() {
		
		QuickenRulebasedTransformer t = new QuickenRulebasedTransformer();
		
		List<TransformationRuleDto> rules = configuration.getAllTransformationRules();
		if (log.isDebugEnabled()) {
			log.debug("get: rules = " + rules);
		}
		
		t.setRules(rules);
		return t;
	}
}
