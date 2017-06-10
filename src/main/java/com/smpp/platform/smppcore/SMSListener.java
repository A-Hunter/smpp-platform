package com.smpp.platform.smppcore;

import org.jsmpp.session.SMPPSession;
import org.springframework.stereotype.Component;

@Component
public class SMSListener {

	public void listenerSMS(SMPPSession session){
		
		session.setMessageReceiverListener(new MessageReceiverListenerImpl()); 
	}
}
