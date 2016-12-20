package com.joeldholmes.integration;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.restassured.operation.preprocess.RestAssuredPreprocessors.*;


import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;

import com.jayway.restassured.response.*;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.specification.RequestSpecification;

import io.katharsis.resource.registry.ResourceRegistry;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class LiturgyApiIntegrationTests {
	
	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");
	
	@LocalServerPort
	int port;
	
	@Autowired
	ResourceRegistry registry;
	
	private RequestSpecification spec;

    @Before
    public final void before() {
    	RestAssured.port = port;
    	this.spec = new RequestSpecBuilder()
				.addFilter(documentationConfiguration(restDocumentation)).build();
    }

    
    @Test
    public void testConnection(){
    	given(this.spec)
   // 	.accept("application/json") 
		.filter(document("resources"))
		.get("/resourcesInfo").then().statusCode(200);
    }
    
    @Test
    public void testLiturgyNoInput(){
    	given(this.spec)
  //  	.accept("application/json") 
		.filter(document("emptyInput"))
		.get("/api/liturgy/").
    	then()
    	.statusCode(200)
    	.body("data", empty());
    }
    
    @Test
    public void testLiturgDate(){
    	given(this.spec)
  //  	.accept("application/json") 
		.filter(document("index", preprocessRequest(prettyPrint(), modifyUris().host("liturgy.jholmestech.com").port(8080)), 
				preprocessResponse(prettyPrint()))) 
		.get("/api/liturgy/?filter[date]=2017-06-04").
    	then()
    	.statusCode(200)
    	.body("data", hasSize(1))
    	.body("data[0].attributes.liturgicalDate", equalTo("Pentecost"));
    }
    
    @Test
    public void testLiturgApproxDate(){
    	given(this.spec)
   // 	.accept("application/json") 
		.filter(document("approxDate"))
		.get("/api/liturgy/?filter[approxDate]=2017-06-03").
    	then()
    	.statusCode(200)
    	.body("data", hasSize(1))
    	.body("data[0].attributes.liturgicalDate", equalTo("Pentecost"));
    }
    
    @Test
    public void testLitugDateRange(){
    	given(this.spec)
  //  	.accept("application/json") 
		.filter(document("getRange"))
		.get("/api/liturgy/?filter[startDate]=2017-01-01&filter[endDate]=2018-01-01").
    	then()
    	.statusCode(200)
    	.body("data", hasSize(68));
    }
    
    @Test
    public void testLiturgHolidayAndYear(){ 	
    	given(this.spec)
   // 	.accept("application/json") 
		.filter(document("getHolidayAndYear"))
		.get("/api/liturgy/?filter[holiday]=Christmas Day&filter[year]=2018")
    	.then()
    	.statusCode(200)
    	.body("data", hasSize(1))
    	.body("data[0].attributes.year", equalTo(2018));
    }
    
    @Test
    public void testLitugYear(){
    	given(this.spec)
   // 	.accept("application/json") 
		.filter(document("getYear"))
		.get("/api/liturgy/?filter[year]=2017")
    	.then()
    	.statusCode(200)
    	.body("data", hasSize(71));
    }
    
    @Test
    public void testLiturgHoliday(){
    	int year = (new DateTime()).getYear();
    	
    	given(this.spec)
    //	.accept("application/json") 
		.filter(document("getHoliday"))
		.get("/api/liturgy/?filter[holiday]=Christmas Day")
    	.then()
    	.statusCode(200)
    	.body("data", hasSize(1))
    	.body("data[0].attributes.year", equalTo(year));
    }
    
    @Test
    public void testDate_error(){
    	given(this.spec)
    //	.accept("application/json") 
		.filter(document("error"))
		.get("/api/liturgy/?filter[date]=asdfasdf")
    	.then()
    	.statusCode(500);
    }
    
}
