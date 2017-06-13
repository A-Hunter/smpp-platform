package com.smpp.platform.entities;

import com.smpp.platform.dal.GeneratedValue;
import com.smpp.platform.dal.Id;

public class IndividualSMS {
    @Id
    @GeneratedValue
    private long id;
    private String smsId;
    private String phone;
    private String sendDate;
    private String text;

    public String getSmsId() {
        return smsId;
    }

    public String getPhone() {
        return phone;
    }

    public String getSendDate() {
        return sendDate;
    }

    public String getText() {
        return text;
    }


    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
