package com.wipro.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class NonRecurssiveInternalSiteWebCrawlerTest {
	
	String crawlSite="http://wiprodigital.com/";
	WebCrawler webCrawler;
	
	@Before
	public void init(){
		
		webCrawler= WebCrawlerFactory.getWebCrawlerInstance(WebCrawlerType.INTERNAL_NON_RECURSSIVE, crawlSite);
	}

	/**
	 * This test class provides the solution for the actual Use Case. This test will crawl all internal
	 * web links recursively and also add all the images and external links to a collection. Then the 
	 * collection(Set) is converted into a basic site map aka xml which can be used for hosting purpose.
	 * 
	 * Use Case and Restrictions kept in mind well design and build
	 * 1>	The crawler should be limited to one domain
	 * 2>	Given a starting URL – say http://wiprodigital.com - it should visit all pages within the domain, but not follow the links to external sites such as Google or Twitter
	 * 3>	The output should be a simple site map, showing links to other pages under the same domain, links to static content such as images, and to external URLs.
	 */
	@Test
	public void crawl_wipro_internal_links(){
		// crawl the website and generate a site map
		webCrawler.crawl();
		// read the generated site map and check that its not null
		List<String> listOfURLs = XMLUtil.readXML(NonRecurssiveInternalSiteWebCrawler.OUTPUT_FILE_NAME);
		assertNotNull(listOfURLs);
		// check site map has been able to crawl sufficient number of sites
		assertTrue(listOfURLs.size() > 100);
		//check the site map contains root urr
		assertTrue(listOfURLs.contains(crawlSite));
		// we could also check for various internal urls but avoiding that approach as a site can update/remove link anytime causing the test to fail
		
	}
	
}
