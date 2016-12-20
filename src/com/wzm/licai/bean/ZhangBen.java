package com.wzm.licai.bean;

public class ZhangBen {
   private int id;
   private String name;
   public ZhangBen(){}
public ZhangBen(int id, String name) {
	super();
	this.id = id;
	this.name = name;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
   @Override
	public String toString() {
		// TODO Auto-generated method stub
		return id+" "+name;
	}
}
