package com.joeldholmes.liturgicalcalendar;

import java.util.Map;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.joeldholmes.liturgicalcalendar.services.impl.LectionaryService;
import com.joeldholmes.liturgicalcalendar.services.interfaces.ILectionaryService;
import com.joeldholmes.liturgycommon.dto.LiturgyDTO;
import com.joeldholmes.liturgycommon.exceptions.ServiceException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ServiceException, JsonProcessingException
    {
    	DateTime date = new DateTime(1900, 1, 1, 0, 0, 0);
    	ILectionaryService lectService = new LectionaryService();

    	ObjectMapper mapper = new ObjectMapper();
    	
    	for(int x=0; x<(366*300); x++){
    		Map<String, LiturgyDTO> lectionaryResults = lectService.getLectionary(date);
    		for(LiturgyDTO dto : lectionaryResults.values()){
    			ObjectNode objectNode = mapper.convertValue(dto, ObjectNode.class);
    			objectNode.remove("date");
    			ObjectNode dateNode = mapper.createObjectNode();
    			dateNode.put("$date", dto.date.getTime());
    			objectNode.set("date", dateNode);
    			System.out.println(mapper.writeValueAsString(objectNode));
    		}
    		date = date.plusDays(1);
    	}
    }
}
