package com.smpp.platform.controllers;

import com.smpp.platform.entities.GroupSMS;
import com.smpp.platform.entities.IndividualSMS;
import com.smpp.platform.services.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "/api")
@ComponentScan("com.tritux.sms.web")
public class MessagesController {

    @Autowired
    SMSService smsService;

    @RequestMapping(value = "/historic", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)//
    public ResponseEntity<List<IndividualSMS>> listMessages() {
        List<IndividualSMS> msgs = smsService.findMsgs();
        if (msgs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(msgs, HttpStatus.OK);
    }

    @RequestMapping(value = "/historicAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)//
    public ResponseEntity<List<GroupSMS>> listAllMessages() {
        List<GroupSMS> msgsAll = smsService.findAllMsgs();
        if (msgsAll.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<>(msgsAll, HttpStatus.OK);
    }


}
