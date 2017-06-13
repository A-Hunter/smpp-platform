package com.smpp.platform.services;

import com.smpp.platform.dal.AppDal;
import com.smpp.platform.entities.SendAllMessage;
import com.smpp.platform.entities.User;
import com.smpp.platform.smppcore.BindEsmesSmsc;
import com.smpp.platform.smppcore.SMSListener;
import com.smpp.platform.smppcore.UnbindEsmesSmsc;
import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.*;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.util.AbsoluteTimeFormatter;
import org.jsmpp.util.TimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class MultipleSMS {
    private static TimeFormatter timeFormatter = new AbsoluteTimeFormatter();
    ;

    int port = 8056;
    String server = "localhost";

    @Autowired
    AppDal db;

    SMPPSession session;
    Address address = null;

    @Autowired
    UserServiceImpl cs;

    @Autowired
    SMSListener smsListener;

    @PostConstruct
    private void init() {
        session = new SMPPSession();
        smsListener.listenerSMS(session);
        BindEsmesSmsc bindEsmesSmsc = new BindEsmesSmsc();
        bindEsmesSmsc.bind(server, port, session);
    }

    public <T> T[] append(T[] arr, T element) {
        final int N = arr.length;
        arr = Arrays.copyOf(arr, N + 1);
        arr[N] = element;
        return arr;
    }

    public void sendAllSMS(SendAllMessage sendAllMessage) throws ParseException {

        String startDateStr = sendAllMessage.getSendDate(); //= "2013-09-27 00:00:00.0";
        DateFormat stDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date startDate = stDate.parse(startDateStr);

        try {
            SubmitMultiResult result;
            List<User> users = cs.findAllUsers();
            Address[] addresses = {};
            // s.o.p(addresses)
            for (User user : users) {
                String add = user.getPhoneNumber();
                address = new Address(TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.UNKNOWN, add);
                addresses = append(addresses, address);

            }

            result = session.submitMultiple("CMT",
                    TypeOfNumber.INTERNATIONAL,
                    NumberingPlanIndicator.UNKNOWN, "1616",
                    addresses,
                    new ESMClass(), (byte) 0, (byte) 1, timeFormatter
                            .format(startDate), null, new RegisteredDelivery(SMSCDeliveryReceipt.SUCCESS),
                    ReplaceIfPresentFlag.REPLACE,
                    new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1, false), (byte) 0, sendAllMessage.getText().getBytes());

            System.out.println("Message submitted, message_id is "
                    + result.getMessageId());
            System.out.println("Messages submitted, result is " + result);
            System.out.println("Messages submitted, result is " + result);
            System.out.println("Messages submitted, result is " + result);
            System.out.println("Messages submitted, result is " + result);
            System.out.println("Messages submitted, result is " + result);
            db.addAllMessage(sendAllMessage);//(result.getMessageId(), result,sendAllMessage);

            System.out.println("'" + sendAllMessage.getText() + "' is your message and it is stored now in Redis Database");

            Thread.sleep(2000);
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
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted");
            e.printStackTrace();
        }
    }

    public void sendAllSMSMale(SendAllMessage sendAllMessage) throws ParseException {

        String startDateStr = sendAllMessage.getSendDate(); //= "2013-09-27 00:00:00.0";

        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(startDateStr);

        try {
            SubmitMultiResult result;
            List<User> users = cs.findAllUsers();
            Address[] addresses = {};
            // s.o.p(addresses)
            for (User user : users) {
                if (user.getGender().equals("male")) {
                    String add = user.getPhoneNumber();
                    address = new Address(TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.UNKNOWN, add);
                    addresses = append(addresses, address);
                }
            }

            result = session.submitMultiple("CMT",
                    TypeOfNumber.INTERNATIONAL,
                    NumberingPlanIndicator.UNKNOWN, "1616",
                    addresses,
                    new ESMClass(), (byte) 0, (byte) 1, timeFormatter
                            .format(startDate), null, new RegisteredDelivery(SMSCDeliveryReceipt.SUCCESS),
                    ReplaceIfPresentFlag.REPLACE,
                    new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1, false), (byte) 0, sendAllMessage.getText().getBytes());

            System.out.println("Message submitted, message_id is "
                    + result.getMessageId());
            System.out.println("Messages submitted, result is " + result);
            System.out.println("Messages submitted, result is " + result);
            System.out.println("Messages submitted, result is " + result);
            System.out.println("Messages submitted, result is " + result);
            System.out.println("Messages submitted, result is " + result);
            db.addAllMessage(sendAllMessage);//(result.getMessageId(), result,sendAllMessage);

            System.out.println("'" + sendAllMessage.getText() + "' is your message and it is stored now in Redis Database");

            Thread.sleep(2000);
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
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted");
            e.printStackTrace();
        }
    }

    public void sendAllSMSFemale(SendAllMessage sendAllMessage) throws ParseException {

        String startDateStr = sendAllMessage.getSendDate(); //= "2013-09-27 00:00:00.0";
        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(startDateStr);

        try {
            SubmitMultiResult result;
            List<User> users = cs.findAllUsers();
            Address[] addresses = {};
            for (User user : users) {
                if (user.getGender().equals("female")) {
                    String add = user.getPhoneNumber();
                    address = new Address(TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.UNKNOWN, add);
                    addresses = append(addresses, address);
                }
            }

            result = session.submitMultiple("CMT",
                    TypeOfNumber.INTERNATIONAL,
                    NumberingPlanIndicator.UNKNOWN, "1616",
                    addresses,
                    new ESMClass(), (byte) 0, (byte) 1, timeFormatter
                            .format(startDate), null, new RegisteredDelivery(SMSCDeliveryReceipt.SUCCESS),
                    ReplaceIfPresentFlag.REPLACE,
                    new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1, false), (byte) 0, sendAllMessage.getText().getBytes());

            System.out.println("Message submitted, message_id is "
                    + result.getMessageId());
            System.out.println("Messages submitted, result is " + result);
            System.out.println("Messages submitted, result is " + result);
            System.out.println("Messages submitted, result is " + result);
            System.out.println("Messages submitted, result is " + result);
            System.out.println("Messages submitted, result is " + result);
            db.addAllMessage(sendAllMessage);//(result.getMessageId(), result,sendAllMessage);

            System.out.println("'" + sendAllMessage.getText() + "' is your message and it is stored now in Redis Database");

            Thread.sleep(2000);
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
        } catch (InterruptedException e) {
            System.err.println("Thread interrupted");
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
        UnbindEsmesSmsc unbindEsmesSmsc = new UnbindEsmesSmsc();
        unbindEsmesSmsc.unbind(session);
    }

}


