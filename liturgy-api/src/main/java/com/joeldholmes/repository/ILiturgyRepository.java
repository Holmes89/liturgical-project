package com.joeldholmes.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.joeldholmes.entity.LiturgyEntity;

public interface ILiturgyRepository extends MongoRepository<LiturgyEntity, String> {

	@Query("{\"_id\": ?0, }")
	LiturgyEntity findById(String id);
	
	List<LiturgyEntity> findByDate(Date date);
	
	Page<LiturgyEntity> findAll(Pageable page);
	
	
}
