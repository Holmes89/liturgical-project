package com.joeldholmes.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.assertj.core.util.Lists;
import org.joda.time.DateTime;
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
	
	@Test
	public void testAproxDateFilter() throws Exception{
		QueryParams params = createParams("filter[approxDate]", "2017-06-03");
		
		Iterable<LiturgyResource> results = litController.findAll(params);
		Assert.assertNotNull(results);
		
		List<LiturgyResource> resultsList = Lists.newArrayList(results);
		Assert.assertEquals(1, resultsList.size());
		
		LiturgyResource result = resultsList.get(0);
		Assert.assertEquals("Pentecost", result.liturgicalDate);
	}
	
	@Test
	public void testDateRange() throws Exception{
		Map<String, Set<String>> paramMap = new HashMap<String, Set<String>>();
		paramMap.put("filter[startDate]", Collections.singleton("2017-01-01"));
		paramMap.put("filter[endDate]", Collections.singleton("2018-01-01"));
		
		QueryParams params = createParams(paramMap);
		
		Iterable<LiturgyResource> results = litController.findAll(params);
		Assert.assertNotNull(results);
		
		List<LiturgyResource> resultsList = Lists.newArrayList(results);
		Assert.assertEquals(68, resultsList.size());
	}
	
	@Test
	public void testYearFilter() throws Exception{
		QueryParams params = createParams("filter[year]", "2017");
		
		Iterable<LiturgyResource> results = litController.findAll(params);
		Assert.assertNotNull(results);
		
		List<LiturgyResource> resultsList = Lists.newArrayList(results);
		Assert.assertEquals(71, resultsList.size());
	}
	
	@Test
	public void testHolidayFilter() throws Exception{
		QueryParams params = createParams("filter[holiday]", "Christmas Day");
		
		Iterable<LiturgyResource> results = litController.findAll(params);
		Assert.assertNotNull(results);
		
		List<LiturgyResource> resultsList = Lists.newArrayList(results);
		Assert.assertEquals(1, resultsList.size());
		
		LiturgyResource result = resultsList.get(0);
		Assert.assertEquals("Christmas Day", result.liturgicalDate);
		
		DateTime currentDate = new DateTime();
		DateTime resultDate = new DateTime(result.date);
		
		Assert.assertEquals(currentDate.getYear(), resultDate.getYear());
		Assert.assertEquals(12, resultDate.getMonthOfYear());
		Assert.assertEquals(25, resultDate.getDayOfMonth());
	}
	
	@Test
	public void testHolidayAndYearFilter() throws Exception{
		Map<String, Set<String>> paramMap = new HashMap<String, Set<String>>();
		paramMap.put("filter[holiday]", Collections.singleton("Easter"));
		paramMap.put("filter[year]", Collections.singleton("2017"));
		
		QueryParams params = createParams(paramMap);
		
		Iterable<LiturgyResource> results = litController.findAll(params);
		Assert.assertNotNull(results);
		
		List<LiturgyResource> resultsList = Lists.newArrayList(results);
		Assert.assertEquals(1, resultsList.size());
		
		LiturgyResource result = resultsList.get(0);
		Assert.assertEquals("Easter", result.liturgicalDate);
		
		DateTime resultDate = new DateTime(result.date);
		
		Assert.assertEquals(2017, resultDate.getYear());
		Assert.assertEquals(4, resultDate.getMonthOfYear());
		Assert.assertEquals(16, resultDate.getDayOfMonth());
	}

	
	private QueryParams createParams(String param, String value){
		Map<String, Set<String>> queryParams = new HashMap<String, Set<String>>();
		Set<String> valueSet = Collections.singleton(value);
		queryParams.put(param, valueSet);
		
		return createParams(queryParams);
	}
	
	private QueryParams createParams(Map<String, Set<String>> queryParams){
		
		QueryParamsBuilder sut = new QueryParamsBuilder(new DefaultQueryParamsParser());
		return sut.buildQueryParams(queryParams);
	}
	
	private QueryParams emptyParams(){
		Map<String, Set<String>> queryParams = new HashMap<String, Set<String>>();
		return createParams(queryParams);
	}
}
