package com.profiling.consul.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cox.cap.commons.context.holder.ContextHolder;
import com.cox.cap.commons.logging.LogEntry;
import com.cox.cap.commons.util.CommonConstants.C_Properties;
import com.profiling.consul.dao.consulconfigDao;
import com.profiling.consul.model.Names;
import com.profiling.consul.service.consulconfigService;
import com.profiling.consul.util.consulconfigUtil;


@CacheConfig(cacheNames = "consulconfigCache")
@Service("consulconfigServiceImpl")
public class consulconfigServiceImpl implements consulconfigService{
	
	@Autowired
	consulconfigDao consulconfigDao;
	
	public List<Names> getNames(){
		LogEntry logEntry = (LogEntry) ContextHolder.getKeeper().getProperty(C_Properties.LOG_ENTRY_KEY);
		if(logEntry != null){
			consulconfigUtil.addMessageToLog(logEntry, "Getting all the names from Database.");
		}
		List<Names> namesList = consulconfigDao.findAll();
		if(logEntry != null){
			consulconfigUtil.addMessageToLog(logEntry, "Returning response.");
		}
		return namesList;
	}
	
	public void addName(Names names) {
		LogEntry logEntry = (LogEntry) ContextHolder.getKeeper().getProperty(C_Properties.LOG_ENTRY_KEY);
		if(logEntry != null){
			consulconfigUtil.addMessageToLog(logEntry, "Adding new names to database - "+names.getName());
		}
		consulconfigDao.save(names);
	} 
	
	@Cacheable(key="#id")
	public Names getNameById(int id) {
		LogEntry logEntry = (LogEntry) ContextHolder.getKeeper().getProperty(C_Properties.LOG_ENTRY_KEY);
		if(logEntry != null){
			consulconfigUtil.addMessageToLog(logEntry, "Looking for name with ID - "+id);
		}
		return consulconfigDao.findOne(id);
	}
	
	@CacheEvict(key = "#id")
	public void deleteName(int id) {
		LogEntry logEntry = (LogEntry) ContextHolder.getKeeper().getProperty(C_Properties.LOG_ENTRY_KEY);
		if(logEntry != null){
			consulconfigUtil.addMessageToLog(logEntry, "Deleting name with ID - "+id);
		}
		consulconfigDao.delete(id);
	}
	
	@CacheEvict(allEntries=true)
	public void clearAllCache() {
		LogEntry logEntry = (LogEntry) ContextHolder.getKeeper().getProperty(C_Properties.LOG_ENTRY_KEY);
		if(logEntry != null){
			consulconfigUtil.addMessageToLog(logEntry, "Clearing cache.");
		}
	}

	@CacheEvict(key = "#id")
	public void clearCache(Integer id) {
		LogEntry logEntry = (LogEntry) ContextHolder.getKeeper().getProperty(C_Properties.LOG_ENTRY_KEY);
		if(logEntry != null){
			consulconfigUtil.addMessageToLog(logEntry, "Removing id = "+id+" from cache.");
		}
	}
	
}