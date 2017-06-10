package com.smpp.platform.controllers;

import java.text.ParseException;

import com.smpp.platform.configuration.TrituxApp;
import com.smpp.platform.entities.SendMessage;
import com.smpp.platform.services.MyService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class SMPPsmsSendingsSingle implements Job {
	//private MyBean myBean;
	//@Autowired
	
	/**
	final static String ID = "Id";
	final static String SENDDATE = "sendDate";
	final static String TEXT = "text";
	*/
	
	public void execute(JobExecutionContext context)
				throws JobExecutionException {


		MyService mSMS = TrituxApp.getContext().getBean(MyService.class) ;
		SendMessage msg = new SendMessage();

		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
	/***
		String sendDate = dataMap.getString(SENDDATE);
		String text = dataMap.getString(TEXT);

		
			dataMap.put(SENDDATE,sendDate);
			dataMap.put(TEXT,text);
	 */
		String phone = dataMap.getString("phone");
		String sendDate = dataMap.getString("sendDate");
		String text = dataMap.getString("text");
		
		
		msg.setSmsId("a1a1aa");
		msg.setPhone(phone);
		msg.setSendDate(sendDate);
		msg.setText(text);
				mSMS.sendSMS(msg);
	
	}

}