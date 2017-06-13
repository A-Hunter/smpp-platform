package com.smpp.platform.controllers;

import com.smpp.platform.configuration.SMPPApplicationContextAware;
import com.smpp.platform.entities.GroupSMS;
import com.smpp.platform.services.GroupSMSService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.ParseException;

public class SMPPsmsSendings implements Job {

    public void execute(JobExecutionContext context)
            throws JobExecutionException {


        GroupSMSService mSMS = SMPPApplicationContextAware.getContext().getBean(GroupSMSService.class);
        GroupSMS msg = new GroupSMS();

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