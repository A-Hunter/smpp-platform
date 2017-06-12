package com.smpp.platform.smppcore;

import java.io.IOException;

import org.jsmpp.bean.BindType;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;

public class BindEsmesSmsc {
	public void bind(String server,int port, SMPPSession session){
		try {
			session.connectAndBind("localhost", 8056,
                      new BindParameter(BindType.BIND_TRX, "test",
                                          "test", "cp",
                                          TypeOfNumber.UNKNOWN,
                                          NumberingPlanIndicator.UNKNOWN,
                                          null));
		   } catch (IOException e) {
	            System.err.println("Failed connect and bind to host");
	            e.printStackTrace();
	        }
	}
}
