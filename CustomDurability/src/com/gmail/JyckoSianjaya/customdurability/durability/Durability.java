package com.gmail.JyckoSianjaya.customdurability.durability;

public class Durability {
	private String rawdurability;
	private String emptydurability;
	public Durability(String info) {
		this.rawdurability = info;
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
	public String getFormatted(int mindur, int maxdur) {
		String clone  = rawdurability;
		clone = clone.replaceAll("%s", "" + mindur);
		clone = clone.replaceAll("%m", "" + maxdur);
		return clone;
	}
}
