package com.smpp.platform.controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

	String startDateStr ;//= "2013-09-27 00:00:00.0";
	startDateStr=msg.getSendDate();
    DateFormat stDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date startDate = stDate.parse(startDateStr);

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
	
		scheduler.start();
		scheduler.scheduleJob(jobDetail, trigger);
}

}
