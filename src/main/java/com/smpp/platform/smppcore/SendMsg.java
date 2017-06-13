package com.smpp.platform.smppcore;

import org.apache.log4j.BasicConfigurator;
import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.*;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.util.AbsoluteTimeFormatter;
import org.jsmpp.util.TimeFormatter;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.Date;

@Service
public class SendMsg {
    private static TimeFormatter timeFormatter = new AbsoluteTimeFormatter();

    public void send(SMPPSession session, String server, int port, Jedis jedis, String addr, String message, Date sendDate) {

        // send Message
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
                    NumberingPlanIndicator.UNKNOWN, addr,
                    new ESMClass(), (byte) 0, (byte) 1, timeFormatter
                            .format(sendDate), null, registeredDelivery,
                    (byte) 0, new GeneralDataCoding(Alphabet.ALPHA_DEFAULT,
                            MessageClass.CLASS1, false), (byte) 0, message.getBytes());

            System.out.println("Message submitted, message_id is "
                    + messageId);
            jedis.set("index".substring(6), message);
            System.out.println("'" + message + "' is your message and it is stored now in Redis Database");

        } catch (PDUException e) {
            // Invalid PDU parameter
            System.err.println("Invalid PDU parameter");
            e.printStackTrace();
        } catch (ResponseTimeoutException e) {
            // Response timeout
            System.err.println("Response timeout");
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            // Invalid response
            System.err.println("Receive invalid respose");
            e.printStackTrace();
        } catch (NegativeResponseException e) {
            // Receiving negative response (non-zero command_status)
            System.err.println("Receive negative response");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO error occur");
            e.printStackTrace();
        }

        // receive Message
        BasicConfigurator.configure();


    }
}
