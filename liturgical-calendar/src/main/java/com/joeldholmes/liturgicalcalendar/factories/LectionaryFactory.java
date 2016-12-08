package com.joeldholmes.liturgicalcalendar.factories;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joeldholmes.liturgicalcalendar.exceptions.FactoryException;
import com.joeldholmes.liturgycommon.enums.LitanyEventsEnum;
import com.joeldholmes.liturgycommon.util.ErrorCodes;

public class LectionaryFactory {

	private final String ENG_FILE = "/json/lectionary.json";

	Map<String, Map<LitanyEventsEnum, Set<String>>> lectionary;
	
	private static LectionaryFactory factory = new LectionaryFactory();
	
	public static LectionaryFactory getInstance( ) {
	      return factory;
	   }
	
	private LectionaryFactory(){
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(ENG_FILE).getFile());
		try{
			InputStream resourceInputStream = new FileInputStream(file);
			TypeReference<LinkedHashMap<String, LinkedHashMap<String, HashSet<String>>>> typeRef = new TypeReference<LinkedHashMap<String, LinkedHashMap<String, HashSet<String>>>>() {};
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Map<String, Set<String>>> tempMap = mapper.readValue(resourceInputStream, typeRef);
			lectionary = new HashMap<String, Map<LitanyEventsEnum, Set<String>>>();
			for(String year: tempMap.keySet()){
				Map<LitanyEventsEnum, Set<String>> eventVerseMap = new HashMap<LitanyEventsEnum, Set<String>>();
				for(String event: tempMap.get(year).keySet()){
					LitanyEventsEnum e = LitanyEventsEnum.fromDisplayName(event);
					if(e==null){
						System.err.println("Invalid event: "+event);
					}
					eventVerseMap.put(e, tempMap.get(year).get(event));
				} 
				lectionary.put(year, eventVerseMap);
			}
		}catch(IOException e){
			System.err.println("Mapping failure");
		}
	}
	
	public Map<LitanyEventsEnum, Set<String>> getLitYear(String yearCode) throws FactoryException{
		if(yearCode==null || yearCode.isEmpty()){
			throw new FactoryException("Year Code cannot be null", ErrorCodes.INVALID_INPUT);
		}
		yearCode = yearCode.toLowerCase();
		if(yearCode.length()>1 || !"abc".contains(yearCode)){
			throw new FactoryException("Year Code can only be a, b, or c", ErrorCodes.INVALID_INPUT);
		}
		return lectionary.get(yearCode);
	}
	
}
