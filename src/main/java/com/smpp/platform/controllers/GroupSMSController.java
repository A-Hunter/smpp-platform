package com.smpp.platform.controllers;

import com.smpp.platform.entities.GroupSMS;
import com.smpp.platform.services.GroupSMSService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;


@Controller
@RequestMapping(value = "/api")
@ComponentScan("com.tritux.sms.web")
public class GroupSMSController {

    @Autowired
    GroupSMSService multiplesms;

    @RequestMapping(value = "/sendAllSMS", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public void pleaseSendAllSMS(@RequestBody final GroupSMS msg) throws SchedulerException, ParseException {

        SmppJobForGroupSMS s = new SmppJobForGroupSMS();
        s.send(msg);
    }

    @RequestMapping(value = "/sendAllSMSMale", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public void pleaseSendAllSMSMale(@RequestBody final GroupSMS msg) throws SchedulerException, ParseException {

        SmppJobForGroupSMS s = new SmppJobForGroupSMS();
        s.send(msg);
    }

    @RequestMapping(value = "/sendAllSMSFemale", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public void pleaseSendAllSMSFemale(@RequestBody final GroupSMS msg) throws SchedulerException, ParseException {

        SmppJobForGroupSMS s = new SmppJobForGroupSMS();
        s.send(msg);
    }
}
