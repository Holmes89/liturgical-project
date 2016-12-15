package com.joeldholmes.repository;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.joda.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.joeldholmes.entity.LiturgyEntity;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LiturgyRepositoryTests {

	@Autowired
	ILiturgyRepository litRepo;
	
	@Test
	public void testFindByDate(){
		LocalDateTime date = new LocalDateTime(2017, 1, 1, 0, 0, 0);
		List<LiturgyEntity> entities = litRepo.findByDate(date.toDate());
		Assert.assertNotNull(entities);
		Assert.assertFalse(entities.isEmpty());
		Assert.assertEquals(3, entities.size());
	}
	
	@Test
	public void testFindByDateBetween(){
		LocalDateTime startDate = new LocalDateTime(2017, 1, 1, 0, 0, 0);
		LocalDateTime endDate = startDate.plusYears(1);
		List<LiturgyEntity> entities = litRepo.findByDateBetween(startDate.toDate(), endDate.toDate());
		Assert.assertNotNull(entities);
		Assert.assertFalse(entities.isEmpty());
		Assert.assertEquals(68, entities.size());
	}
	
	@Test
	public void testFindByYear(){
		List<LiturgyEntity> entities = litRepo.findByYear(2017);
		Assert.assertNotNull(entities);
		Assert.assertFalse(entities.isEmpty());
		Assert.assertEquals(71, entities.size());
	}
	
	@Test
	public void testFindByLiturgicalDateAndYear(){
		List<LiturgyEntity> entities = litRepo.findByLiturgicalDateAndYear("Pentecost", 2017);
		Assert.assertNotNull(entities);
		Assert.assertFalse(entities.isEmpty());
		Assert.assertEquals(1, entities.size());
		
		LiturgyEntity entity = entities.get(0);
		LocalDateTime date = new LocalDateTime(entity.date);
		
		Assert.assertEquals(2017, date.getYear());
		Assert.assertEquals(6, date.getMonthOfYear());
		Assert.assertEquals(4, date.getDayOfMonth());
	}
}
