package com.cox.cap.cucumber.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.cox.cap.consulconfig;
import com.cox.cap.model.Names;

@ContextConfiguration(classes = consulconfig.class)
@WebAppConfiguration
@SpringBootTest
public class consulconfigSpringIntegrationTest {
	
	public static List<Names> mockNameList;
	protected MockMvc mockMvc;
	
	public void getAllNames(){
		if(mockNameList == null)
			mockNameList = new ArrayList<Names>();
		Names name = new Names();
		name.setId(1);
		name.setName("Cognizant");
		mockNameList.add(name);
		name = new Names();
		name.setId(2683);
		name.setName("Xavient");
		mockNameList.add(name);
	}
	
	public void setNames(int id, String name){
		if(mockNameList == null)
			mockNameList = new ArrayList<Names>();
		Names names = new Names();
		names.setId(id);
		names.setName(name);
		mockNameList.add(names);
	}
}
