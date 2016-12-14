package com.joeldholmes.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.joeldholmes.dto.LiturgyDTO;

@Document(collection="liturgyEntries")
public class LiturgyEntity extends LiturgyDTO{

	@Id
	public String id;
}
