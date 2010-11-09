package de.servicezombie.csv2qif.service.impl;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import de.servicezombie.core.util.BeanUtils;
import de.servicezombie.core.xml.DomDocumentWrapper;
import de.servicezombie.csv2qif.def.ErrorCodes;
import de.servicezombie.csv2qif.def.TransformerRules;
import de.servicezombie.csv2qif.service.IConverterService;
import de.servicezombie.csv2qif.service.IConfigurationReaderService;
import de.servicezombie.csv2qif.util.ErrorCodeAwareException;
import de.servicezombie.csv2qif.util.Transformer;
import de.servicezombie.csv2qif.vo.CsvConfigProvider;
import de.servicezombie.csv2qif.vo.QuickenTransaction;
import de.servicezombie.csv2qif.vo.TransformationRuleDto;

public class XmlConfigurationReaderService implements IConfigurationReaderService {

	private static final Log log = LogFactory.getLog(XmlConfigurationReaderService.class);

	private File xmlDocumentFile;
	private DomDocumentWrapper domDocument;

	@Inject
	public void setXmlDocument(@Named("xml-document") String documentFile) {
		xmlDocumentFile = documentFile != null ? new File(documentFile) : null;
		if (xmlDocumentFile != null) {
			domDocument = new DomDocumentWrapper(xmlDocumentFile);
		}
	}

	/**
	 * Get transformation rules based on an xml file
	 * 
	 * @param type specifies what type of rules are needed
	 * @see TransformerRules for supported strings
	 */
	public List<TransformationRuleDto> getTransformationRulesFor(String type) throws ErrorCodeAwareException {

		if (domDocument == null) {
			return Collections.emptyList();
		}

		NodeList nodes;
		final String xpathQuery;

		if (type != null)
			xpathQuery = "//rules[@type='" + type + "']";
		else
			xpathQuery = "//rules";

		try {
			nodes = domDocument.evalXpath(xpathQuery);
		}
		catch (XPathExpressionException e) {
			throw new ErrorCodeAwareException(e, ErrorCodes.INVALID_XPATH, xpathQuery, e.getMessage());
		}
		List<TransformationRuleDto> result = new LinkedList<TransformationRuleDto>();

		if (log.isDebugEnabled()) {
			log.debug("getTransformationRulesFor: found " + nodes.getLength() + " number of nodes");
		}

		if (nodes.getLength() == 0) {
			log.warn("unable to find any nodes matching query '" + xpathQuery + "'");
		}

		for (int i = 0; i < nodes.getLength(); i++) {
			List<TransformationRuleDto> singleRules = fillTransformationRuleDto(nodes.item(i));
			result.addAll(singleRules);
		}

		return result;
	}

	private List<TransformationRuleDto> fillTransformationRuleDto(Node parentNode) throws ErrorCodeAwareException {

		List<TransformationRuleDto> singleRules = new LinkedList<TransformationRuleDto>();
		NodeList rules;

		final String xpathQuery = "rule";

		try {
			rules = domDocument.evalXpath(xpathQuery, parentNode);
		}
		catch (XPathExpressionException e) {
			throw new ErrorCodeAwareException(e, ErrorCodes.INVALID_XPATH, xpathQuery, e.getMessage());
		}

		Node defaultResultFieldAttribute = parentNode.getAttributes().getNamedItem("resultField");
		Node defaultPatternFieldAttribute = parentNode.getAttributes().getNamedItem("patternField");

		final String defaultResultField = defaultResultFieldAttribute == null ? null : defaultResultFieldAttribute.getNodeValue();
		final String defaultPatternField = defaultPatternFieldAttribute == null ? null : defaultPatternFieldAttribute
				.getNodeValue();
		final String transformerType = DomDocumentWrapper.getAttributeTextValue(parentNode, "type");

		for (int i = 0; i < rules.getLength(); i++) {
			TransformationRuleDto dto = new TransformationRuleDto();

			Node rule = rules.item(i);
			NamedNodeMap attributes = rule.getAttributes();

			Node resultFieldAttribute = attributes.getNamedItem("resultField");
			Node patternFieldAttribute = attributes.getNamedItem("patternField");

			if (resultFieldAttribute != null) {
				dto.setResultField(resultFieldAttribute.getNodeValue());
			}
			else {
				dto.setResultField(defaultResultField);
			}

			if (patternFieldAttribute != null) {
				dto.setPatternField(patternFieldAttribute.getNodeValue());
			}
			else {
				dto.setPatternField(defaultPatternField);
			}

			dto.setTransformerType(transformerType);
			dto.setPatternAsString(rule.getAttributes().getNamedItem("pattern").getNodeValue());
			dto.setResult(rule.getAttributes().getNamedItem("result").getNodeValue());
			singleRules.add(dto);
		}

		if (log.isDebugEnabled()) {
			log.debug("fillTransformationRuleDto: << singleRules = " + singleRules);
		}
		return singleRules;
	}

	public List<TransformationRuleDto> getAllTransformationRules() {
		return getTransformationRulesFor(null);
	}

	private void applyPropertiesByXmlConfig(Object target, Node parent) throws XPathExpressionException {
		NodeList properties = domDocument.evalXpath("property", parent);

		for (int i = 0; i < properties.getLength(); i++) {
			Node node = properties.item(i);
			final String propertyName = DomDocumentWrapper.getAttributeTextValue(node, "name");
			final String propertyValue = DomDocumentWrapper.getAttributeTextValue(node, "value");

			if (log.isDebugEnabled()) {
				log.debug("applyPropertiesByXmlConfig: call " + target + "." + propertyName + " = '" + propertyValue + "'");
			}

			try {
				org.apache.commons.beanutils.BeanUtils.setProperty(target, propertyName, propertyValue);
			}
			catch (IllegalAccessException e) {
				log.error("Exceptionmessage: " + e.getMessage());
				throw new ErrorCodeAwareException(e, ErrorCodes.CONFIG_INVALID_PROPERTY);
			}
			catch (InvocationTargetException e) {
				log.error("Exceptionmessage: " + e.getCause().getMessage());
				throw new ErrorCodeAwareException(e, ErrorCodes.CONFIG_INVALID_PROPERTY);
			}
		}

	}

	/**
	 * @param name name of transformer to use or null for default transformer
	 */
	public Transformer<QuickenTransaction> getSourceTransformerFor(String name) {

		if (name == null)
			name = getDefaultConverterName();
		
		// can we instantiate it via new?
		Transformer<QuickenTransaction> transformerInstance = null;

		try {
			NodeList nodes = domDocument.evalXpath("/configuration/transformer[@id='" + name + "']");
			String clazzName = DomDocumentWrapper.getFirstAttributeTextValue(nodes, "class");
			if (clazzName == null) {
				throw new ErrorCodeAwareException(ErrorCodes.CONFIG_MISSING_TRANSFORMER_CLASS, name);
			}

			transformerInstance = BeanUtils.instantiateClass(clazzName);
			applyPropertiesByXmlConfig(transformerInstance, nodes.item(0));
		}
		catch (XPathExpressionException e) {
			throw new ErrorCodeAwareException(e, ErrorCodes.CONFIG_INVALID_FILE_STRUCTURE, e.getMessage());
		}

		return transformerInstance;
	}

	/**
	 * @param name name of transformer to use or null for default transformer
	 */
	public CsvConfigProvider getCsvConfigurationFor(String name) {
		
		if (name == null)
			name = getDefaultConverterName();
		
		NodeList nodes = null;
		
		try {
			nodes = domDocument.evalXpath("/configuration/transformer[@id='" + name + "']/csv-configration");
		}
		catch (XPathExpressionException e) {
			throw new ErrorCodeAwareException(e, ErrorCodes.CONFIG_INVALID_FILE_STRUCTURE, e.getMessage());
		}
		
		// if no config available, return null
		if (nodes == null || nodes.getLength() < 1) {
			throw new ErrorCodeAwareException(ErrorCodes.CONFIG_INVALID_TRANSFORMER_NAME, name);
		}
		
		Node n = nodes.item(0);
		CsvConfigProvider csvConfig = new CsvConfigProvider();
		
		String buf;
		
		buf = DomDocumentWrapper.getAttributeTextValue(n, "field-separator");
		if (!StringUtils.isEmpty(buf))
			csvConfig.setFieldSeparator(de.servicezombie.core.util.StringUtils.evaluateEsacpedChar(buf));
		
		buf = DomDocumentWrapper.getAttributeTextValue(n, "skip-lines");
		if (StringUtils.isNumeric(buf)) {
			csvConfig.setSkipLines(Integer.parseInt(buf));
		}
		else {
			throw new ErrorCodeAwareException(ErrorCodes.INVALID_NUMBER, buf);
		}
		
		buf = DomDocumentWrapper.getAttributeTextValue(n, "quote-char");
		if (StringUtils.isEmpty(buf))
			csvConfig.setQuoteChar(null);
		else
			csvConfig.setQuoteChar(de.servicezombie.core.util.StringUtils.evaluateEsacpedChar(buf));
				
		return csvConfig;
	}

	public String getDefaultConverterName() {
		NodeList nodes = null;
		
		try {
			nodes = domDocument.evalXpath("/configuration");
		}
		catch (XPathExpressionException e) {
			throw new ErrorCodeAwareException(e, ErrorCodes.CONFIG_INVALID_FILE_STRUCTURE, e.getMessage());
		}
		
		final String defaultTransformer = DomDocumentWrapper.getFirstAttributeTextValue(nodes, "default-transformer");
		
		if (log.isDebugEnabled()) {
			log.debug("getDefaultConverterService: << " + defaultTransformer);
		}
		return defaultTransformer;
	}

}
