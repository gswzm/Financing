package com.wzm.licai.bean;

public class UserBean {
   public static final String TABLE_USER="user"; 
   public static final String NAME="name";
   public static final String PWD="password";
   public static final String ID="_id";
   private int id;
   private String name;
   private String pwd;
   public UserBean(){}
public UserBean(int id, String name, String pwd) {
	super();
	this.id = id;
	this.name = name;
	this.pwd = pwd;
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
public String getPwd() {
	return pwd;
}
public void setPwd(String pwd) {
	this.pwd = pwd;
}
   
}
