package com.wipro.service;

import java.io.File;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This class provides implementation behaviour to convert a given Set into
 * XML file
 * 
 * @author anuj.kothiyal
 */
public class TransformSetToXml implements com.wipro.service.Transformer{
	private static Logger LOG = Logger.getLogger(TransformSetToXml.class.getCanonicalName()) ;
	private static String ERROR_GENERATING_XML = "Error occured while generating xml. Please check the logs for more details";
	static String ROOT_ELEMENT = "wipro";
	static String CHILD_ELEMENT_URL = "url";
	static String CHILD_ELEMENT_LOC = "loc";
	
	/**
	 * This method creates an xml with root tag as wipro, followed by xml tag url and loc containing actual value
	 * sample:
	 * <pre>
	 * {@code
	 * 	<wipro>
     *		<url>
     *   		<loc>http://wiprodigital.com/tag/robo-advisors/</loc>
     *		</url>
     *		<url>
     *  		<loc>http://wiprodigital.com/tag/analyst-report/</loc>
     *		</url>
     *	</wipro>
	 * }
	 * 
	 * </pre>
	 */
    public void transform(Set<String> locationSet, File outputFile) {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        try {
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            LOG.log(Level.SEVERE, "Unable to create DocumentBuilder: ", e);
            throw new RuntimeException(ERROR_GENERATING_XML);
        }

        Document doc = docBuilder.newDocument();

            if (!locationSet.isEmpty()) {
                Element root = doc.createElement(ROOT_ELEMENT);
                doc.appendChild(root);
                for (String value : locationSet) {
                    Element url = doc.createElement(CHILD_ELEMENT_URL);
                    Element loc = doc.createElement(CHILD_ELEMENT_LOC);
                    loc.appendChild(doc.createTextNode(value));
                    url.appendChild(loc);
                    root.appendChild(url);
                }
            }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
        	LOG.log(Level.SEVERE, "Unable to create Transformer: ", e );
        	throw new RuntimeException(ERROR_GENERATING_XML);
        }
        // use indentation
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(outputFile);

        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
        	LOG.log(Level.SEVERE, "Unable to transform: " , e);
        	throw new RuntimeException(ERROR_GENERATING_XML);
        }
    }


}
