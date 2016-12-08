package com.joeldholmes.liturgicalcalendar.services.interfaces;

import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.joeldholmes.liturgycommon.dto.LiturgyDTO;
import com.joeldholmes.liturgycommon.exceptions.ServiceException;

public interface ILectionaryService {

	Set<String> getLectionaryVerses(DateTime now) throws ServiceException;

	Map<String, LiturgyDTO> getLectionary(DateTime now) throws ServiceException;

}
