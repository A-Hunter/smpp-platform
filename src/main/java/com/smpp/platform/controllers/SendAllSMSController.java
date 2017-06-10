package com.smpp.platform.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.smpp.platform.entities.SendAllMessage;
import com.smpp.platform.services.MultipleSMS;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value="/api")
@ComponentScan("com.tritux.sms.web")
public class SendAllSMSController {
	
	@Autowired
	MultipleSMS multiplesms;
	
	@RequestMapping(value = "/sendAllSMS", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public void pleaseSendAllSMS(@RequestBody final SendAllMessage msg) throws SchedulerException, ParseException {
		
		SmppJob s = new SmppJob() ;
		
		s.sendings(msg);
	//	multiplesms.sendAllSMS(msg);
		
	}
	
	@RequestMapping(value = "/sendAllSMSMale", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public void pleaseSendAllSMSMale(@RequestBody final SendAllMessage msg) throws SchedulerException, ParseException {
		
		SmppJob s = new SmppJob() ;
		
		s.sendings(msg);
		//multiplesms.sendAllSMSMale(msg);
		
	}
	
	@RequestMapping(value = "/sendAllSMSFemale", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public void pleaseSendAllSMSFemale(@RequestBody final SendAllMessage msg) throws SchedulerException, ParseException {
		
		SmppJob s = new SmppJob() ;
		
		s.sendings(msg);
		//multiplesms.sendAllSMSFemale(msg);
		
	}
}
