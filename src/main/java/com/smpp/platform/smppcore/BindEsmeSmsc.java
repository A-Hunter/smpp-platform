package com.smpp.platform.smppcore;

import java.io.IOException;

import org.jsmpp.bean.BindType;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;

public class BindEsmeSmsc {
	
public void bind(String server,int port, SMPPSession session){
	try {

		session.connectAndBind(server, port, new BindParameter(
				BindType.BIND_TRX, "test", "test", "cp",
				TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, null));
		System.out
				.println("In this stage , I'm connected with 2 servers :");
		System.out.println("- SMPP server : 8056 ");
		System.out.println("- Redis server : 6379 \n");

	} catch (IOException e) {
		System.err.println("Failed connect and bind to host");
		e.printStackTrace();
	}

}

}
