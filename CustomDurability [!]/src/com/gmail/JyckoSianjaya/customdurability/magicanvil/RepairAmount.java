package com.gmail.JyckoSianjaya.customdurability.magicanvil;

import java.util.Random;

import com.gmail.JyckoSianjaya.customdurability.storage.ConfigStorage;

public class RepairAmount {
	private RepairType type = RepairType.INTEGER;
	private double repairamount = 0;
	public RepairAmount(RepairType type, double repairamount) {
		this.type = type;
		switch (type) {
		case PERCENTAGE:
			repairamount = repairamount / 100;
			break;
		case RANDOM_PERCENTAGE:
			repairamount = repairamount / 100;
			break;
		case INTEGER:
			break;
		case RANDOM_INTEGER:
			break;
		}
		this.repairamount = repairamount;
	}
	public double getRepairAmount() { return repairamount; }
	public RepairType getRepairType() { return type; }
	public void setRepairAmount(double toamount) {
		this.repairamount = toamount;
	}
	public void setRepairType(RepairType toType) {
		this.type = toType;
	}
	public int getFinalRepairAmount(int currentdur, int maxdur) {
		switch (type) {
		case PERCENTAGE:
			currentdur = (int) (currentdur * repairamount);
			return currentdur;
		case RANDOM_PERCENTAGE: {
			Random rand = new Random();
			double max = ConfigStorage.getInstance().getMagicAnvil_maxRandPercentage() * 100;
			double min = ConfigStorage.getInstance().getMagicAnvil_minRandPercentage() * 100;
			int randss = rand.nextInt((int) max) - 1;
			if (randss < min) randss = (int) min;
			return currentdur * randss / 100;
		}
		case INTEGER:
			return (int) repairamount;
		case RANDOM_INTEGER:
			Random rand = new Random();
			int maximum = ConfigStorage.getInstance().getMagicAnvil_maxIntegerAmount();
			int max = rand.nextInt(maximum);
			int minimum = ConfigStorage.getInstance().getMagicAnvil_minIntegerAmount();
			if (max < ConfigStorage.getInstance().getMagicAnvil_minIntegerAmount()) max = minimum;
			return max;
			default:
				return 0;
		}
	}
}
