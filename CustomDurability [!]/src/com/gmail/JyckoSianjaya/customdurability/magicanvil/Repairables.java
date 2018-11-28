package com.gmail.JyckoSianjaya.customdurability.magicanvil;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import com.gmail.JyckoSianjaya.customdurability.Utility.XMaterial;

public class Repairables {
	private ArrayList<Material> repairables = new ArrayList<Material>();
	public Repairables() {
	}
	public Repairables(List<String> materials) {
		for (String str : materials) {
			Material mat = XMaterial.requestXMaterial(str, Byte.valueOf("0")).parseMaterial();
			repairables.add(mat);
		}
	}
	public Repairables clone() {
		return new Repairables(repairables);
	}
	public Repairables(ArrayList<Material> aa) {
		repairables = new ArrayList<Material>(aa);
	}
	public void addMaterial(Material mat) {
		if (repairables.contains(mat)) {
			repairables.remove(mat);
		}
		repairables.add(mat);
	}
	public boolean isRepairable(Material mat) {
		return repairables.contains(mat);
	}
	public void removeMaterial(Material mat) {
		repairables.remove(mat);
	}
	public ArrayList<Material> getMaterials() {
		return repairables;
	}
}
