package com.joeldholmes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.joeldholmes.entity.LiturgyEntity;

public interface ILiturgyRepository extends MongoRepository<LiturgyEntity, String> {

	@Query("{\"_id\": ?0, }")
	LiturgyEntity findById(String id);
	
	@Query("{\"date\": ?0, }")
	LiturgyEntity findByDate(Long timestamp);
	
	Page<LiturgyEntity> findAll(Pageable page);
}
