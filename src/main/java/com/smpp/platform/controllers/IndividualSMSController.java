package com.smpp.platform.controllers;

import com.smpp.platform.entities.IndividualSMS;
import com.smpp.platform.services.IndividualSMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/api")
@ComponentScan("com.tritux.sms.web")
public class IndividualSMSController {

    @Autowired
    IndividualSMSService service;

    @RequestMapping(value = "/sendSMS", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public void pleaseSendSMS(@RequestBody final IndividualSMS msg) throws Exception {
        SmppJobForIndividualSMS s = new SmppJobForIndividualSMS();
        s.send(msg);
    }
}
