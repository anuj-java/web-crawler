package com.wipro.service;

import java.io.File;
import java.util.Set;

/**
 * This interface provides functionality for Transforming given Set of
 * String values into an xml file.
 * 
 * @author anuj.kothiyal
 */
public interface Transformer {
	
	/**
	 * 
	 * @param locationSet Collection of String objects to be transformed into an xml
	 * @param outputFile XML File to be generated
	 */
	void transform(Set<String> locationSet, File outputFile);

}
