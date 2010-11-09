package de.servicezombie.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jmock.Mockery;
import org.testng.Assert;
import org.testng.annotations.Test;

import de.servicezombie.csv2qif.service.impl.XmlConfigurationReaderService;
import de.servicezombie.csv2qif.util.Transformer;
import de.servicezombie.csv2qif.vo.CsvConfigProvider;
import de.servicezombie.csv2qif.vo.QuickenTransaction;
import de.servicezombie.csv2qif.vo.TransformationRuleDto;

public class TestXmlConfigurationReaderService {

	private static final String DEFAULT_XML_CONFIG = "src/test/resources/transformer.xml";
	private static final Log log = LogFactory.getLog(TestXmlConfigurationReaderService.class);
	
	@Test
	public void testRuleLoading() throws Exception {
				
		XmlConfigurationReaderService svc = new XmlConfigurationReaderService();
		svc.setXmlDocument(DEFAULT_XML_CONFIG);
		
		List<TransformationRuleDto> data = svc.getTransformationRulesFor("pattern-matcher");
		log.info("data=" + data);
		Assert.assertFalse(data.isEmpty());
		
		data = svc.getTransformationRulesFor("sdfsdfsdf");
		log.info("emptyData=" + data);
		Assert.assertTrue(data.isEmpty());
		
		data = svc.getTransformationRulesFor("if-deposit-pattern-matcher");
		log.info("data=" + data);
		Assert.assertFalse(data.isEmpty());
		
		svc.getAllTransformationRules();
	}
	
	@Test
	public void testGetCsvConfiguration() throws Exception {
		XmlConfigurationReaderService svc = new XmlConfigurationReaderService();
		svc.setXmlDocument(DEFAULT_XML_CONFIG);		
		
		CsvConfigProvider csvConfig = svc.getCsvConfigurationFor("bank-1");
		
		Assert.assertNotNull(csvConfig);
		Assert.assertEquals(0, csvConfig.getSkipLines());
		Assert.assertNull(csvConfig.getQuoteChar());
		Assert.assertEquals('\t', csvConfig.getFieldSeparator());
		
	}
	
	@Test
	public void testTransformerLoading() throws Exception {
		
		Mockery mockery = new Mockery();
		
		
		Transformer<QuickenTransaction> actualSourceTransformer;
		
		XmlConfigurationReaderService svc = new XmlConfigurationReaderService();
		svc.setXmlDocument(DEFAULT_XML_CONFIG);
									
		actualSourceTransformer = svc.getSourceTransformerFor("bank-1");
		Assert.assertTrue(actualSourceTransformer instanceof de.servicezombie.csv2qif.util.BeanshellScriptedQuickenTransformer);		
		mockery.assertIsSatisfied();
				
		String name = svc.getDefaultConverterName();
		Assert.assertEquals(name, "vb-sauerland");
		
	}
}
