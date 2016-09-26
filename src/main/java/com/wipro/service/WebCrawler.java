package com.wipro.service;

/**
 * This interface provides functionality of a Web Crawler.
 * 
 * @author anuj.kothiyal
 */
public interface WebCrawler {
	
    public static final String APPLICATION_NAME = "WebCrawler";
    public static final String APPLICATION_VERSION = "1.0";
    public static final String APPLICATION_URL = "https://github.com/anuj-java/web-crawler";
    public static final String APPLICATION_USER_AGENT = APPLICATION_NAME + "/" + APPLICATION_VERSION + " (" + APPLICATION_URL + ")";	
	
    /**
     * A Web Crawler 
     */
	void crawl();

}
