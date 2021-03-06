package com.smpp.platform.controllers;

import com.smpp.platform.entities.IndividualSMS;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SmppJobForIndividualSMS {

    public void send(IndividualSMS msg) throws ParseException, SchedulerException {

        String startDateStr;//= "2013-09-27 00:00:00.0";
        startDateStr = msg.getSendDate();
        DateFormat stDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = stDate.parse(startDateStr);

        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobDetail jobDetail = JobBuilder.newJob(SmppIndividualSMSBuilder.class)
                .withIdentity("SmppIndividualSMSBuilder", "mygroup")
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
