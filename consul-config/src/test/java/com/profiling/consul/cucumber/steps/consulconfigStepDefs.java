package com.profiling.consul.cucumber.steps;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.profiling.consul.config.consulconfigSpringIntegrationTest;
import com.profiling.consul.controller.consulconfigController;
import com.profiling.consul.model.Names;
import com.profiling.consul.service.consulconfigService;

import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class consulconfigStepDefs extends consulconfigSpringIntegrationTest {
	
	@Mock
	consulconfigService service;
	
	@InjectMocks
	consulconfigController controller;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@When("the client calls /names$")
	public void getNamesWhenTest(){
		getAllNames();
		when(service.getNames()).thenReturn(mockNameList);
	}
	
	@Then("the client receives status code of (\\d+)$")
	public void getNamesThenTest(int statusCode) throws Exception{
		mockMvc.perform(get("/v1/names"))
			.andExpect(status().is(statusCode));
	}
	
	@And("the client receives list of names with (\\d+) elements$")
	public void getNamesAndTest(int noOfElements) throws Exception{
		mockMvc.perform(get("/v1/names"))
		.andExpect(jsonPath("$",hasSize(noOfElements)))
		.andExpect(jsonPath("$[0].id",is(1)))
		.andExpect(jsonPath("$[0].name",is("Cognizant")));
	}
	
	@When("the client calls /names with id (\\d+) and name (.+)$")
	public void setNamesWhenTest(int id, String name){
		setNames(id, name);
		Mockito.doNothing().when(service).addName(new Names());
	}
	
	@Then("the client receives status code of (\\d+) on POST$")
	public void setNamesThenTest(int statusCode) throws Exception{
		mockMvc.perform(post("/v1/names")
						.content("{\"id\":2,\"name\":\"Dummy\"}")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is(statusCode));
	}
	
	@And("GET call returns (\\d+) elements with 3rd id (\\d+) and name (.+)$")
	public void setNamesAndTest(int noOfElements, int id, String name) throws Exception{
		when(service.getNames()).thenReturn(mockNameList);
		mockMvc.perform(get("/v1/names"))
		.andExpect(jsonPath("$",hasSize(noOfElements)))
		.andExpect(jsonPath("$[2].id",is(id)))
		.andExpect(jsonPath("$[2].name",is(name)));
	}
}
