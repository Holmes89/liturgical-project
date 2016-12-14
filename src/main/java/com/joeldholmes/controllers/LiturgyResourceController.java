package com.joeldholmes.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.resources.LiturgyResource;
import com.joeldholmes.services.interfaces.ILiturgyService;
import com.joeldholmes.utils.QueryParamUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.repository.annotations.JsonApiFindAll;
import io.katharsis.repository.annotations.JsonApiResourceRepository;

@Component
@RestController
@JsonApiResourceRepository(LiturgyResource.class)
public class LiturgyResourceController {
	
	@Autowired
	ILiturgyService litService;
	
	@HystrixCommand(commandKey="LiturgyFindAll", groupKey="Liturgy", threadPoolKey="Liturgy")
	@JsonApiFindAll
	public Iterable<LiturgyResource> findAll(QueryParams params) throws ServiceException{
		Map<String, String> filters = QueryParamUtils.getSingleFilters(params);
		if(filters.containsKey("date")){
			return litService.findByDate(filters.get("date"));
		}
		else if(filters.containsKey("approxDate")){
			return litService.findByApproximateDate(filters.get("approxDate"));
		}
		else if(filters.containsKey("startDate") && filters.containsKey("endDate")){
			return litService.findByDateRange(filters.get("startDate"), filters.get("endDate"));
		}
		else if(filters.containsKey("holiday") && filters.containsKey("year")){
			return litService.findByHoliday(filters.get("holiday"), filters.get("year"));
		}
		else if(filters.containsKey("year")){
			return litService.findByYear(filters.get("year"));
		}
		else if(filters.containsKey("holiday")){
			return litService.findByHoliday(filters.get("holiday"));
		}
		else{
			return null;
		}
	}
	
	

}
