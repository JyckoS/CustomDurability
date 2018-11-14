package com.gmail.JyckoSianjaya.customdurability.storage;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.gmail.JyckoSianjaya.customdurability.customdurability;
import com.gmail.JyckoSianjaya.customdurability.Utility.Utility;
import com.gmail.JyckoSianjaya.customdurability.Utility.XMaterial;
import com.gmail.JyckoSianjaya.customdurability.damages.DamageConfig;
import com.gmail.JyckoSianjaya.customdurability.durability.Durability;

public class ConfigStorage {
	private static ConfigStorage instance;
	private HashMap<String, Durability> durabilities = new HashMap<String, Durability>();
	private HashMap<Material, DamageConfig> damages = new HashMap<Material, DamageConfig>();
	private static ServerVersion version;
	public enum ServerVersion {
		not_13,
		_13;
	}
	public DamageConfig getDamage(Material m) {
		return damages.get(m);
	}
	public static ServerVersion getServerVersion() {
		return version;
	}
	private ConfigStorage() {
		if (Bukkit.getVersion().contains(".13")) {
			Utility.sendConsole("&eYou are running server version: &f" + Bukkit.getBukkitVersion());
			version = ServerVersion._13;
		}
		else {
			version = ServerVersion.not_13;
		}
		reloadConfig();
	}
	public boolean containsDamage(Material mat) {
		return damages.containsKey(mat);
	}
	public boolean containsKey(String durabilitytype) {
		return durabilities.containsKey(durabilitytype);
	}
	public Set<String> getTypes() {
		return durabilities.keySet();
	}
	public void reloadConfig() {
		try {
		FileConfiguration config = customdurability.getInstance().getConfig();
		ConfigurationSection durs = config.getConfigurationSection("durability_types");
		ConfigurationSection notifier = config.getConfigurationSection("durability_notifier");
		Set<String> keys = durs.getKeys(false);
		for (String key : keys) {
			String not = notifier.getString(key);
			registerKey(key.toLowerCase(), Utility.TransColor(durs.getString(key)), not);
		}
		ConfigurationSection dmgs = config.getConfigurationSection("durabilities");
		/*
		 * Loads Tools Damages
		 */
		Byte dummybyte = Byte.valueOf("0");
		// TRIDENT
		if (version == ServerVersion._13) {
			try {
			int tridentdmg = dmgs.getInt("tridents.block_break");
			DamageConfig tridentcfg = new DamageConfig();
			tridentcfg.setBlockDmg(tridentdmg);
			registerDamage(XMaterial.TRIDENT.parseMaterial(), tridentcfg);
			} catch (Exception e) {
				e.printStackTrace();
				Utility.sendConsole("&eAn error occured loading tridents properties.");
			}
		}
		// ELYTRA
		int shdmg = dmgs.getInt("shield_item.flying_for_second");
		DamageConfig shconfig = new DamageConfig();
		shconfig.setFlintLitDmg(shdmg);
		registerDamage(XMaterial.SHIELD.parseMaterial(), shconfig);
		
		// ELYTRA
		int elydmg = dmgs.getInt("elytra_item.flying_for_second");
		DamageConfig elyconfig = new DamageConfig();
		elyconfig.setFlintLitDmg(elydmg);
		registerDamage(XMaterial.ELYTRA.parseMaterial(), elyconfig);
		// BOW
		int bowdmg = dmgs.getInt("bow_item.shooting_arrow");
		DamageConfig bowconfig = new DamageConfig();
		bowconfig.setBowShootDmg(bowdmg);
		registerDamage(XMaterial.BOW.parseMaterial(), bowconfig);
		
		// FLINT & STEL
		int flintdmg = dmgs.getInt("flint_and_steel.burn_up");
		DamageConfig flintconfig = new DamageConfig();
		flintconfig.setFlintLitDmg(flintdmg);
		registerDamage(XMaterial.FLINT_AND_STEEL.parseMaterial(), flintconfig);
		
		// CARROT ON STICK
		int boostdmg = dmgs.getInt("shears.shear_sheeps");
		DamageConfig carrotconfig = new DamageConfig();
		carrotconfig.setSheepShearDmg(boostdmg);
		registerDamage(XMaterial.SHEARS.parseMaterial(), carrotconfig);
		
		//FISHING ROD
		
		int frod_reel_item = dmgs.getInt("fishing_rod.item_reel");
		int frod_reel_empty = dmgs.getInt("fishing_rod.reeling_blocks");
		int frod_yank_item = dmgs.getInt("fishing_rod.yank_item");
		int frod_yank_mob = dmgs.getInt("fishing_rod.yank_mob");
		DamageConfig rodconfig = new DamageConfig();
		rodconfig.setFrod_Item_reel_dmg(frod_reel_item);
		rodconfig.setFrod_empty_reel_dmg(frod_reel_empty);
		rodconfig.setFrod_Yank_Entity_Dmg(frod_yank_mob);
		rodconfig.setFrod_Yank_Item_dmg(frod_yank_item);
		registerDamage(XMaterial.FISHING_ROD.parseMaterial(), rodconfig);
		//SWORD
		
		int sword_ehit = dmgs.getInt("swords_item.entity_hit");
		int sword_bbreak = dmgs.getInt("swords_item.block_break");
		DamageConfig swordconfig = new DamageConfig();
		swordconfig.setBlockDmg(sword_bbreak);
		swordconfig.setEntityDmg(sword_ehit);
		registerDamage(XMaterial.DIAMOND_SWORD.parseMaterial(), swordconfig);
		registerDamage(XMaterial.GOLDEN_SWORD.parseMaterial(), swordconfig);

		registerDamage(XMaterial.IRON_SWORD.parseMaterial(), swordconfig);

		registerDamage(XMaterial.STONE_SWORD.parseMaterial(), swordconfig);
		

		registerDamage(XMaterial.WOODEN_SWORD.parseMaterial(), swordconfig);

		// HOE
		
		int hoe_ehit = dmgs.getInt("hoes_item.entity_hit");
		int hoe_tiling = dmgs.getInt("hoes_item.tile_grass");
		DamageConfig hoeconfig = new DamageConfig();
		hoeconfig.setTilingDmg(hoe_tiling);
		hoeconfig.setEntityDmg(hoe_ehit);
		registerDamage(XMaterial.DIAMOND_HOE.parseMaterial(), hoeconfig);
		registerDamage(XMaterial.GOLDEN_HOE.parseMaterial(), hoeconfig);

		registerDamage(XMaterial.IRON_HOE.parseMaterial(), hoeconfig);

		registerDamage(XMaterial.STONE_HOE.parseMaterial(), hoeconfig);

		registerDamage(XMaterial.WOODEN_HOE.parseMaterial(), hoeconfig);

		
		// AXE
		
		int axe_bbreak = dmgs.getInt("axe_item.block_break");
		int axe_ehit = dmgs.getInt("axe_item.entity_hit");
		DamageConfig axeconfig = new DamageConfig();
		axeconfig.setBlockDmg(axe_bbreak);
		axeconfig.setEntityDmg(axe_ehit);
		registerDamage(XMaterial.DIAMOND_AXE.parseMaterial(), axeconfig);
		registerDamage(XMaterial.GOLDEN_AXE.parseMaterial(), axeconfig);
		registerDamage(XMaterial.IRON_AXE.parseMaterial(), axeconfig);
		registerDamage(XMaterial.STONE_AXE.parseMaterial(), axeconfig);
		registerDamage(XMaterial.WOODEN_AXE.parseMaterial(), axeconfig);
		
		// SHOVEL
		
		int shovel_bbreak = dmgs.getInt("shovel.block_break");
		int shovel_ehit = dmgs.getInt("shovel.entity_hit");
		int shovel_tile = dmgs.getInt("shovel.tile_grass");
		DamageConfig shovelconfig = new DamageConfig();
		shovelconfig.setTilingDmg(shovel_tile);
		shovelconfig.setBlockDmg(shovel_bbreak);
		shovelconfig.setEntityDmg(shovel_ehit);
		registerDamage(XMaterial.DIAMOND_SHOVEL.parseMaterial(), shovelconfig);
		registerDamage(XMaterial.GOLDEN_SHOVEL.parseMaterial(), shovelconfig);
		registerDamage(XMaterial.IRON_SHOVEL.parseMaterial(), shovelconfig);
		registerDamage(XMaterial.STONE_SHOVEL.parseMaterial(), shovelconfig);
		registerDamage(XMaterial.WOODEN_SHOVEL.parseMaterial(), shovelconfig);

		// PICKAXE
		
		int pickaxe_bbreak = dmgs.getInt("pickaxe.block_break");
		int pickaxe_ehit = dmgs.getInt("pickaxe.entity_hit");
		DamageConfig pickconfig = new DamageConfig();
		pickconfig.setBlockDmg(pickaxe_bbreak);
		pickconfig.setEntityDmg(pickaxe_ehit);
		registerDamage(XMaterial.DIAMOND_PICKAXE.parseMaterial(), pickconfig);
		registerDamage(XMaterial.GOLDEN_PICKAXE.parseMaterial(), pickconfig);
		registerDamage(XMaterial.IRON_PICKAXE.parseMaterial(), pickconfig);
		registerDamage(XMaterial.STONE_PICKAXE.parseMaterial(), pickconfig);
		registerDamage(XMaterial.WOODEN_PICKAXE.parseMaterial(), pickconfig);
		} catch (Exception e) {
			e.printStackTrace();
			Utility.sendConsole("[CD] &7There is a trouble trying to reload the config. Is it setted up correctly?");
		}
	}
	public void registerDamage(Material mat, DamageConfig conf) {
		damages.put(mat, conf);
	}
	public boolean hasKey(String key) {
		return durabilities.containsKey(key);
	}
	public Durability getDurability(String key) {
		return durabilities.get(key);
	}
	public static ConfigStorage getInstance() {
		if (instance == null) {
			instance = new ConfigStorage();
		}
		return instance;
	}
	public void registerKey(String key, String format, String notifier) {
		durabilities.put(key, new Durability(Utility.TransColor(format), notifier));
	}
	
}
