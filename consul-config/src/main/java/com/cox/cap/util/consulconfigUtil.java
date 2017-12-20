package com.cox.cap.util;

import com.cox.cap.commons.logging.LogEntry;
import com.cox.cap.commons.logging.LogMessage;

public class consulconfigUtil {
	
	public static void addMessageToLog(LogEntry logentry, String message){
		LogMessage logMessage = new LogMessage();
		logMessage.setMessage(message);
		logentry.getMessages().add(logMessage);
	}

}