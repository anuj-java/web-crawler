package com.wipro.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Utility class for unit testing
 * @author anuj.kothiyal
 *
 */
public class XMLUtil {
	private static Logger LOG = Logger.getLogger(XMLUtil.class
			.getCanonicalName());

	public static List<String> readXML(String fileName) {
		List<String> listOfUrls = new ArrayList<String>();

		try {

			File file = new File(fileName);

			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();

			Document doc = dBuilder.parse(file);

			if (doc.hasChildNodes()) {
				addNode(doc.getChildNodes(), listOfUrls);
			}

		} catch (Exception e) {
			LOG.log(Level.SEVERE, "Error while reading xml", e);
		}

		return listOfUrls;
	}

	private static void addNode(NodeList nodeList, List<String> listOfUrls) {

		for (int count = 0; count < nodeList.getLength(); count++) {

			Node tempNode = nodeList.item(count);
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

				if (TransformSetToXml.CHILD_ELEMENT_LOC
						.equalsIgnoreCase(tempNode.getNodeName())) {
					listOfUrls.add(tempNode.getTextContent());
				}

				if (tempNode.hasChildNodes()) {
					// loop again if has child nodes
					addNode(tempNode.getChildNodes(), listOfUrls);

				}
			}

		}

	}

}
