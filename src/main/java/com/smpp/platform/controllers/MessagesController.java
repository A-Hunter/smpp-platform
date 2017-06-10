package com.smpp.platform.controllers;

import java.util.List;

import com.smpp.platform.entities.SendAllMessage;
import com.smpp.platform.entities.SendMessage;
import com.smpp.platform.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/api")
@ComponentScan("com.tritux.sms.web")
public class MessagesController {

	 @Autowired
	 UserService crudService;
     
    @RequestMapping(value = "/historic", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)//
    public ResponseEntity<List<SendMessage>> listMessages() {
        List<SendMessage> msgs = crudService.findMsgs();
        if(msgs.isEmpty()){
            return new ResponseEntity<List<SendMessage>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<SendMessage>>(msgs, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/historicAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)//
    public ResponseEntity<List<SendAllMessage>> listAllMessages() {
        List<SendAllMessage> msgsAll = crudService.findAllMsgs();
        if(msgsAll.isEmpty()){
            return new ResponseEntity<List<SendAllMessage>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<SendAllMessage>>(msgsAll, HttpStatus.OK);
    }
    
	 
}
