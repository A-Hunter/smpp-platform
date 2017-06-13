package com.smpp.platform.configuration;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SMPPApplicationContextAware implements ApplicationContextAware{
	private static ApplicationContext applicationContext;
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SMPPApplicationContextAware.applicationContext=applicationContext;
		
	}
	
	public static ApplicationContext getContext(){
		return applicationContext;
	}

}
