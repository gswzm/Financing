package com.wzm.licai.bean;

public class Wallet {
    private int id;
    private String name;
    private double money;
    public Wallet(){}
	public Wallet(int id, String name, double money) {
		super();
		this.id = id;
		this.name = name;
		this.money = money;

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
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
    
}
