package com.joeldholmes.service;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.joeldholmes.exceptions.ServiceException;
import com.joeldholmes.resources.LiturgyResource;
import com.joeldholmes.services.impl.LiturgyService;
import com.joeldholmes.utils.ErrorCodes;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LiturgyServiceTests {
	
	@Autowired
	LiturgyService litService;
	
	@Test
	public void testFindByDate() throws Exception{
		List<LiturgyResource> resources = litService.findByDate("2017-06-04");
		Assert.assertFalse(resources.isEmpty());
		Assert.assertEquals(1, resources.size());
		
		LiturgyResource resource = resources.get(0);
		DateTime date = new DateTime(resource.date);
		
		Assert.assertEquals('a', resource.liturgicalYear);
		Assert.assertEquals(2017, date.getYear());
		Assert.assertEquals(6, date.getMonthOfYear());
		Assert.assertEquals(4, date.getDayOfMonth());
		Assert.assertEquals("Pentecost", resource.liturgicalDate);
		
	}
	
	@Test(expected=ServiceException.class)
	public void testFindByDate_nullDate() throws Exception{
		try{
			litService.findByDate(null);
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.NULL_INPUT, e.getErrorCode());
			throw e;
		}
	}
	
	@Test(expected=ServiceException.class)
	public void testFindByDate_emptyDate() throws Exception{
		try{
			litService.findByDate("");
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.NULL_INPUT, e.getErrorCode());
			throw e;
		}
	}
	
	@Test(expected=ServiceException.class)
	public void testFindByDate_invalidDate() throws Exception{
		try{
			litService.findByDate("asdfasdf");
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.INVALID_INPUT, e.getErrorCode());
			throw e;
		}
	}
	
	@Test
	public void testFindByApproximateDate() throws Exception{
		List<LiturgyResource> resources = litService.findByApproximateDate("2017-06-03");
		Assert.assertFalse(resources.isEmpty());
		Assert.assertEquals(1, resources.size());
		
		LiturgyResource resource = resources.get(0);
		DateTime date = new DateTime(resource.date);
		
		Assert.assertEquals('a', resource.liturgicalYear);
		Assert.assertEquals(2017, date.getYear());
		Assert.assertEquals(6, date.getMonthOfYear());
		Assert.assertEquals(4, date.getDayOfMonth());
		Assert.assertEquals("Pentecost", resource.liturgicalDate);
	}
	
	@Test(expected=ServiceException.class)
	public void testFindByApproximateDate_nullDate() throws Exception{
		try{
			litService.findByApproximateDate(null);
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.NULL_INPUT, e.getErrorCode());
			throw e;
		}
	}
	
	@Test(expected=ServiceException.class)
	public void testFindByApproximateDate_emptyDate() throws Exception{
		try{
			litService.findByApproximateDate("");
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.NULL_INPUT, e.getErrorCode());
			throw e;
		}
	}
	
	@Test(expected=ServiceException.class)
	public void testFindByApproximateDate_invalidDate() throws Exception{
		try{
			litService.findByApproximateDate("asdfasdf");
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.INVALID_INPUT, e.getErrorCode());
			throw e;
		}
	}

	@Test
	public void testFindByDateRange() throws Exception{
		List<LiturgyResource> resources = litService.findByDateRange("2017-01-01", "2018-01-01");
		Assert.assertFalse(resources.isEmpty());
		Assert.assertEquals(68, resources.size());
	}
	
	@Test(expected=ServiceException.class)
	public void testFindByDateRange_nullDate() throws Exception{
		try{
			litService.findByDateRange(null, "2018-01-01");
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.NULL_INPUT, e.getErrorCode());
		}
		try{
			litService.findByDateRange("2018-01-01", null);
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.NULL_INPUT, e.getErrorCode());
			throw e;
		}
	}
	
	@Test(expected=ServiceException.class)
	public void testFindByDateRange_emptyDate() throws Exception{
		try{
			litService.findByDateRange("", "2018-01-01");
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.NULL_INPUT, e.getErrorCode());
		}
		try{
			litService.findByDateRange("2018-01-01", "");
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.NULL_INPUT, e.getErrorCode());
			throw e;
		}
	}
	
	@Test(expected=ServiceException.class)
	public void testFindByDateRange_invalidDate() throws Exception{
		try{
			litService.findByDateRange("asdfasd", "2018-01-01");
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.INVALID_INPUT, e.getErrorCode());
		}
		try{
			litService.findByDateRange("2018-01-01", "Asdfasdf");
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.INVALID_INPUT, e.getErrorCode());
			throw e;
		}
	}

	@Test
	public void testFindByHoliday_String() throws Exception{
		DateTime currentDate = new DateTime();
		
		List<LiturgyResource> resources = litService.findByHoliday("Pentecost");
		Assert.assertFalse(resources.isEmpty());
		Assert.assertEquals(1, resources.size());
		
		LiturgyResource resource = resources.get(0);
		DateTime date = new DateTime(resource.date);
		
		Assert.assertEquals(currentDate.getYear(), date.getYear());
	}
	@Test
	public void testFindByHoliday_invalidName() throws Exception{
		List<LiturgyResource> resources = litService.findByHoliday("asdfasdf");
		Assert.assertTrue(resources.isEmpty());
	}

	@Test(expected=ServiceException.class)
	public void testFindByHoliday_nullName() throws Exception{
		try{
			litService.findByHoliday(null);
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.NULL_INPUT, e.getErrorCode());
			throw e;
		}
	}
	
	@Test(expected=ServiceException.class)
	public void testFindByHoliday_emptyName() throws Exception{
		try{
			litService.findByHoliday("");
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.NULL_INPUT, e.getErrorCode());
			throw e;
		}
	}
	
	@Test
	public void testFindByYear() throws Exception{
		List<LiturgyResource> resources = litService.findByYear("2017");
		Assert.assertFalse(resources.isEmpty());
		Assert.assertEquals(71, resources.size());
	}
	
	@Test(expected=ServiceException.class)
	public void testFindByHoliday_nullYear() throws Exception{
		try{
			litService.findByYear(null);
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.NULL_INPUT, e.getErrorCode());
			throw e;
		}
	}
	
	@Test(expected=ServiceException.class)
	public void testFindByHoliday_emptyYear() throws Exception{
		try{
			litService.findByYear("");
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.NULL_INPUT, e.getErrorCode());
			throw e;
		}
	}
	
	@Test(expected=ServiceException.class)
	public void testFindByHoliday_InvalidYear() throws Exception{
		try{
			litService.findByYear("fasdfasdf");
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.INVALID_INPUT, e.getErrorCode());
			throw e;
		}
	}
	
	@Test
	public void testFindByHoliday_StringAndString() throws Exception{
		List<LiturgyResource> resources = litService.findByHoliday("Pentecost", "2017");
		Assert.assertFalse(resources.isEmpty());
		Assert.assertEquals(1, resources.size());
		
		LiturgyResource resource = resources.get(0);
		DateTime date = new DateTime(resource.date);
		
		Assert.assertEquals(2017, date.getYear());
		Assert.assertEquals(6, date.getMonthOfYear());
		Assert.assertEquals(4, date.getDayOfMonth());
		Assert.assertEquals("Pentecost", resource.liturgicalDate);
		Assert.assertEquals(6, resource.litany.size());
	}
	
	@Test(expected=ServiceException.class)
	public void testFindByHoliday_StringAndString_nulls() throws Exception{
		String year = null;
		try{
			litService.findByHoliday("Pentecost", year);
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.NULL_INPUT, e.getErrorCode());
		}
		try{
			litService.findByHoliday(null, "2016");
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.NULL_INPUT, e.getErrorCode());
			throw e;
		}
	}
	
	@Test(expected=ServiceException.class)
	public void testFindByHoliday_StringAndString_empties() throws Exception{
		try{
			litService.findByHoliday("", "2016");
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.NULL_INPUT, e.getErrorCode());
		}
		try{
			litService.findByHoliday("Pentecost", "");
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.NULL_INPUT, e.getErrorCode());
			throw e;
		}
	}
	
	@Test(expected=ServiceException.class)
	public void testFindByHoliday_StringAndString_invalids() throws Exception{
		try{
			litService.findByHoliday("Pentecost", "asdfasdf");
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.INVALID_INPUT, e.getErrorCode());
			throw e;
		}
	}

	@Test
	public void testFindByHoliday_StringAndInt() throws Exception{
		List<LiturgyResource> resources = litService.findByHoliday("Pentecost", 2017);
		Assert.assertFalse(resources.isEmpty());
		Assert.assertEquals(1, resources.size());
		
		LiturgyResource resource = resources.get(0);
		DateTime date = new DateTime(resource.date);
		
		Assert.assertEquals(2017, date.getYear());
		Assert.assertEquals(6, date.getMonthOfYear());
		Assert.assertEquals(4, date.getDayOfMonth());
		Assert.assertEquals("Pentecost", resource.liturgicalDate);
		Assert.assertEquals(6, resource.litany.size());
	}
	
	@Test(expected=ServiceException.class)
	public void testFindByHoliday_StringAndInt_nulls() throws Exception{
		try{
			litService.findByHoliday(null, 2016);
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.NULL_INPUT, e.getErrorCode());
			throw e;
		}
	}
	
	@Test(expected=ServiceException.class)
	public void testFindByHoliday_StringAndInt_empties() throws Exception{
		try{
			litService.findByHoliday("", 2016);
		} catch(ServiceException e){
			Assert.assertEquals(ErrorCodes.NULL_INPUT, e.getErrorCode());
			throw e;
		}
	}

}
