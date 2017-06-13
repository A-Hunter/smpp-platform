package com.smpp.platform.controllers;

import java.text.ParseException;

import com.smpp.platform.configuration.SMPPApplicationContextAware;
import com.smpp.platform.entities.SendAllMessage;
import com.smpp.platform.services.MultipleSMS;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SMPPsmsSendings implements Job {
	
	public void execute(JobExecutionContext context)
				throws JobExecutionException {


		MultipleSMS mSMS = SMPPApplicationContextAware.getContext().getBean(MultipleSMS.class) ;
		SendAllMessage msg = new SendAllMessage();

		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
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