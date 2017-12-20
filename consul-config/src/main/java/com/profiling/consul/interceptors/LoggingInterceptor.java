package com.profiling.consul.interceptors;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cox.cap.commons.context.holder.ContextHolder;
import com.cox.cap.commons.context.holder.Keeper;
import com.cox.cap.commons.logging.LogEntry;
import com.cox.cap.commons.logging.LoggerUtil;
import com.cox.cap.commons.logging.LoggerUtil.ServiceCategory;
import com.cox.cap.commons.util.CommonConstants.C_HeaderFields;
import com.cox.cap.commons.util.CommonConstants.C_Properties;

@Component
public class LoggingInterceptor extends HandlerInterceptorAdapter {
	
	@Value("${service.name}")
	String servicename;
	
	@Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception)
    throws Exception {
		Keeper keeper = ContextHolder.getKeeper();
		LogEntry logEntry = (LogEntry)keeper.getProperty(C_Properties.LOG_ENTRY_KEY);
 		logEntry.setLogger(LoggerFactory.getLogger(((HandlerMethod)handler).getBeanType()));
 		postProcessor(keeper);
 		ContextHolder.cleanup();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
    throws Exception {
    	
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	Keeper keeper = new Keeper();
    	keeper.setProperty(Keeper.REQUEST_URI , request.getRequestURI());
    	keeper.setProperty(Keeper.CREATED_TIMESTAMP, new Date());
    	preProcessor(keeper);
    	ContextHolder.putKeeper(keeper);
        return true;
    }
    
    private void preProcessor(Keeper keeper) {
		LogEntry logEntry = (keeper.getProperty(C_Properties.LOG_ENTRY_KEY) != null) ? (LogEntry)keeper.getProperty(C_Properties.LOG_ENTRY_KEY) : new LogEntry();
		keeper.setProperty(C_Properties.LOG_ENTRY_KEY, logEntry);
		if(servicename == null)
			servicename = "undefined";
		logEntry.setService(servicename.replace("/", "-"));
		
		if(logEntry.getPath() == null){
			logEntry.setPath((String)keeper.getProperty(Keeper.REQUEST_URI));
		}
		
		if (logEntry.getServiceCategory() == null) {
			logEntry.setServiceCategory(ServiceCategory.WEBSERVICE_REST);
		}
		
		if (logEntry.getService() != null) logEntry.setService(logEntry.getService().toLowerCase());
		// Identifiers (TransactionId, ClientTransactionId, SessionId, ClientSessionId)
		if (logEntry.getTransactionId() == null) {
			logEntry.setTransactionId((String) keeper.getProperty(C_Properties.BREADCRUMB_ID));
			if (logEntry.getTransactionId() == null) {
				logEntry.setTransactionId((String) keeper.getHeader(Keeper.BREADCRUMB_ID));
			}
		}
		// Identifiers (ClientTransactionId)
		if (logEntry.getClientTransactionId() == null) {
			logEntry.setClientTransactionId((String) keeper.getProperty(C_Properties.CLIENT_TRANSACTION_ID_KEY));
			if (logEntry.getClientTransactionId() == null) {
				logEntry.setClientTransactionId((String) keeper.getHeader(C_HeaderFields.CLIENT_TRANSACTION_ID_KEY));
			}
		}
		// Identifiers (SessionId)
		if (logEntry.getSessionId() == null) {
			logEntry.setSessionId((String) keeper.getProperty(C_Properties.SESSION_ID_KEY));
			if (logEntry.getSessionId() == null) {
				logEntry.setSessionId((String) keeper.getHeader(C_HeaderFields.SESSION_ID_KEY));
			}
		}
		// Identifiers (ClientSessionId)
		if (logEntry.getClientSessionId() == null) {
			logEntry.setClientSessionId((String) keeper.getProperty(C_Properties.CLIENT_SESSION_ID_KEY));
			if (logEntry.getClientSessionId() == null) {
				logEntry.setClientSessionId((String) keeper.getHeader(C_HeaderFields.CLIENT_SESSION_ID_KEY));
			}
		}
		// Identifiers (ClientId)
		if (logEntry.getClientId() == null) {
			logEntry.setClientId((String) keeper.getProperty(C_Properties.CLIENT_ID_KEY));
			if (logEntry.getClientId() == null) {
				logEntry.setClientId((String) keeper.getHeader(C_HeaderFields.CLIENT_ID_KEY));
			}
		}
		// ClientIp
		// If available, Client IP = XForwardedFor (IP of client behind load balancer, passed in the HTTP header.)
		// Else, the SRC IP in the HTTP CommonsDBHeader will be used. (aka Load Balancer IP)
		if (logEntry.getClientIp() == null) {
			logEntry.setClientIp((String) keeper.getProperty(C_Properties.CLIENT_IP_KEY));
			if (logEntry.getClientIp() == null) {
				logEntry.setClientIp((String) keeper.getProperty(C_Properties.LOAD_BALANCER_IP_KEY));
			}
		}
		// Get Start Timestamp from the Exchange CREATED_TIMESTAMP
		logEntry.setStartTime((Date)keeper.getProperty(Keeper.CREATED_TIMESTAMP));
		
	}
    
    protected void postProcessor(Keeper keeper) {
		LogEntry logEntry = (LogEntry)keeper.getProperty(C_Properties.LOG_ENTRY_KEY);
		if (logEntry != null && logEntry.getClientId() != null) 
			logEntry.setClientId(logEntry.getClientId().toLowerCase());
//			loggingService.writeLogAsync(logEntry);
		if (logEntry != null) 
			LoggerUtil.writeLog(logEntry);
	}
}
