package com.smpp.platform.smppcore;

import org.jsmpp.session.SMPPSession;

public class UnbindEsmeSmsc {
public void unbind(SMPPSession session){
	session.unbindAndClose();
}
}
