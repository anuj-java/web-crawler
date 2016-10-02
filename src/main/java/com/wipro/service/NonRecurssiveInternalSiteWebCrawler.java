package com.wipro.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This class provides implementation behaviour to crawl all 
 * internal web urls for a given root URL and creates a site map
 * in a non-recurssive fashion
 *  
 * @author anuj.kothiyal
 *
 */
public class NonRecurssiveInternalSiteWebCrawler implements WebCrawler {
	private static Logger LOG = Logger.getLogger(NonRecurssiveInternalSiteWebCrawler.class.getCanonicalName()) ;
    private final String ROOT_URL;
    /** List of all urls. Avoiding duplicates via use of Set interface **/
	private Set<String> locationSet = new HashSet<String>();
	/** maintaining a Queue to track urls to be processed  **/
	private Queue<String> listOfUrls = new LinkedList<String>();
	private Transformer transformer;
	private static final List<String> IMAGE_TYPES = new ArrayList<String>();
	private static final String NOT_A_PAGE = ".*?\\#.*";
	/** Create a sitemap with name as- output_<current time stamp>.xml at project root. In a more
	 * practical scenario we could have generated and moved this into a location from where we could
	 * host the xml to outside world. **/
	static String OUTPUT_FILE_NAME = "output_"+(LocalDateTime.now().toString()).replaceAll(":", ".")+".xml";
	
    static{
    	IMAGE_TYPES.add(".*?\\.jpg");
    	IMAGE_TYPES.add(".*?\\.jpeg");
    	IMAGE_TYPES.add(".*?\\.png");
    	IMAGE_TYPES.add(".*?\\.gif");	
    }
	
	public NonRecurssiveInternalSiteWebCrawler(String url){
		this.ROOT_URL = url;
		this.transformer = new TransformSetToXml();
	}
	
	/**
	 * This method provides implementation behaviour to crawl all internal sites and add each
	 * of them to a Set. It also adds images and external urls to the Set.
	 * 
	 * Once the crawling is completed Set will contain list of all urls which is then converted
	 * into an xml via user of Transformer service
	 */
	public void crawl() {
        LOG.log(Level.INFO, "Crawling ...");
        
		this.crawlPage(ROOT_URL);
		transformer.transform(locationSet,  new File(OUTPUT_FILE_NAME));
		
		LOG.log(Level.INFO, "Output generated at project root, File name is "+OUTPUT_FILE_NAME);
	}

	/**
	 * A recursive method which crawls all internal urls and adds
	 * external urls without crawling
	 * @param url
	 */
	    private void crawlPage(String url) {

	    this.listOfUrls.add(url);
	    
	    while(listOfUrls.peek() != null ){
	    		String tempUrl = listOfUrls.poll();
		        locationSet.add(tempUrl);
		        
		        Document doc= null;
		        try {
		        	/** We had an option of spinning our own html parser here but it is recommended not to
		        	 * re-invent wheel if a stable well tested api already provides required functionality**/
		            doc = Jsoup.connect(tempUrl).userAgent(WebCrawler.APPLICATION_USER_AGENT).timeout(5000).get();
		        } catch (IOException e) {
		        	// in case of an issue connecting with a url, log the exception and continue
		            LOG.log(Level.SEVERE,"\nUnable to read Page at [" + tempUrl + "]");
		            //locationSet.add(url);
		            continue;
		        }
		        addImagesToSet(doc);
		        // Checking all links on the page
		        
		        Elements links = doc.select("a[href]");
		        inner:
		        for (Element link : links) {
		            String linkUrl = link.attr("abs:href");
		            // if link starts with the root url, then it is internal
		            if (linkUrl.startsWith(ROOT_URL)) { 
		            	// check link does not end with a '#'
		                if (!linkUrl.matches(NOT_A_PAGE)) { 
		                    // Is link a file?
		                    for (String regex : IMAGE_TYPES) {
		                        if (linkUrl.matches(regex)) {
		                            // Image file, add to set and dont crawl
		                        	locationSet.add(linkUrl);
		                            continue inner;
		                        }
		                    }
		                    // if we are here this is a valid internal url, add it to Queue
		                    if (linkUrl.length()>0 && !linkUrl.startsWith("mailto:") && !locationSet.contains(linkUrl)) {	
		                    	listOfUrls.add(linkUrl);
		                    }
		                }
		            } else{
		            	// add the external url to list but dont crawl
		            	locationSet.add(linkUrl);
		            }
		        }
	    	}
	    }

    /**
     * This method finds all image urls via html 'img' attrinute 
     * and adds it to the Set of url's
     * 
     * @param doc HTML document
     */
	private void addImagesToSet(Document doc) {
		// Get every image from that page
        Elements images = doc.select("img");
        for (Element image : images) {
            String imageUrl = image.attr("abs:src");
            // It is an image --> add to list
            locationSet.add(imageUrl);
        }
	}	
    
}
