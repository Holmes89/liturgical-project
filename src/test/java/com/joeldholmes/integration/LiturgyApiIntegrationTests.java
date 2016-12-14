package com.joeldholmes.integration;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class LiturgyApiIntegrationTests {
	
	@LocalServerPort
	int port;

    @Before
    public final void before() {

    	RestAssured.port = port;
//    	RestAssured.baseURI = "http://localhost";
    	RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
    
    @Test
    public void testConnection(){
    	get("/resourcesInfo").then().statusCode(200);
    }
    
    @Test
    public void testLiturgyNoInput(){
    	get("/api/liturgy/").
    	then()
    	.statusCode(200)
    	.body("data", empty());
    }
    
    @Test
    public void testLiturgDate(){
    	get("/api/liturgy/?filter[date]=2017-06-04").
    	then()
    	.statusCode(200)
    	.body("data", hasSize(1));
    }
}
