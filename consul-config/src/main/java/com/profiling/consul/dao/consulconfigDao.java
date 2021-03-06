package com.profiling.consul.dao;

import java.util.List;

import com.profiling.consul.model.Names;

public interface consulconfigDao {
	
	public List<Names> findAll();
	
	public Names save(Names name);	
	
	public Names findOne(Integer id);
	
	public void delete(Integer id);
}