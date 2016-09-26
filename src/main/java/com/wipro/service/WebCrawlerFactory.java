package com.wipro.service;

/**
 * Creating a factory class to separate instantiation logic from implementation. It would be easier to
 * add new implementation for External Web crawler or another version of Internal web crawler without 
 * modifying existing code. Hence adhering to OPEN-CLOSE principle of SOLID
 * 
 * @author anuj.kothiyal
 */
public class WebCrawlerFactory {
	
	/**
	 * 
	 * @param type- What type for WebCrawler is required ex. INTERNAL
	 * @param url - Root URL to start crawling
	 * @return instance of a web crawler
	 */
	public static WebCrawler getWebCrawlerInstance(WebCrawlerType type, String url){
		WebCrawler webCrawler =null;
		
		if(WebCrawlerType.INTERNAL.compareTo(type) == 0){
			webCrawler = new InternalSiteWebCrawler(url);
			
		}else if(WebCrawlerType.EXTERNAL.compareTo(type) == 0){
			throw new RuntimeException("Not yet Implemented");
			
		}
		
		return webCrawler;
	}

}
