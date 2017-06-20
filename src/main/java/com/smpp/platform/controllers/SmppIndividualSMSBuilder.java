package com.smpp.platform.controllers;

import com.smpp.platform.configuration.SMPPApplicationContextAware;
import com.smpp.platform.entities.IndividualSMS;
import com.smpp.platform.services.IndividualSMSService;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SmppIndividualSMSBuilder implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {

        IndividualSMSService mSMS = SMPPApplicationContextAware.getContext().getBean(IndividualSMSService.class);
        IndividualSMS msg = new IndividualSMS();

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

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