package com.smpp.platform.smppcore;

import com.smpp.platform.dal.AppDal;
import org.jsmpp.bean.*;
import org.jsmpp.extra.ProcessRequestException;
import org.jsmpp.session.DataSmResult;
import org.jsmpp.session.MessageReceiverListener;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.session.Session;
import org.jsmpp.util.InvalidDeliveryReceiptException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReceptListener {

    @Autowired
    AppDal db;

    public void listen(SMPPSession session) {
        // Set listener to receive deliver_sm
        session.setMessageReceiverListener(new MessageReceiverListener() {

            public void onAcceptDeliverSm(DeliverSm deliverSm)
                    throws ProcessRequestException {
                if (MessageType.SMSC_DEL_RECEIPT.containedIn(deliverSm
                        .getEsmClass())) {
                    // delivery receipt
                    try {
                        DeliveryReceipt delReceipt = deliverSm
                                .getShortMessageAsDeliveryReceipt();
                        long id = Long.parseLong(delReceipt.getId()) & 0xffffffff;

                        String messageId = Long.toString(id, 16).toUpperCase();

                    } catch (InvalidDeliveryReceiptException e) {
                        System.err.println("receive faild");
                        e.printStackTrace();
                    }
                } else {
                    // regular short message
                    System.out.println("Receiving message : "
                            + new String(deliverSm.getShortMessage()));
                }
            }

            public void onAcceptAlertNotification(
                    AlertNotification alertNotification) {
                System.out.println("onAcceptAlertNotification");
            }

            public DataSmResult onAcceptDataSm(DataSm dataSm, Session source)
                    throws ProcessRequestException {
                System.out.println("onAcceptDataSm");
                return null;
            }
        });
    }
}
