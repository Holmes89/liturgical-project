package com.joeldholmes;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import io.katharsis.spring.boot.KatharsisConfigV2;

@Configuration
@Import(KatharsisConfigV2.class)
public class LiturgyApiApplicationConfig {
	
}
