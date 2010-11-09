package de.servicezombie.core.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomDocumentWrapper {
	
	private static final Log log = LogFactory.getLog(DomDocumentWrapper.class);
	
	private File xmlDocumentFile;
	private Document xmlDocument;
	private XPathFactory xpathFactory;
	
	public DomDocumentWrapper(File xmlFile) {
		xmlDocumentFile = xmlFile;
		loadDocumentIfAvailable();
	}
	
	public Document getDocument() {
		return xmlDocument;
	}
	
	private void loadDocumentIfAvailable() {
		
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true); // never forget this!
			DocumentBuilder builder = factory.newDocumentBuilder();
			xmlDocument = builder.parse(xmlDocumentFile);

			xpathFactory = XPathFactory.newInstance();
		}
		catch (Exception e) {
			throw new IllegalStateException("unable to initialize xml parser", e);
		}
		
	}
	
	public  NodeList evalXpath(String xpathQuery) throws XPathExpressionException {
		return evalXpath(xpathQuery, xmlDocument);
	}
	
	public NodeList evalXpath(String xpathQuery, Node parentNode) throws XPathExpressionException {
		
		if (log.isDebugEnabled()) {
			log.debug("evalXpath: >> xpathQuery = '" + xpathQuery + "', root = " + parentNode);
		}
	
		XPath xpath = xpathFactory.newXPath();
		Object xpathResult;

		XPathExpression expr = xpath.compile(xpathQuery);
		xpathResult = expr.evaluate(parentNode, XPathConstants.NODESET);

		return (NodeList) xpathResult;

	}
	
	
	public static String getFirstAttributeTextValue(NodeList nodes, String attributeName) {
		if (nodes == null || nodes.getLength() < 1) {
			return null;
		}
		return getAttributeTextValue(nodes.item(0), attributeName);
	}
	
	public static String getAttributeTextValue(Node n, String attributeName) {
		
		Node attribute = n.getAttributes().getNamedItem(attributeName);
		final String result;
		if (attribute == null) {
			result = null;
		}
		else {
			result = attribute.getNodeValue();
		}
		
		return result;
	}

}
