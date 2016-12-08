package com.joeldholmes.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.joeldholmes.entity.LiturgyEntity;
import com.joeldholmes.repository.ILiturgyRepository;
import com.joeldholmes.resources.LiturgyResource;
import com.joeldholmes.services.interfaces.ILiturgyService;

public class LiturgyService implements ILiturgyService{

	@Autowired
	ILiturgyRepository lectRepo;

	
	private List<LiturgyResource> convertEntities(List<LiturgyEntity> entities){
		List<LiturgyResource> resources = new ArrayList<LiturgyResource>();
		for(LiturgyEntity e: entities){
			resources.add(LiturgyResource.convertEntity(e));
		}
		return resources;
	}
}
