package com.gmail.JyckoSianjaya.customdurability.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.gmail.JyckoSianjaya.customdurability.customdurability;
import com.gmail.JyckoSianjaya.customdurability.Utility.Utility;
import com.gmail.JyckoSianjaya.customdurability.Utility.XMaterial;
import com.gmail.JyckoSianjaya.customdurability.magicanvil.RepairAmount;
import com.gmail.JyckoSianjaya.customdurability.magicanvil.RepairType;
import com.gmail.JyckoSianjaya.customdurability.magicanvil.Repairables;
import com.gmail.JyckoSianjaya.customdurability.storage.ConfigStorage.ServerVersion;

public class RepairStorage {
	private static RepairStorage instance;
	private HashMap<Material, RepairAmount> repairs = new HashMap<Material, RepairAmount>();
	private ArrayList<Material> todo = new ArrayList<Material>();
	private HashMap<Material, Repairables> repairables = new HashMap<Material, Repairables>();
	private RepairStorage() {
		loadTodo();
		reloadRepairs();
	}
	public static RepairStorage getInstance() {
		if (instance == null) {
			instance = new RepairStorage();
		}
		return instance;
	}
	public boolean hasRepairAmount(Material mat) {
		return repairs.containsKey(mat);
	}
	public boolean hasRepairables(Material mat) {
		return repairables.containsKey(mat);
	}
	public Repairables getRepairables(Material mat) {
		return repairables.get(mat);
	}
	public RepairAmount getRepairAmount(Material mat) {
		return repairs.get(mat);
	}
	private void loadTodo() {
		ArrayList<XMaterial> todo = new ArrayList<XMaterial>();
		todo.clear();
		todo.add(XMaterial.DIAMOND_AXE);
		todo.add(XMaterial.DIAMOND_BOOTS);
		todo.add(XMaterial.DIAMOND_CHESTPLATE);
		todo.add(XMaterial.DIAMOND_HELMET);
		todo.add(XMaterial.DIAMOND_HOE);
		todo.add(XMaterial.DIAMOND_LEGGINGS);
		todo.add(XMaterial.DIAMOND_PICKAXE);
		todo.add(XMaterial.DIAMOND_SHOVEL);
		todo.add(XMaterial.DIAMOND_SWORD);
		todo.add(XMaterial.GOLDEN_AXE);
		todo.add(XMaterial.GOLDEN_BOOTS);
		todo.add(XMaterial.GOLDEN_CHESTPLATE);
		todo.add(XMaterial.GOLDEN_HELMET);
		todo.add(XMaterial.GOLDEN_HOE);
		todo.add(XMaterial.GOLDEN_LEGGINGS);
		todo.add(XMaterial.GOLDEN_PICKAXE);
		todo.add(XMaterial.GOLDEN_SHOVEL);
		todo.add(XMaterial.GOLDEN_SWORD);
		todo.add(XMaterial.IRON_AXE);
		todo.add(XMaterial.IRON_BOOTS);
		todo.add(XMaterial.IRON_CHESTPLATE);
		todo.add(XMaterial.IRON_HELMET);
		todo.add(XMaterial.IRON_HOE);
		todo.add(XMaterial.IRON_LEGGINGS);
		todo.add(XMaterial.IRON_PICKAXE);
		todo.add(XMaterial.IRON_SHOVEL);
		todo.add(XMaterial.IRON_SWORD);
		todo.add(XMaterial.STONE_AXE);
		todo.add(XMaterial.STONE_HOE);
		todo.add(XMaterial.STONE_PICKAXE);
		todo.add(XMaterial.STONE_SHOVEL);
		todo.add(XMaterial.STONE_SWORD);	
		todo.add(XMaterial.WOODEN_AXE);
		todo.add(XMaterial.WOODEN_HOE);
		todo.add(XMaterial.WOODEN_PICKAXE);
		todo.add(XMaterial.WOODEN_SHOVEL);
		todo.add(XMaterial.WOODEN_SWORD);
		todo.add(XMaterial.FISHING_ROD);
		todo.add(XMaterial.SHEARS);
		todo.add(XMaterial.BOW);
		todo.add(XMaterial.FLINT_AND_STEEL);
		todo.add(XMaterial.SHIELD);
		if (ConfigStorage.getServerVersion() == ServerVersion._13) {
		todo.add(XMaterial.TRIDENT);
		}
		for (XMaterial mat : todo) {
			this.todo.add(mat.parseMaterial());
		}
	}
	public void reloadRepairs() {
		FileConfiguration config = customdurability.getInstance().getConfig();
		Set<String> materials = config.getConfigurationSection("magicanvil_options.repair_values").getKeys(false);
		materials.remove("same_material");
		ConfigurationSection reps = config.getConfigurationSection("magicanvil_options.repair_values");
		Byte dummybyte = Byte.valueOf("0");
		for (String str : materials) {
			RepairType type = null;
			try {
			type = RepairType.valueOf(reps.getString(str + ".type"));
			} catch (Exception e) {
				Utility.sendConsole("&c[CD] (RepairValues) &7There is no repair type: &c" + reps.getString(str + ".type") + "&7. For the Material: " + str);
				type = RepairType.INTEGER;
			}
			double amount = reps.getDouble(str + ".amount");
			Utility.sendConsole("LOADED: " + str + " type: " + type + "amount: " + amount);
			RepairAmount amounts = new RepairAmount(type, amount);
			Material mat = null;
			try {
			mat = XMaterial.requestXMaterial(str.toUpperCase(), dummybyte).parseMaterial();
			} catch (Exception e) {
				Utility.sendConsole("[CD] (RepairValues) &7There is no Material: " + str);
				mat = Material.IRON_INGOT;
			}
			repairs.put(mat, amounts);
		}
		RepairType type = null;
		try {
			type = RepairType.valueOf(reps.getString("same_material.type"));
		} catch (Exception e) {
			type = RepairType.PERCENTAGE;
			Utility.sendConsole("&c[CD] (Repair Values) &7There is no repair value type for &fsame_material&7. &4(INVALID)");
		}
		double amount = reps.getDouble("same_material.amount");
		RepairAmount rm = new RepairAmount(type, amount);
		for (Material mat : todo) {
			repairs.put(mat, rm);
		}
		/*
		 * 
		 * LOADS REPAIRABLES
		 * 
		 */
		ConfigurationSection rp = config.getConfigurationSection("repair_materials");
		if (ConfigStorage.getServerVersion() == ServerVersion._13) {
			// TRIDENT
			List<String> azstunmatssd = rp.getStringList("trident_item");
			ArrayList<Material> azsimatssd = new ArrayList<Material>();
			for (String str : azstunmatssd) {
				if (str.equalsIgnoreCase("@SELFITEM")) {
					azsimatssd.add(XMaterial.TRIDENT.parseMaterial());
					continue;
				}
				azsimatssd.add(XMaterial.requestXMaterial(str, dummybyte).parseMaterial());
			}
			Repairables azcxrddrpsd = new Repairables(azsimatssd);
			repairables.put(XMaterial.TRIDENT.parseMaterial(), azcxrddrpsd);
			
		}
		// FLINT AND STEEL
		List<String> azstunmatssd = rp.getStringList("flint_and_steel");
		ArrayList<Material> azsimatssd = new ArrayList<Material>();
		for (String str : azstunmatssd) {
			if (str.equalsIgnoreCase("@SELFITEM")) {
				azsimatssd.add(XMaterial.FLINT_AND_STEEL.parseMaterial());
				continue;
			}
			azsimatssd.add(XMaterial.requestXMaterial(str, dummybyte).parseMaterial());
		}
		Repairables azcxrddrpsd = new Repairables(azsimatssd);
		repairables.put(XMaterial.FLINT_AND_STEEL.parseMaterial(), azcxrddrpsd);
		
		// BOW
		List<String> zstunmatssd = rp.getStringList("bow_item");
		ArrayList<Material> zsimatssd = new ArrayList<Material>();
		for (String str : zstunmatssd) {
			if (str.equalsIgnoreCase("@SELFITEM")) {
				zsimatssd.add(XMaterial.BOW.parseMaterial());
				continue;
			}
			zsimatssd.add(XMaterial.requestXMaterial(str, dummybyte).parseMaterial());
		}
		Repairables zcxrddrpsd = new Repairables(zsimatssd);
		repairables.put(XMaterial.BOW.parseMaterial(), zcxrddrpsd);
		// SHEAR
		List<String> stunmatssd = rp.getStringList("shear_item");
		ArrayList<Material> simatssd = new ArrayList<Material>();
		for (String str : stunmatssd) {
			if (str.equalsIgnoreCase("@SELFITEM")) {
				simatssd.add(XMaterial.SHEARS.parseMaterial());
				continue;
			}
			simatssd.add(XMaterial.requestXMaterial(str, dummybyte).parseMaterial());
		}
		Repairables cxrddrpsd = new Repairables(simatssd);
		repairables.put(XMaterial.SHEARS.parseMaterial(), cxrddrpsd);
		// WOODEN
		List<String> stunmatss = rp.getStringList("wooden_items");
		ArrayList<Material> simatss = new ArrayList<Material>();
		Boolean sameitem = false;
		for (String str : stunmatss) {
			if (str.equalsIgnoreCase("@SELFITEM")) {
				sameitem = true;
				continue;
			}
			simatss.add(Material.matchMaterial(str));
		}
		Repairables cxrddrps = new Repairables(simatss);
		for (Material mat : this.todo) {
			Repairables clonexx = cxrddrps.clone();
			if (!mat.toString().contains("WOOD")) {
				continue;
			}
			if (sameitem) clonexx.addMaterial(mat);
			repairables.put(mat, clonexx);
		}
		// STONE
		List<String> stunmats = rp.getStringList("stone_items");
		ArrayList<Material> simats = new ArrayList<Material>();
		Boolean sameitems = false;
		for (String str : stunmats) {
			if (str.equalsIgnoreCase("@SELFITEM")) {
				sameitems = true;
				continue;
			}
			simats.add(XMaterial.requestXMaterial(str, dummybyte).parseMaterial());
		}
		Repairables cxrddrp = new Repairables(simats);
		for (Material mat : this.todo) {
			if (!mat.toString().contains("STONE")) {
				continue;
			}
			Repairables clonx = cxrddrp.clone();
			if (sameitems) clonx.addMaterial(mat);
			repairables.put(mat, clonx);
		}
		// IRON
		List<String> ironmats = rp.getStringList("iron_items");
		ArrayList<Material> imats = new ArrayList<Material>();
		Boolean sameitemz = false;
		for (String str : ironmats) {
			if (str.equalsIgnoreCase("@SELFITEM")) {
				sameitemz = true;
				continue;
			}
			imats.add(XMaterial.requestXMaterial(str, dummybyte).parseMaterial());
		}
		Repairables xrddrp = new Repairables(imats);
		for (Material mat : this.todo) {
			if (!mat.toString().contains("IRON")) {
				continue;
			}
			Repairables ironc = xrddrp.clone();
			if (sameitemz) ironc.addMaterial(mat);
			repairables.put(mat, ironc);
		}
		// GOLD
		List<String> goldmats = rp.getStringList("gold_items");
		ArrayList<Material> gmats = new ArrayList<Material>();
		Boolean samemat = false;
		for (String str : goldmats) {
			if (str.equalsIgnoreCase("@SELFITEM")) {
				samemat = true;
				continue;
			}
			gmats.add(XMaterial.requestXMaterial(str, dummybyte).parseMaterial());
		}
		Repairables rddrp = new Repairables(gmats);
		for (Material mat : this.todo) {
			if (!mat.toString().contains("GOLDEN")) {
				continue;
			}
			Repairables clan = rddrp.clone();
			if (samemat) clan.addMaterial(mat);
			repairables.put(mat, clan);
		}
		// DIAMOND 
		List<String> diamondmats = rp.getStringList("diamond_items");
		ArrayList<Material> mats = new ArrayList<Material>();
		Boolean samemaz = false;
		for (String str : diamondmats) {
			if (str.equalsIgnoreCase("@SELFITEM")) {
				samemaz = true;
				continue;
			}
			mats.add(XMaterial.requestXMaterial(str, dummybyte).parseMaterial());
		}
		Repairables ddrp = new Repairables(mats);
		for (Material mat : this.todo) {
			if (!mat.toString().contains("DIAMOND")) {
				continue;
			}
			Repairables dmd = ddrp.clone();
			if (samemaz) dmd.addMaterial(mat);
			repairables.put(mat, dmd);
		}
		
	}
}
