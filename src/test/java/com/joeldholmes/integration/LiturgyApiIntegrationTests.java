package com.joeldholmes.integration;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import io.katharsis.resource.registry.ResourceRegistry;
import io.restassured.RestAssured;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class LiturgyApiIntegrationTests {
	
	@LocalServerPort
	int port;
	
	@Autowired
	ResourceRegistry registry;

    @Before
    public final void before() {

    	RestAssured.port = port;
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
    	.body("data", hasSize(1))
    	.body("data[0].attributes.liturgicalDate", equalTo("Pentecost"));
    }
    
    @Test
    public void testLiturgApproxDate(){
    	get("/api/liturgy/?filter[approxDate]=2017-06-03").
    	then()
    	.statusCode(200)
    	.body("data", hasSize(1))
    	.body("data[0].attributes.liturgicalDate", equalTo("Pentecost"));
    }
    
    @Test
    public void testLitugDateRange(){
    	get("/api/liturgy/?filter[startDate]=2017-01-01&filter[endDate]=2018-01-01").
    	then()
    	.statusCode(200)
    	.body("data", hasSize(68));
    }
    
    @Test
    public void testLiturgHolidayAndYear(){ 	
    	get("/api/liturgy/?filter[holiday]=Christmas Day&filter[year]=2018")
    	.then()
    	.statusCode(200)
    	.body("data", hasSize(1))
    	.body("data[0].attributes.year", equalTo(2018));
    }
    
    @Test
    public void testLitugYear(){
    	get("/api/liturgy/?filter[year]=2017")
    	.then()
    	.statusCode(200)
    	.body("data", hasSize(71));
    }
    
    @Test
    public void testLiturgHoliday(){
    	int year = (new DateTime()).getYear();
    	
    	get("/api/liturgy/?filter[holiday]=Christmas Day")
    	.then()
    	.statusCode(200)
    	.body("data", hasSize(1))
    	.body("data[0].attributes.year", equalTo(year));
    }
    
    
}
