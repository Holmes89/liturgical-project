package com.joeldholmes.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class LiturgyApiIntegrationTests {
	
	@Value("${local.server.port}")
    protected int port;

    @Before
    public final void before() {
    	RestAssured.port = port;
    }
    
    @Test
    public void testLiturgyNoInput(){
    	get("/api/liturgy/").
    	then().body("data", empty());
    }
    
    @Test
    public void testLiturgDate(){
    	get("/api/liturgy/?filter[date]=2017-06-04").
    	then().body("data", hasSize(1));
    }
}
