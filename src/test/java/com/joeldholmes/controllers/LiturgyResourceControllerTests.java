package com.joeldholmes.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.joeldholmes.resources.LiturgyResource;

import io.katharsis.queryParams.DefaultQueryParamsParser;
import io.katharsis.queryParams.QueryParams;
import io.katharsis.queryParams.QueryParamsBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LiturgyResourceControllerTests {

	@Autowired
	LiturgyResourceController litController;
	
	
	@Test
	public void testEmptyCall() throws Exception{
		Iterable<LiturgyResource> results = litController.findAll(emptyParams());
		Assert.assertNull(results);
	}
	
	@Test
	public void testDateFilter() throws Exception{
		QueryParams params = createParams("filter[date]", "2017-06-04");
		
		Iterable<LiturgyResource> results = litController.findAll(params);
		Assert.assertNotNull(results);
		
		List<LiturgyResource> resultsList = Lists.newArrayList(results);
		Assert.assertEquals(1, resultsList.size());
		
		LiturgyResource result = resultsList.get(0);
		Assert.assertEquals("Pentecost", result.liturgicalDate);
	}
	
	private QueryParams createParams(String param, String value){
		Map<String, Set<String>> queryParams = new HashMap<String, Set<String>>();
		Set<String> valueSet = Collections.singleton(value);
		queryParams.put(param, valueSet);
		
		QueryParamsBuilder sut = new QueryParamsBuilder(new DefaultQueryParamsParser());
		return sut.buildQueryParams(queryParams);
	}
	
	private QueryParams emptyParams(){
		Map<String, Set<String>> queryParams = new HashMap<String, Set<String>>();
		QueryParamsBuilder sut = new QueryParamsBuilder(new DefaultQueryParamsParser());
		return sut.buildQueryParams(queryParams);
	}
}
