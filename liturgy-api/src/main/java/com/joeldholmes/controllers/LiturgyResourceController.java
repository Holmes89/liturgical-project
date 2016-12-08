package com.joeldholmes.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.joeldholmes.entity.LiturgyEntity;
import com.joeldholmes.liturgycommon.exceptions.ServiceException;
import com.joeldholmes.repository.ILiturgyRepository;
import com.joeldholmes.resources.LiturgyResource;
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
	ILiturgyRepository litRepo;
	
	@HystrixCommand(commandKey="LiturgyFindAll", groupKey="BibleVerse", threadPoolKey="BibleVerse")
	@JsonApiFindAll
	public Iterable<LiturgyResource> findAll(QueryParams params) throws ServiceException{
		Pageable pageable = QueryParamUtils.getPageable(params);
		List<LiturgyEntity> all = Lists.newArrayList(litRepo.findAll(pageable).iterator());
		List<LiturgyResource> resources = new ArrayList<LiturgyResource>();
		for(LiturgyEntity entity: all){
			resources.add(LiturgyResource.convertEntity(entity));
		}
		return resources;
	}

}
