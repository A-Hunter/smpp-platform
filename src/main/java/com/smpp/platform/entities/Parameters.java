package com.smpp.platform.entities;

import com.smpp.platform.dal.Id;


public class Parameters {
	@Id
	private long id=0;
	private long adminId=0;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getAdminId() {
		return adminId;
	}
	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}
	
}
