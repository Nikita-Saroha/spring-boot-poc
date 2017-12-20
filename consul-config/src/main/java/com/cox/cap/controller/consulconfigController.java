package com.cox.cap.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cox.cap.model.Names;
import com.cox.cap.service.consulconfigService;

@RequestMapping("/v1")
@RestController
@Api(value="namestore", description="Operations pertaining to list of names stored in database.")
@RefreshScope
public class consulconfigController{
	
	@Autowired
	consulconfigService consulconfigService;
	
	@Value("${test}")
	String test;
	
	@RequestMapping(value = "/names", method = RequestMethod.GET, produces = "application/json")
	@ApiOperation(value = "View a list of available names.",response = List.class)
	public List<Names> getNames(){
		System.out.println(test);
		return consulconfigService.getNames();
	}
	
	@PostMapping(value = "/names", consumes = "application/json")
	@ApiOperation(value = "Add a new name with an ID.")
	public void setName(@RequestBody Names names){
		consulconfigService.addName(names);
	}
	
	@GetMapping("/names/{id}")
	@ApiOperation(value = "Search a name with an ID.",response = Names.class)
	public Names getNameById(@PathVariable Integer id){
		return consulconfigService.getNameById(id);
	}
	
	@DeleteMapping("/names/{id}")
	@ApiOperation(value = "Delete a name from database.")
	public void deleteNameById(@PathVariable Integer id){
		consulconfigService.deleteName(id);
	}
	
	@DeleteMapping("/cache/remove/all")
	@ApiOperation(value = "Clear cache.")
	public void clearAllCache(){
		consulconfigService.clearAllCache();
	}
	
	@DeleteMapping("/cache/remove/{id}")
	@ApiOperation(value = "Remove data for ID from cache.")
	public void clearCache(@PathVariable Integer id){
		consulconfigService.clearCache(id);
	}
	
}