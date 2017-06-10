package com.smpp.platform.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.smpp.platform.entities.SendMessage;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;


public class SmppJobSingle {


	
	public void sendings(SendMessage msg) throws ParseException, SchedulerException{
	//JobBuilder.
	// This method should contain the multiple-sms-sending process .. 
	// I should look for it in Jsmpp lib 
//	SimpleDateFormat formatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
	//String dateInString = "Thursday, May 26, 2016 10:35:00 AM";
	String startDateStr ;//= "2013-09-27 00:00:00.0";
	startDateStr=msg.getSendDate();
    DateFormat stDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date startDate = stDate.parse(startDateStr);
	Date date = null;
	/**
	 DateFormat stDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
	      

	    Date startDate = stDate.parse(startDateStr);
	 */
/*
	try {
		date = formatter.parse(msg.getSendDate());
		System.out.println(date);
		System.out.println(formatter.format(date));
	} catch (ParseException e) {
		e.printStackTrace();
	}
*/

		
		//try {
			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			Scheduler scheduler = schedulerFactory.getScheduler();
		JobDetail jobDetail = JobBuilder.newJob(SMPPsmsSendingsSingle.class)
				.withIdentity("SMPPsmsSendings", "mygroup")
				.usingJobData("Id", msg.getSmsId())
				.usingJobData("phone", msg.getPhone())
				.usingJobData("sendDate", msg.getSendDate())
				.usingJobData("text", msg.getText())
				.build();
	
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("greattrigger", "mygroup")
			    .startAt(startDate)
			    .withSchedule(SimpleScheduleBuilder.simpleSchedule())
			    .build();
		//CronScheduleBuilder.cronSchedule("0 0 9-12 * * ?")
		//jobDetail.getJobDataMap().put(SMPPsmsSendings.ID, msg.getId());
		/**
		jobDetail.getJobDataMap().put(SMPPsmsSendings.SENDDATE, msg.getSendDate());
		jobDetail.getJobDataMap().put(SMPPsmsSendings.TEXT,msg.getText());
		*/
	//	jobDetail.getJobDataMap().put("sendDate", msg.getSendDate());
	//	jobDetail.getJobDataMap().put("text",msg.getText());
	
		scheduler.start();
		scheduler.scheduleJob(jobDetail, trigger);
	/*
		try {
			//wait for 30 seconds to finish the job
			Thread.sleep(30000);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		} catch (SchedulerException e) {
            e.printStackTrace();
        }
       */ 
	//	multiplesms.sendAllSMS(msg);
		
		//scheduler.shutdown(true);
	//	multiplesms.sendAllSMS(msg);
	

}

}
