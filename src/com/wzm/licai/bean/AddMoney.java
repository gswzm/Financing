package com.wzm.licai.bean;

public class AddMoney {
    private int id;
    private double money;
    private String date;
    private String type;
    private String mark;
    private String wid;
    private String uid;
    private String zid;
    public AddMoney(){}
	public AddMoney(double money, String date, String type, String mark,
			String wid, String uid, String zid) {
		super();
		this.money = money;
		this.date = date;
		this.type = type;
		this.mark = mark;
		this.wid = wid;
		this.uid = uid;
		this.zid = zid;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getWid() {
		return wid;
	}
	public void setWid(String wid) {
		this.wid = wid;
	}
	public String getZid() {
		return zid;
	}
	public void setZid(String zid) {
		this.zid = zid;
	}   
}
