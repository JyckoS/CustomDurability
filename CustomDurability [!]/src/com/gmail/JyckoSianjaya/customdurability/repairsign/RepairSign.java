package com.gmail.JyckoSianjaya.customdurability.repairsign;

import org.bukkit.block.Sign;

public class RepairSign {
	private Sign s;
	private RepairCost cost;
	private RepairTarget target;
	public RepairSign(Sign s, RepairCost cost, RepairTarget t) {
		this.s = s;
		this.cost = cost;
		this.target = t;
	}
	public RepairTarget getTarget() { return target; }
	public Sign getSign() { return s; }
	public RepairCost getCost() { return cost; }
	public enum RepairTarget {
		HAND,
		ALL;
	}
}
