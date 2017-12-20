package com.cox.cap.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.cox.cap.exception.BaseExceptionHandler;
import com.cox.cap.exception.ServiceException;
import com.cox.cap.exception.SystemException;

@ControllerAdvice
public class consulconfigException extends BaseExceptionHandler{
	
	private static final Logger logger = LoggerFactory.getLogger(consulconfigException.class);
	
	public consulconfigException(){
		super(logger);
		addMapping(ServiceException.class, "SERVICE_ERROR", HttpStatus.BAD_REQUEST);
        addMapping(SystemException.class, "SYSTEM_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
