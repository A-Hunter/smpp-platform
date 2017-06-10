package com.smpp.platform.configuration;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class TrituxApp implements ApplicationContextAware{
	private static ApplicationContext applicationContext;
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		TrituxApp.applicationContext=applicationContext;
		
	}
	
	public static ApplicationContext getContext(){
		return applicationContext;
	}

}
