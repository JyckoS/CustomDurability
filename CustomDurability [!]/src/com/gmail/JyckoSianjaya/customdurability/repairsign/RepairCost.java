package com.gmail.JyckoSianjaya.customdurability.repairsign;

public class RepairCost {
	private RepairCostType type = null;
	private int amount = 0;
	public RepairCostType getRepairCostType() {
		return type;
	}
	public int getAmount() {
		return amount;
	}
	public RepairCost(RepairCostType type, int amount) {
		this.type = type;
		this.amount = amount;
	}
	public enum RepairCostType {
		XP,
		XPLevel,
		Money,
		None;
	}
}
