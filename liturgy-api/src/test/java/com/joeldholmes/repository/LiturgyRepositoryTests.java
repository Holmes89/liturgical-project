package com.joeldholmes.repository;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joeldholmes.entity.LiturgyEntity;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LiturgyRepositoryTests {

	@Autowired
	ILiturgyRepository litRepo;
	
	@Test
	public void testFindByDate(){
		DateTime date = new DateTime(2017, 1, 1, 0, 0, 0);
		List<LiturgyEntity> entities = litRepo.findByDate(date.toDate());
		Assert.assertNotNull(entities);
		Assert.assertFalse(entities.isEmpty());
		Assert.assertEquals(3, entities.size());
	}
}
