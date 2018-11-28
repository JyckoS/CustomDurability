package com.gmail.JyckoSianjaya.customdurability.Experimental;

import com.gmail.JyckoSianjaya.customdurability.Utility.Utility;

public class AwesomeClass {
	private int cost = 100;
	public int coste = 100;
	private boolean lakad = false;
	private boolean can = true;
	public AwesomeClass(Integer amount) {
		cost = amount;
		say();
		lakad();
		lol();
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	private void say() {
		Utility.sendConsole("SAY!");
	}
	protected void lakad() {
		Utility.sendConsole("LAKAD!");
	}
	void lol() {
		Utility.sendConsole("LMAO!");
	}
	
}
