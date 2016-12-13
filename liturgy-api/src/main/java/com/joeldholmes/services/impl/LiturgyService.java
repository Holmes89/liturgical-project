package com.joeldholmes.services.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joeldholmes.entity.LiturgyEntity;
import com.joeldholmes.liturgycommon.exceptions.ServiceException;
import com.joeldholmes.liturgycommon.util.ErrorCodes;
import com.joeldholmes.repository.ILiturgyRepository;
import com.joeldholmes.resources.LiturgyResource;
import com.joeldholmes.services.interfaces.ILiturgyService;

@Service
public class LiturgyService implements ILiturgyService{

	@Autowired
	ILiturgyRepository lectRepo;

	private final String DATE_FORMAT = "yyyy-MM-dd";
	private SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
	
	@Override
	public List<LiturgyResource> findByDate(String dateString) throws ServiceException {
		if(dateString==null || dateString.isEmpty()){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Date String must not be null or empty");
		}
		Date date = convertDate(dateString);
		return convertEntities(lectRepo.findByDate(date));
	}


	@Override
	public List<LiturgyResource> findByApproximateDate(String dateString) throws ServiceException  {
		if(dateString==null || dateString.isEmpty()){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Date String must not be null or empty");
		}
		DateTime date = new DateTime(convertDate(dateString));
		List<LiturgyEntity> entities = lectRepo.findByDateBetween(date.toDate(), date.plusWeeks(1).toDate());
		List<LiturgyResource> returnValues = new ArrayList<LiturgyResource>();
		if(!entities.isEmpty()){
			returnValues = convertEntities(Collections.singletonList(entities.get(0)));
		}
		return returnValues;
	}


	@Override
	public List<LiturgyResource> findByDateRange(String startDateString, String endDateString) throws ServiceException {
		if(startDateString==null || startDateString.isEmpty()){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Start Date String must not be null or empty");
		}
		if(endDateString==null || endDateString.isEmpty()){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "End Date String must not be null or empty");
		}
		DateTime startDate = new DateTime(convertDate(startDateString));
		DateTime endDate = new DateTime(convertDate(endDateString));
		List<LiturgyEntity> entities = lectRepo.findByDateBetween(startDate.toDate(), endDate.toDate());
		return convertEntities(entities);
	}


	@Override
	public List<LiturgyResource> findByHoliday(String holiday, String year)  throws ServiceException{
		if(holiday==null || holiday.isEmpty()){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Holiday String must not be null or empty");
		}
		if(year==null || year.isEmpty()){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Year String must not be null or empty");
		}
		int convertedYear = convertYear(year);
		return findByHoliday(holiday, convertedYear);
	}
	
	@Override
	public List<LiturgyResource> findByHoliday(String holiday, int year)  throws ServiceException{
		List<LiturgyEntity> entities = lectRepo.findByLiturgicalDateAndYear(holiday, year);
		return convertEntities(entities);
	}


	@Override
	public List<LiturgyResource> findByYear(String year) throws ServiceException {
		if(year==null || year.isEmpty()){
			throw new ServiceException(ErrorCodes.NULL_INPUT, "Year String must not be null or empty");
		}
		int convertedYear = convertYear(year);
		List<LiturgyEntity> entities = lectRepo.findByYear(convertedYear);
		return convertEntities(entities);
	}


	@Override
	public List<LiturgyResource> findByHoliday(String holiday) throws ServiceException  {
		DateTime date = new DateTime();
		int year = date.getYear();
		return findByHoliday(holiday, year);
	}
	
	private Date convertDate(String dateString) throws ServiceException{
		try {
			Date date = dateFormatter.parse(dateString);
			return date;
		} catch (ParseException e) {
			throw new ServiceException(ErrorCodes.INVALID_INPUT, "Date must be formatted "+DATE_FORMAT, e);
		}
	}
	
	private Integer convertYear(String year) throws ServiceException{
		try{
			return Integer.parseInt(year);
		}catch(NumberFormatException e){
			throw new ServiceException(ErrorCodes.INVALID_INPUT, "Year must be integer", e);
		}
	}

	private List<LiturgyResource> convertEntities(List<LiturgyEntity> entities){
		List<LiturgyResource> resources = new ArrayList<LiturgyResource>();
		for(LiturgyEntity e: entities){
			resources.add(LiturgyResource.convertEntity(e));
		}
		return resources;
	}
}
