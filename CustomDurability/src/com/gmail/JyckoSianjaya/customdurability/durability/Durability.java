package com.gmail.JyckoSianjaya.customdurability.durability;

import com.gmail.JyckoSianjaya.customdurability.Utility.Utility;

public class Durability {
	private String rawdurability;
	private String emptydurability;
	private String notifier = "";
	private Boolean hasNotifier = false;
	public Durability(String info, String notifier) {
		this.rawdurability = info;
		if (notifier != null) {
		this.notifier = Utility.TransColor(notifier);
		if (notifier.length() > 1) {
			hasNotifier = true;
		}
		}
		info = info.replaceAll("%s", "");
		info = info.replaceAll("%m", "");
		this.emptydurability = info;
	}
	public String getRawFormat() {
		return rawdurability;
	}
	public String getEmptyFormat() {
		return emptydurability;
	}
	public boolean hasNotifier() {
		return hasNotifier;
	}
	public String getFormatted(int mindur, int maxdur) {
		String clone  = rawdurability;
		clone = clone.replaceAll("%s", "" + mindur);
		clone = clone.replaceAll("%m", "" + maxdur);
		return clone;
	}
	public String getActionBar(int curdur, int maxdur) {
		String clone = notifier;
		clone = clone.replaceAll("%s", curdur + "");
		clone = clone.replaceAll("%m", maxdur + "");
		return clone;
	}
}
