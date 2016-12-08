package com.joeldholmes.liturgicalcalendar;

import java.util.Map;

import org.joda.time.DateTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    			System.out.println(mapper.writeValueAsString(dto));
    		}
    		date = date.plusDays(1);
    	}
    }
}
