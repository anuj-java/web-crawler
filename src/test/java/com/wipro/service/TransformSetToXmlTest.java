package com.wipro.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class TransformSetToXmlTest {
	
	TransformSetToXml transformSetToXml;
	
	@Before
	public void init(){
		transformSetToXml = new TransformSetToXml();
	}
	
	/**
	 * This method tests the transform method and generates an xml in 
	 * src/test/resources folder of the project
	 */
	@Test
	public void transform_set_to_list(){
		String fileName = "src\\test\\resources\\output_test.xml";
		Set<String> locationSet = new HashSet<String>();
		locationSet.add("http://wiprodigital.com/tag/retail/");
		locationSet.add("http://wiprodigital.com/tag/analyst-report/");
		locationSet.add("https://www.callme.dk/");
		locationSet.add("http://wiprodigital.com/cases/increasing-customer-value-through-iot-for-jcb-india/");
		locationSet.add("http://wiprodigital.com/tag/robo-advisors/");
		
		transformSetToXml.transform(locationSet , new File(fileName));
		List<String> listOfUrls= XMLUtil.readXML(fileName);
		assertNotNull(listOfUrls);
		assertTrue(listOfUrls.size() == 5);
		assertTrue(listOfUrls.contains("http://wiprodigital.com/tag/retail/"));
		
	}

}
