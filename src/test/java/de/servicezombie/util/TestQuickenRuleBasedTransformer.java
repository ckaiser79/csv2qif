package de.servicezombie.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.testng.annotations.Test;

import de.servicezombie.csv2qif.util.QuickenRulebasedTransformer;
import de.servicezombie.csv2qif.vo.QuickenTransaction;
import de.servicezombie.csv2qif.vo.TransformationRuleDto;

public class TestQuickenRuleBasedTransformer {
	
	private static final Log log = LogFactory.getLog(TestQuickenRuleBasedTransformer.class);
	
	@Test
	public void testBehaviour() throws Exception {
		QuickenRulebasedTransformer t = new QuickenRulebasedTransformer();
				
		QuickenTransaction qt = new QuickenTransaction();
		qt.setCategory(null);
		qt.setPartnerName("mr foo");
		qt.setText("danke");
		
		t.setRules(null);
		t.transform(qt);
		log.info(qt);
		
		List<TransformationRuleDto> rules = new ArrayList<TransformationRuleDto>();
		TransformationRuleDto dto1 = new TransformationRuleDto();
		TransformationRuleDto dto2 = new TransformationRuleDto();
		
		dto1.setTransformerType("payee");
		dto2.setTransformerType(null);
		
		dto1.setPatternField("partnerName");
		dto1.setPatternAsString(".+foo.*");
		dto1.setResult("FOO COMPANY");
		dto1.setResultField("partnerName");
		
		rules.add(dto1);		
		rules.add(dto2);
		
		t.setRules(rules);
		t.transform(qt);
		log.info(qt);
	}

}
