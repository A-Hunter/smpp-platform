package com.smpp.platform.services;

import com.smpp.platform.entities.GroupSMS;
import com.smpp.platform.entities.IndividualSMS;

import java.util.List;

/**
 * Created by Ghazi Naceur on 13/06/2017.
 */
public interface SMSService {
    List<IndividualSMS> findMsgs();

    List<IndividualSMS> listMessages();

    List<GroupSMS> findAllMsgs();

    List<GroupSMS> listAllMessages();

    void saveSendMessage(IndividualSMS individualSMS);

    void saveSendAllMessage(GroupSMS groupSMS);
}
