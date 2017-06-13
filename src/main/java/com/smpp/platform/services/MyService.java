package com.smpp.platform.services;


import com.smpp.platform.dal.AppDal;
import com.smpp.platform.entities.SendMessage;
import com.smpp.platform.smppcore.BindEsmeSmsc;
import com.smpp.platform.smppcore.ReceptListener;
import com.smpp.platform.smppcore.UnbindEsmeSmsc;
import org.apache.log4j.BasicConfigurator;
import org.jsmpp.bean.*;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.util.AbsoluteTimeFormatter;
import org.jsmpp.util.TimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class MyService {
    private static TimeFormatter timeFormatter = new AbsoluteTimeFormatter();

    int port = 8056;
    String server = "localhost";

    @Autowired
    AppDal db;
    SMPPSession session;

    @Autowired
    ReceptListener receptListener;

    @PostConstruct
    private void init() {
        BasicConfigurator.configure();
        session = new SMPPSession();
        BindEsmeSmsc bindEsmeSmsc = new BindEsmeSmsc();
        bindEsmeSmsc.bind(server, port, session);
        receptListener.listen(session);
    }

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date;

    public void sendSMS(SendMessage sendMessage) {

        try {
            date = formatter.parse(sendMessage.getSendDate());
            System.out.println(date);
            System.out.println(formatter.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        try {
            // set RegisteredDelivery
            final RegisteredDelivery registeredDelivery = new RegisteredDelivery();
            registeredDelivery
                    .setSMSCDeliveryReceipt(SMSCDeliveryReceipt.SUCCESS_FAILURE);

            String messageId = null;

            messageId = session.submitShortMessage("CMT",
                    TypeOfNumber.INTERNATIONAL,
                    NumberingPlanIndicator.UNKNOWN, "1616",
                    TypeOfNumber.INTERNATIONAL,
                    NumberingPlanIndicator.UNKNOWN, sendMessage.getPhone(),
                    new ESMClass(), (byte) 0, (byte) 1, timeFormatter
                            .format(date), null, registeredDelivery,
                    (byte) 0, new GeneralDataCoding(Alphabet.ALPHA_DEFAULT,
                            MessageClass.CLASS1, false), (byte) 0, sendMessage.getText().getBytes());

            System.out.println("Message submitted, message_id is "
                    + messageId);
            db.addMessage(sendMessage);//(messageId, sendMessage);
            System.out.println("'" + sendMessage.getText() + "' is your message and it is stored now in Redis Database");

        } catch (Exception e) {
            // Invalid PDU parameter
            System.err.println("Invalid PDU parameter");
            e.printStackTrace();
        }


    }

    @PreDestroy
    private void destroy() {
        // wait 3 second
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // unbind(disconnect)
        UnbindEsmeSmsc unbindEsmeSmsc = new UnbindEsmeSmsc();
        unbindEsmeSmsc.unbind(session);

        System.out.println("finish!");
    }

}
