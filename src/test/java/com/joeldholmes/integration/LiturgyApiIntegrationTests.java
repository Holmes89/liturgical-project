package com.joeldholmes.integration;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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
import org.springframework.restdocs.payload.JsonFieldType;
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
    	.accept("application/vnd.api+json;charset=UTF-8")
		.filter(document("resources"))
		.get("/resourcesInfo").then().statusCode(200);
    }
    
    @Test
    public void testLiturgyNoInput(){
    	given(this.spec)
    	.accept("application/vnd.api+json;charset=UTF-8")
		.filter(document("emptyInput"))
		.get("/api/liturgy/").
    	then()
    	.statusCode(200)
    	.body("data", empty());
    }
    
    @Test
    public void testLiturgDate(){
    	given(this.spec)
    	.queryParam("filter[date]", "2017-06-04")
    	.accept("application/vnd.api+json;charset=UTF-8") 
		.filter(document("index", 
				preprocessRequest(
							prettyPrint(), 
							modifyUris().host("liturgy.jholmestech.com").port(8080)), 
				preprocessResponse(prettyPrint()),
				responseFields(
						fieldWithPath("data")
								.type(JsonFieldType.ARRAY) 
								.description("Array of objects returned by service per JSON API standard"),
						fieldWithPath("data[0].type")
								.type(JsonFieldType.STRING) 
								.description("Type of object being returned (JSON API)"),
						fieldWithPath("data[0].id")
								.type(JsonFieldType.STRING) 
								.description("Id of object being returned (JSON API)"),
						fieldWithPath("data[0].attributes")
								.type(JsonFieldType.OBJECT) 
								.description("Attributes of object being returned (JSON API)"),
						fieldWithPath("data[0].attributes.date")
								.type(JsonFieldType.NUMBER) 
								.description("Timestamp of Liturgical Event"),
						fieldWithPath("data[0].attributes.liturgicalYear")
								.type(JsonFieldType.STRING) 
								.description("Letter of year for Liturgy"),
						fieldWithPath("data[0].attributes.year")
								.type(JsonFieldType.NUMBER) 
								.description("Year of event"),
						fieldWithPath("data[0].attributes.liturgicalDate")
								.type(JsonFieldType.STRING) 
								.description("Event name"),
						fieldWithPath("data[0].attributes.litany")
								.type(JsonFieldType.ARRAY) 
								.description("Array of Strings denoting verses to be read for this event"),
						fieldWithPath("data[0].relationships")
								.type(JsonFieldType.OBJECT) 
								.description("Linked Resources (none) (JSON API)"),
						fieldWithPath("data[0].links")
								.type(JsonFieldType.OBJECT) 
								.description("Links to resources"),
						fieldWithPath("data[0].links.self")
								.type(JsonFieldType.STRING) 
								.description("URL to get this object"),
						fieldWithPath("included")
								.type(JsonFieldType.ARRAY) 
								.description("Included objects")
					)
				)) 
		.get("/api/liturgy/")
    	.then()
    	.statusCode(200)
    	.body("data", hasSize(1))
    	.body("data[0].attributes.liturgicalDate", equalTo("Pentecost"));
    }
    
    @Test
    public void testLiturgApproxDate(){
    	given(this.spec)
    	.queryParam("filter[approxDate]", "2017-06-03")
    	.accept("application/vnd.api+json;charset=UTF-8") 
		.filter(document("approxDate"))
		.get("/api/liturgy/").
    	then()
    	.statusCode(200)
    	.body("data", hasSize(1))
    	.body("data[0].attributes.liturgicalDate", equalTo("Pentecost"));
    }
    
    @Test
    public void testLitugDateRange(){
    	given(this.spec)
    	.queryParam("filter[startDate]", "2017-01-01")
    	.queryParam("filter[endDate]", "2018-01-01")
    	.accept("application/vnd.api+json;charset=UTF-8") 
		.filter(document("getRange"))
		.get("/api/liturgy/").
    	then()
    	.statusCode(200)
    	.body("data", hasSize(68));
    }
    
    @Test
    public void testLiturgHolidayAndYear(){ 	
    	given(this.spec)
    	.queryParam("filter[holiday]", "Christmas Day")
    	.queryParam("filter[year]", "2018")
    	.accept("application/vnd.api+json;charset=UTF-8") 
		.filter(document("getHolidayAndYear"))
		.get("/api/liturgy/")
    	.then()
    	.statusCode(200)
    	.body("data", hasSize(1))
    	.body("data[0].attributes.year", equalTo(2018));
    }
    
    @Test
    public void testLitugYear(){
    	given(this.spec)
    	.queryParam("filter[year]", "2017")
    	.accept("application/vnd.api+json;charset=UTF-8") 
		.filter(document("getYear"))
		.get("/api/liturgy/")
    	.then()
    	.statusCode(200)
    	.body("data", hasSize(71));
    }
    
    @Test
    public void testLiturgHoliday(){
    	int year = (new DateTime()).getYear();
    	
    	given(this.spec)
    	.queryParam("filter[holiday]", "Christmas Day")
    	.accept("application/vnd.api+json;charset=UTF-8") 
		.filter(document("getHoliday"))
		.get("/api/liturgy/")
    	.then()
    	.statusCode(200)
    	.body("data", hasSize(1))
    	.body("data[0].attributes.year", equalTo(year));
    }
    
    @Test
    public void testDate_error(){
    	given(this.spec)
    	.queryParam("filter[date]", "asdfasdf")
    	.accept("application/vnd.api+json;charset=UTF-8") 
		.filter(document("error"))
    	.then()
    	.statusCode(500);
    }
    
}
