package com.profiling.consul.service;

import java.util.List;

import com.profiling.consul.model.Names;

public interface consulconfigService{
	
	List<Names> getNames();
	
	void addName(Names names);
	
	Names getNameById(int id);
	
	void deleteName(int id);
	
	void clearAllCache();

	void clearCache(Integer id);
	
}