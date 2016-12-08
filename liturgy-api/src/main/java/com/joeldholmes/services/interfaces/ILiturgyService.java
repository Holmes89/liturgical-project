package com.joeldholmes.services.interfaces;

import java.util.List;
import java.util.Set;

import com.joeldholmes.resources.LiturgyResource;

public interface ILiturgyService {

	List<LiturgyResource> findByDate(String dateString);

	List<LiturgyResource> findByApproximateDate(String dateString);

	List<LiturgyResource> findByDateRange(String startDateString, String endDateString);

	List<LiturgyResource> findByHoliday(String holiday, String year);

	List<LiturgyResource> findByYear(String year);

	List<LiturgyResource> findByHoliday(String holiday);

}
