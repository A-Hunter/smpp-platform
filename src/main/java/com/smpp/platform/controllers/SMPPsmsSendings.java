package com.smpp.platform.controllers;

import java.text.ParseException;
import java.util.Date;

import com.smpp.platform.configuration.TrituxApp;
import com.smpp.platform.entities.SendAllMessage;
import com.smpp.platform.services.MultipleSMS;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

//@PersistJobDataAfterExecution
//@DisallowConcurrentExecution
public class SMPPsmsSendings implements Job {
	//private MyBean myBean;
	//@Autowired
	
	/**
	final static String ID = "Id";
	final static String SENDDATE = "sendDate";
	final static String TEXT = "text";
	*/
	
	public void execute(JobExecutionContext context)
				throws JobExecutionException {


		MultipleSMS mSMS = TrituxApp.getContext().getBean(MultipleSMS.class) ;
		SendAllMessage msg = new SendAllMessage();

		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
	/***
		String sendDate = dataMap.getString(SENDDATE);
		String text = dataMap.getString(TEXT);

		
			dataMap.put(SENDDATE,sendDate);
			dataMap.put(TEXT,text);
	 */
		String sendDate = dataMap.getString("sendDate");
		String text = dataMap.getString("text");
		
		msg.setSmsId("a1a1aa");
		msg.setSendDate(sendDate);
		msg.setText(text);
				try {
					mSMS.sendAllSMS(msg);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
	}

}