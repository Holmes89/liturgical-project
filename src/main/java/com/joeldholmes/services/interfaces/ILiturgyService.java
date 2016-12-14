package com.joeldholmes.services.interfaces;

import java.util.List;
import java.util.Set;

import com.joeldholmes.liturgycommon.exceptions.ServiceException;
import com.joeldholmes.resources.LiturgyResource;

public interface ILiturgyService {

	List<LiturgyResource> findByDate(String dateString) throws ServiceException;

	List<LiturgyResource> findByApproximateDate(String dateString) throws ServiceException;

	List<LiturgyResource> findByDateRange(String startDateString, String endDateString)throws ServiceException;

	List<LiturgyResource> findByHoliday(String holiday, String year) throws ServiceException;

	List<LiturgyResource> findByYear(String year) throws ServiceException;

	List<LiturgyResource> findByHoliday(String holiday) throws ServiceException;

	List<LiturgyResource> findByHoliday(String holiday, int year) throws ServiceException;

}
