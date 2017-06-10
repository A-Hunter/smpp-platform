package com.smpp.platform.controllers;

import com.smpp.platform.entities.SendMessage;
import com.smpp.platform.services.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping(value="/api")
@ComponentScan("com.tritux.sms.web")
public class SendSMSController {
	
	@Autowired
	MyService service;
	
	@RequestMapping(value = "/sendSMS", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public void pleaseSendSMS(@RequestBody final SendMessage msg) throws Exception {
		//service.sendSMS(msg);
		SmppJobSingle s = new SmppJobSingle() ;
			//service.sendSMS(msg);
			//(msg.getPhone(),msg.getSendDate(),msg.getText());
		s.sendings(msg);
	
	 }
	
	
}
