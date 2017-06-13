package com.smpp.platform.entities;

import com.smpp.platform.dal.GeneratedValue;
import com.smpp.platform.dal.Id;


public class GroupSMS {
	@Id
	@GeneratedValue
	private long id;
	private String smsId;
	private String sendDate;
	private String text;
	
	
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getText() {
		return text;
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
	public String getSmsId() {
		return smsId;
	}
	public void setSmsId(String smsId) {
		this.smsId = smsId;
	}


	

}
