package com.joeldholmes.resources;

import com.joeldholmes.entity.LiturgyEntity;
import com.joeldholmes.liturgycommon.dto.LiturgyDTO;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type="liturgy")
public class LiturgyResource extends LiturgyDTO{
	
	@JsonApiId
	public String id;
	
	public static LiturgyResource convertEntity(LiturgyEntity entity){
		LiturgyResource resource = new LiturgyResource();
		
		resource.id = entity.id;
		resource.date = entity.date;
		resource.litany = entity.litany;
		resource.liturgicalDate = entity.liturgicalDate;
		resource.liturgicalYear = entity.liturgicalYear;
		resource.year = entity.year;
		
		return resource;
	}

}
