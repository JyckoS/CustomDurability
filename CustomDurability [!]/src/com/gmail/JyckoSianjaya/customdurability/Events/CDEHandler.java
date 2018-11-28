package com.gmail.JyckoSianjaya.customdurability.Events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.JyckoSianjaya.customdurability.customdurability;
import com.gmail.JyckoSianjaya.customdurability.CustomEvents.CustomDurabilitySignRepairEvent;
import com.gmail.JyckoSianjaya.customdurability.CustomEvents.CustomDurabilityUsageEvent;
import com.gmail.JyckoSianjaya.customdurability.Utility.Utility;
import com.gmail.JyckoSianjaya.customdurability.Utility.XMaterial;
import com.gmail.JyckoSianjaya.customdurability.Utility.XSound;
import com.gmail.JyckoSianjaya.customdurability.Utility.NBT.NBTItem;
import com.gmail.JyckoSianjaya.customdurability.damages.DamageConfig;
import com.gmail.JyckoSianjaya.customdurability.durability.Durability;
import com.gmail.JyckoSianjaya.customdurability.magicanvil.MAGUIHolder;
import com.gmail.JyckoSianjaya.customdurability.magicanvil.MagicAnvilGUI;
import com.gmail.JyckoSianjaya.customdurability.magicanvil.RepairAmount;
import com.gmail.JyckoSianjaya.customdurability.repairsign.RepairCost;
import com.gmail.JyckoSianjaya.customdurability.repairsign.RepairCost.RepairCostType;
import com.gmail.JyckoSianjaya.customdurability.repairsign.RepairSign;
import com.gmail.JyckoSianjaya.customdurability.repairsign.RepairSign.RepairTarget;
import com.gmail.JyckoSianjaya.customdurability.storage.ConfigStorage;
import com.gmail.JyckoSianjaya.customdurability.storage.RepairStorage;

public class CDEHandler {
	private static CDEHandler instance;
	private ConfigStorage cfg = ConfigStorage.getInstance();
	private RepairStorage rst = RepairStorage.getInstance();
	private CDEHandler() {
	}
	public static CDEHandler getInstance() {
		if (instance == null) {
			instance = new CDEHandler();
		}
		return instance;
	}
	public void manageBlockBreakEvent(BlockBreakEvent e) {
		Player p = e.getPlayer();
		ItemStack item = p.getItemInHand();
		if (item == null || item.getType() == Material.AIR) return;
		DamageConfig config = cfg.getDamage(item.getType());
		if (config.getBlockDamage() == 0) return;
		fixItem(item, p, config.getBlockDamage(), PlayerHand.RIGHT);
	}
	public void manageFishingReelItem(PlayerFishEvent e) {
		Player p = e.getPlayer();
		PlayerInventory inv = p.getInventory();
		Boolean rightnull = false;
		Boolean leftnull = false;
		ItemStack main = inv.getItemInMainHand();
		if (main == null || main.getType() == Material.AIR) rightnull = true;
		ItemStack off = inv.getItemInOffHand();
		if (off == null || off.getType() == Material.AIR) leftnull = true;
		Boolean rightvalid = false;
		Boolean offvalid = false;
		NBTItem mainnbt = null;
		if (!rightnull) {
			mainnbt = new NBTItem(main);
		}
		NBTItem offnbt = null;
		if (!leftnull) offnbt = new NBTItem(off);
		if (!rightnull) {
		if (mainnbt.hasKey("durabilitytype")) {
			if (main.getType() == rod) {
				rightvalid = true;
				}
			}
		}
		if (!leftnull) {
			if (offnbt.hasKey("durabilitytype")) {
				if (off.getType() == rod) {
					offvalid = true;
				}
			}
		}
		if (rightvalid && offvalid || rightvalid) {
			DamageConfig rightconfig = cfg.getDamage(main.getType());
			fixItem(main, p, rightconfig.getFrod_Yanks_item(), PlayerHand.RIGHT);
			return;
		}
		if (offvalid) {
			DamageConfig leftconfig = cfg.getDamage(off.getType()); 
			fixItem(off, p, leftconfig.getFrod_Yanks_item(), PlayerHand.OFFHAND);
			return;
		}
	}
	public void manageFishingEmptyReelEvent(PlayerFishEvent e) {
		Player p = e.getPlayer();
		PlayerInventory inv = p.getInventory();
		Boolean rightnull = false;
		Boolean leftnull = false;
		ItemStack main = inv.getItemInMainHand();
		if (main == null || main.getType() == Material.AIR) rightnull = true;
		ItemStack off = inv.getItemInOffHand();
		if (off == null || off.getType() == Material.AIR) leftnull = true;
		Boolean rightvalid = false;
		Boolean offvalid = false;
		NBTItem mainnbt = null;
		if (!rightnull) {
			mainnbt = new NBTItem(main);
		}
		NBTItem offnbt = null;
		if (!leftnull) offnbt = new NBTItem(off);
		if (!rightnull) {
		if (mainnbt.hasKey("durabilitytype")) {
			if (main.getType() == rod) {
				rightvalid = true;
				}
			}
		}
		if (!leftnull) {
			if (offnbt.hasKey("durabilitytype")) {
				if (off.getType() == rod) {
					offvalid = true;
				}
			}
		}
		if (rightvalid && offvalid || rightvalid) {
			DamageConfig rightconfig = cfg.getDamage(main.getType());
			fixItem(main, p, rightconfig.getFrod_reels_none(), PlayerHand.RIGHT);
			return;
		}
		if (offvalid) {
			DamageConfig leftconfig = cfg.getDamage(off.getType()); 
			fixItem(off, p, leftconfig.getFrod_reels_none(), PlayerHand.OFFHAND);
			return;
		}
	}
	public void manageFishingCaughtEvent(PlayerFishEvent e) {
		Player p = e.getPlayer();
		PlayerInventory inv = p.getInventory();
		Boolean rightnull = false;
		Boolean leftnull = false;
		ItemStack main = inv.getItemInMainHand();
		if (main == null || main.getType() == Material.AIR) rightnull = true;
		ItemStack off = inv.getItemInOffHand();
		if (off == null || off.getType() == Material.AIR) leftnull = true;
		Boolean rightvalid = false;
		Boolean offvalid = false;
		NBTItem mainnbt = null;
		if (!rightnull) {
			mainnbt = new NBTItem(main);
		}
		NBTItem offnbt = null;
		if (!leftnull) offnbt = new NBTItem(off);
		if (!rightnull) {
		if (mainnbt.hasKey("durabilitytype")) {
			if (main.getType() == rod) {
				rightvalid = true;
				}
			}
		}
		if (!leftnull) {
			if (offnbt.hasKey("durabilitytype")) {
				if (off.getType() == rod) {
					offvalid = true;
				}
			}
		}
		if (rightvalid && offvalid || rightvalid) {
			DamageConfig rightconfig = cfg.getDamage(main.getType());
			fixItem(main, p, rightconfig.getFrod_reels_item(), PlayerHand.RIGHT);
			return;
		}
		if (offvalid) {
			DamageConfig leftconfig = cfg.getDamage(off.getType()); 
			fixItem(off, p, leftconfig.getFrod_reels_item(), PlayerHand.OFFHAND);
			return;
		}
	}
	Material rod = XMaterial.FISHING_ROD.parseMaterial();
	public void manageFishingReelEntityEvent(PlayerFishEvent e) {
		Player p = e.getPlayer();
		PlayerInventory inv = p.getInventory();
		Boolean rightnull = false;
		Boolean leftnull = false;
		ItemStack main = inv.getItemInMainHand();
		if (main == null || main.getType() == Material.AIR) rightnull = true;
		ItemStack off = inv.getItemInOffHand();
		if (off == null || off.getType() == Material.AIR) leftnull = true;
		Boolean rightvalid = false;
		Boolean offvalid = false;
		NBTItem mainnbt = null;
		if (!rightnull) {
			mainnbt = new NBTItem(main);
		}
		NBTItem offnbt = null;
		if (!leftnull) offnbt = new NBTItem(off);
		if (!rightnull) {
		if (mainnbt.hasKey("durabilitytype")) {
			if (main.getType() == rod) {
				rightvalid = true;
				}
			}
		}
		if (!leftnull) {
			if (offnbt.hasKey("durabilitytype")) {
				if (off.getType() == rod) {
					offvalid = true;
				}
			}
		}
		if (rightvalid && offvalid || rightvalid) {
			DamageConfig rightconfig = cfg.getDamage(main.getType());
			fixItem(main, p, rightconfig.getFrod_Yanks_entity(), PlayerHand.RIGHT);
			return;
		}
		if (offvalid) {
			DamageConfig leftconfig = cfg.getDamage(off.getType()); 
			fixItem(off, p, leftconfig.getFrod_Yanks_entity(), PlayerHand.OFFHAND);
			return;
		}
	}
	public enum PlayerHand {
		OFFHAND,
		RIGHT;
	}
	public void manageEntityHitEvent(EntityDamageByEntityEvent e) {
		Player p = (Player) e.getDamager();
		PlayerInventory inv = p.getInventory();
		ItemStack item = p.getItemInHand();
		if (item == null) return;
		DamageConfig config = cfg.getDamage(item.getType());
		if (config.getEntityHitDmg() == 0) return;
		fixItem(item, p, config.getEntityHitDmg(), PlayerHand.RIGHT);
	}
	public void manageBowshootEvent(EntityShootBowEvent e) {
		Player p = (Player) e.getEntity();
		ItemStack bow = e.getBow();
		PlayerInventory inv = p.getInventory();
		Boolean rightnull = false;
		Boolean leftnull = false;
		ItemStack main = inv.getItemInMainHand();
		if (main == null || main.getType() == Material.AIR) rightnull = true;
		ItemStack off = inv.getItemInOffHand();
		if (off == null || off.getType() == Material.AIR) leftnull = true;
		Boolean rightvalid = false;
		Boolean offvalid = false;
		NBTItem mainnbt = null;
		if (!rightnull) {
			mainnbt = new NBTItem(main);
		}
		NBTItem offnbt = null;
		if (!leftnull) offnbt = new NBTItem(off);
		if (!rightnull) {
		if (mainnbt.hasKey("durabilitytype")) {
			if (main.equals(bow)) {
				rightvalid = true;
				}
			}
		}
		if (!leftnull) {
			if (offnbt.hasKey("durabilitytype")) {
				if (off.equals(bow)) {
					offvalid = true;
				}
			}
		}
		if (rightvalid && offvalid || rightvalid) {
			DamageConfig rightconfig = cfg.getDamage(main.getType());
			fixItem(main, p, rightconfig.getBowShootDmg(), PlayerHand.RIGHT);
			return;
		}
		if (offvalid) {
			DamageConfig leftconfig = cfg.getDamage(off.getType()); 
			fixItem(off, p, leftconfig.getBowShootDmg(), PlayerHand.OFFHAND);
			return;
		}
	}
	public void manageAnvilClaim(InventoryClickEvent e) {
		Inventory inv = e.getClickedInventory();
		ItemStack click = e.getCurrentItem();
		if (click == null) return;
		e.setCancelled(true);
		PlayerInventory pinv = e.getWhoClicked().getInventory();
		if (Utility.isEmpty(pinv)) {
			pinv.addItem(click);
		}
		else {
			Utility.sendActionBar((Player) e.getWhoClicked(), "&cYour inventory is &c&lfull&c!");
			return;
		}
		MagicAnvilGUI.setBack(inv);
		Utility.PlaySound((Player) e.getWhoClicked(), XSound.LEVEL_UP.bukkitSound(), 2.0F, 2.0F);
	}
	public void manageFlintBurn(PlayerInteractEvent e) {
		Player p = (Player) e.getPlayer();
		ItemStack bow = e.getItem();
		PlayerInventory inv = p.getInventory();
		Boolean rightnull = false;
		Boolean leftnull = false;
		ItemStack main = inv.getItemInMainHand();
		if (main == null || main.getType() == Material.AIR) rightnull = true;
		ItemStack off = inv.getItemInOffHand();
		if (off == null || off.getType() == Material.AIR) leftnull = true;
		Boolean rightvalid = false;
		Boolean offvalid = false;
		NBTItem mainnbt = null;
		if (!rightnull) {
			mainnbt = new NBTItem(main);
		}
		NBTItem offnbt = null;
		if (!leftnull) offnbt = new NBTItem(off);
		if (!rightnull) {
		if (mainnbt.hasKey("durabilitytype")) {
			if (main.equals(bow)) {
				rightvalid = true;
				}
			}
		}
		if (!leftnull) {
			if (offnbt.hasKey("durabilitytype")) {
				if (off.equals(bow)) {
					offvalid = true;
				}
			}
		}
		if (rightvalid && offvalid || rightvalid) {
			DamageConfig rightconfig = cfg.getDamage(main.getType());
			fixItem(main, p, rightconfig.getFlintBurnDmg(), PlayerHand.RIGHT);
			return;
		}
		if (offvalid) {
			DamageConfig leftconfig = cfg.getDamage(off.getType()); 
			fixItem(off, p, leftconfig.getFlintBurnDmg(), PlayerHand.OFFHAND);
			return;
		}
	}
	private Material shear = XMaterial.SHEARS.parseMaterial();
	public void manageShearEvent(PlayerShearEntityEvent e) {
		Player p = (Player) e.getPlayer();
		PlayerInventory inv = p.getInventory();
		Boolean rightnull = false;
		Boolean leftnull = false;
		ItemStack main = inv.getItemInMainHand();
		if (main == null || main.getType() == Material.AIR) rightnull = true;
		ItemStack off = inv.getItemInOffHand();
		if (off == null || off.getType() == Material.AIR) leftnull = true;
		Boolean rightvalid = false;
		Boolean offvalid = false;
		NBTItem mainnbt = null;
		if (!rightnull) {
			mainnbt = new NBTItem(main);
		}
		NBTItem offnbt = null;
		if (!leftnull) offnbt = new NBTItem(off);
		if (!rightnull) {
		if (mainnbt.hasKey("durabilitytype")) {
			if (main.getType() == shear) {
				rightvalid = true;
				}
			}
		}
		if (!leftnull) {
			if (offnbt.hasKey("durabilitytype")) {
				if (off.getType() == shear) {
					offvalid = true;
				}
			}
		}
		if (rightvalid && offvalid || rightvalid) {
			DamageConfig rightconfig = cfg.getDamage(main.getType());
			fixItem(main, p, rightconfig.getShearSheepDmg(), PlayerHand.RIGHT);
			return;
		}
		if (offvalid) {
			DamageConfig leftconfig = cfg.getDamage(off.getType()); 
			fixItem(off, p, leftconfig.getShearSheepDmg(), PlayerHand.OFFHAND);
			return;
		}
	}
	public void grassTiling(PlayerInteractEvent e) {
		Player p = (Player) e.getPlayer();
		ItemStack bow = e.getItem();
		PlayerInventory inv = p.getInventory();
		Boolean rightnull = false;
		Boolean leftnull = false;
		ItemStack main = inv.getItemInMainHand();
		if (main == null || main.getType() == Material.AIR) rightnull = true;
		ItemStack off = inv.getItemInOffHand();
		if (off == null || off.getType() == Material.AIR) leftnull = true;
		Boolean rightvalid = false;
		Boolean offvalid = false;
		NBTItem mainnbt = null;
		if (!rightnull) {
			mainnbt = new NBTItem(main);
		}
		NBTItem offnbt = null;
		if (!leftnull) offnbt = new NBTItem(off);
		if (!rightnull) {
		if (mainnbt.hasKey("durabilitytype")) {
			if (main.equals(bow)) {
				rightvalid = true;
				}
			}
		}
		if (!leftnull) {
			if (offnbt.hasKey("durabilitytype")) {
				if (off.equals(bow)) {
					offvalid = true;
				}
			}
		}
		if (rightvalid && offvalid || rightvalid) {
			DamageConfig rightconfig = cfg.getDamage(main.getType());
			fixItem(main, p, rightconfig.getGrassTilingDmg(), PlayerHand.RIGHT);
			return;
		}
		if (offvalid) {
			DamageConfig leftconfig = cfg.getDamage(off.getType()); 
			fixItem(off, p, leftconfig.getGrassTilingDmg(), PlayerHand.OFFHAND);
			return;
		}
	}
	public void manageAnvilClose(InventoryCloseEvent e) {
		Inventory inv = e.getInventory();
		ItemStack ite1 = inv.getItem(10);
		ItemStack ite2 = inv.getItem(13);
		ItemStack ite3 = inv.getItem(16);
		HumanEntity p = e.getPlayer();
		World w = p.getWorld();
		if (ite3 != null && !ite3.getType().toString().contains("ANVIL")) {
			w.dropItem(p.getLocation().add(0, 1.0, 0.0), ite3);
		}
		if (ite1 != null) {
		w.dropItem(p.getLocation().add(0, 1.0, 0.0), ite1);
	}
		if (ite2 != null) {
		w.dropItem(p.getLocation().add(0, 1.0, 0.0), ite2);
		}
	}
	public void manageAnvilStartForge(final InventoryClickEvent e) {
		ItemStack firstitem = e.getInventory().getItem(10);
		final ItemStack material = e.getInventory().getItem(13);
		final ItemStack result = e.getCurrentItem();
		e.setCancelled(true);
		if (result != null) {
			if (!result.getType().toString().contains("ANVIL")) {
			Utility.sendActionBar((Player) e.getWhoClicked(), "&cSomething is blocking the repair process!");
			Utility.PlaySound((Player) e.getWhoClicked(), villagernah, 0.7F, 1.3F);
			e.setCancelled(true);
			return;
			}
		}
		if (firstitem == null || material == null) {
			Utility.sendActionBar((Player) e.getWhoClicked(), "&cSomething is missing..");
			Utility.PlaySound((Player) e.getWhoClicked(), villagernah, 0.7F, 1.3F);
			return;
		}
		final Inventory inv = e.getInventory();
		final NBTItem nbt1 = new NBTItem(firstitem);
		if (!nbt1.hasKey("durabilitytype")) return;
		if (!rst.getRepairables(firstitem.getType()).isRepairable(material.getType())) {
			Utility.sendActionBar((Player) e.getWhoClicked(), "&cCan't repair with that!");
			return;
		}
		if (!rst.hasRepairAmount(material.getType())) return;
		final RepairAmount amount = rst.getRepairAmount(material.getType());
		final int curdur = nbt1.getInteger("customdur.currentDur");
		final int maxdur = nbt1.getInteger("customdur.maxDur");
		int finale = amount.getFinalRepairAmount(curdur, maxdur);
		if (finale > maxdur) {
			finale = maxdur;
		}
		int torepair = finale + curdur;
		if (torepair > maxdur) torepair = maxdur;
		nbt1.setInteger("customdur.currentDur", torepair);
		firstitem = nbt1.getItem();
		ArrayList<String> lorez = new ArrayList<String>(firstitem.getItemMeta().getLore());
		int targets = 0;
		Durability dtype = cfg.getDurability(nbt1.getString("durabilitytype"));
		for (String str : lorez) {
			if (!str.contains(dtype.getEmptyFormat())) {
				targets++;
				continue;
			}
			break;
		}
		lorez.set(targets - 1, dtype.getFormatted(curdur + finale, maxdur));
		final ItemMeta meta = firstitem.getItemMeta();
		meta.setLore(lorez);
		firstitem.setItemMeta(meta);
		MagicAnvilGUI.setDone(inv);
		inv.setItem(16, firstitem);
		inv.setItem(10, null);
		inv.setItem(13, null);
		for (final HumanEntity enty : e.getViewers()) {
			Utility.PlaySound((Player) enty, XSound.ANVIL_USE.bukkitSound(), 0.5F, 2.0F);
		}
	}
	private final Sound villagernah = XSound.VILLAGER_NO.bukkitSound();
	public void manageAnvilPlaceMaterial(InventoryClickEvent e) {
		ItemStack it = e.getCursor();
		if (it == null) return;
		if (it.getType().toString().contains("AIR")) return;
		if (!rst.hasRepairAmount(it.getType())) {
			e.setCancelled(true);
			Utility.sendActionBar((Player) e.getWhoClicked(), "&cThat has no repair amount!");
			Utility.PlaySound((Player) e.getWhoClicked(), villagernah, 0.7F, 0.8F);
			return;
		}
		if (e.getInventory().getItem(10) == null) {
			e.setCancelled(true);
			Utility.sendActionBar((Player) e.getWhoClicked(), "&cThere's nothing to repair!");
			Utility.PlaySound((Player) e.getWhoClicked(), villagernah, 0.7F, 1.3F);
			return;
		}
		if (it.getAmount() > 1) {
			ItemStack clon = it.clone();
			clon.setAmount(it.getAmount() - 1);
			e.setCursor(clon);
			it.setAmount(1);
		}
		e.setCancelled(true);
		Inventory inv = e.getClickedInventory();
		inv.setItem(e.getSlot(), it);
		Utility.PlaySound((Player) e.getWhoClicked(), placeitem, 0.5F, 1.8F);

	}
	private final Sound placeitem = XSound.ANVIL_LAND.bukkitSound();
	public void manageAnvilPlaceItem(final InventoryClickEvent e) {
		ItemStack it = e.getCursor();
		if (it == null) return;
		if (it.getType().toString().contains("AIR")) return;
		NBTItem nbt = new NBTItem(it);
		if (!nbt.hasKey("durabilitytype")) {
			e.setCancelled(true);
			return;
		}
		Utility.PlaySound((Player) e.getWhoClicked(), placeitem, 0.5F, 1.8F);
	}
	public void manageAnvilOpening(final PlayerInteractEvent e) {
		e.setCancelled(true);
		Player p = e.getPlayer();
		Inventory anv = MagicAnvilGUI.getGUI();
		p.openInventory(anv);
		Utility.PlaySound(p, Sound.BLOCK_ANVIL_PLACE, 0.5F, 1.58F);
		Utility.sendActionBar(p, "&7Opening &b&lMagic Anvil&7..");
	}
	/*
	public void manageShieldBlockEvent(EntityDamageByEntityEvent e) {
		Player p = (Player) e.getEntity();
		DamageConfig shc = cfg.getDamage(XMaterial.SHIELD.parseMaterial());
		int dmg = shc.getBlockDamage();
		if (dmg == 0) return;
		Boolean hasShieldhand = false;
		Boolean hasShieldoff = false;
		ItemStack it = p.getInventory().getItemInHand();
		if (it != null) {
			if (it.getType() == Material.SHIELD) hasShieldhand = true;
		}
		ItemStack off = p.getInventory().getItemInOffHand();
		if (off != null) {
			if (off.getType() == Material.SHIELD) hasShieldoff = true;
		}
		if (hasShieldhand && hasShieldoff) {
			fixItem(off, p, dmg);
			return;
		}
		if (hasShieldhand) {
			fixItem(it, p, dmg);
			return;
		}
		if (hasShieldoff) {
			fixItem(off, p, dmg);
			return;
		}
		return;
	}
	*/
	public void manageSignRepair(RepairSign s, PlayerInteractEvent e) {
		CustomDurabilitySignRepairEvent event = new CustomDurabilitySignRepairEvent(e.getPlayer(), s);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled()) return;
		Player p = e.getPlayer();
		RepairTarget target = s.getTarget();
		RepairCost cost = s.getCost();
		RepairCostType costtype = cost.getRepairCostType();
		int costamount = cost.getAmount();
		HashMap<Integer, NBTItem> torepair = new HashMap<Integer, NBTItem>();
		Boolean ishandonly = false;
		switch (target) {
		case ALL: {
			PlayerInventory inv = p.getInventory();
			int amountrepair = 0;
			// Reminder: This Loops from 0 to 35. 36 doesn't count
			for (int i = 0; i < 36; i++) {
				ItemStack it = inv.getItem(i);
				if (it == null || it.getType() == Material.AIR) {
					continue;
				}
				NBTItem itm = new NBTItem(it);
				if (!itm.hasKey("durabilitytype")) {
					continue;
				}
				amountrepair++;
				torepair.put(i, itm);
			}
			for (int i = 80; i < 84; i++) {
				ItemStack it = inv.getItem(i);
				if (it == null || it.getType() == Material.AIR) {
					continue;
				}
				NBTItem itm = new NBTItem(it);
				if (!itm.hasKey("durabilitytype")) {
					continue;
				}
				amountrepair++;
				torepair.put(i, itm);
			}
			for (int i = 100; i < 104; i++) {
				ItemStack it = inv.getItem(i);
				if (it == null || it.getType() == Material.AIR) {
					continue;
				}
				NBTItem itm = new NBTItem(it);
				if (!itm.hasKey("durabilitytype")) {
					continue;
				}
				amountrepair++;
				torepair.put(i, itm);
			}
			if (amountrepair == 0) {
				Utility.sendMsg(p, "&cThere's nothing to be repaired in your inventory!");
				return;
			}
			break;
		}
		case HAND: {
			ItemStack inhand = e.getItem();
			if (inhand == null || inhand.getType() == Material.AIR) {
				Utility.sendMsg(p, "&cWooopsy! &7You can't repair those!");
				return;
			}
			NBTItem it = new NBTItem(inhand);
			if (!it.hasKey("durabilitytype")) {
				Utility.sendMsg(p, "&cYou can't repair that item!");
				return;
			}
			ishandonly = true;
			break;
		}
		default:
			return;
		}
		switch (costtype) {
		case None:
			break;
		case XP:
		{
			int xps = p.getTotalExperience();
			if (xps >= costamount) {
				p.setTotalExperience(xps - costamount);
				Utility.PlaySound(p, XSound.DRINK.bukkitSound(), 2.0F, 0.0F);
				break;
			}
			Utility.sendMsg(p, "&7Your Experience is not enough! &8(Requires: &b" + costamount + " EXP&8)");
			return;
		}
		case XPLevel:
		{
			int levels = p.getExpToLevel();
			if (levels >= costamount) {
				p.setLevel(levels - costamount);
				Utility.PlaySound(p, XSound.EAT.bukkitSound(), 2.0F, 0.0F);
				break;
			}
			Utility.sendMsg(p, "&7Your XP levels is not enough! &8(Requires: &b" + costamount + "XP Levels&8)");
			return;
		}
		case Money:
		{
			double money = customdurability.getEconomy().getBalance(p);
			if (money >= costamount) {
				customdurability.getEconomy().withdrawPlayer(p, costamount);
				Utility.PlaySound(p, XSound.ENDERDRAGON_WINGS.bukkitSound(), 2.0F, 2.0F);
				break;
			}
			Utility.sendMsg(p, "&7You do not have enough money! &8(Requires: &a" + cfg.getCurrency() + costamount + "&8)");
		}
		}
		if (!ishandonly) {
			PlayerInventory inv = p.getInventory();
			for (int i : torepair.keySet()) {
				NBTItem it = torepair.get(i);
				it.setInteger("customdur.currentDur", it.getInteger("customdur.maxDur"));
				int max = it.getInteger("customdur.maxDur");
				ItemStack items = it.getItem();
				ArrayList<String> lorez = new ArrayList<String>(items.getItemMeta().getLore());
				int targets = 0;
				Durability dtype = cfg.getDurability(it.getString("durabilitytype"));
				for (String str : lorez) {
					if (!str.contains(dtype.getEmptyFormat())) {
						targets++;
						continue;
					}
					break;
				}
				lorez.set(targets - 1, dtype.getFormatted(max, max));
				ItemMeta meta = items.getItemMeta();
				meta.setLore(lorez);
				items.setItemMeta(meta);
				inv.setItem(i, items);
			}
			Utility.sendMsg(p, "&aSuccessfully repaired all item!");
		}
		else {
			PlayerInventory inv = p.getInventory();
			NBTItem it = new NBTItem(inv.getItemInMainHand());
			it.setInteger("customdur.currentDur", it.getInteger("customdur.maxDur"));
			int max = it.getInteger("customdur.maxDur");
			ItemStack items = it.getItem();
			ArrayList<String> lorez = new ArrayList<String>(items.getItemMeta().getLore());
			int targets = 0;
			Durability dtype = cfg.getDurability(it.getString("durabilitytype"));
			for (String str : lorez) {
				if (!str.contains(dtype.getEmptyFormat())) {
					targets++;
					continue;
				}
				break;
			}
			lorez.set(targets - 1, dtype.getFormatted(max, max));
			ItemMeta meta = items.getItemMeta();
			meta.setLore(lorez);
			items.setItemMeta(meta);
			inv.setItemInMainHand(items);
			Utility.sendMsg(p, "&aSuccessfully repaired item in your hand!");
		}
		Utility.PlaySound(p, XSound.ANVIL_USE.bukkitSound(), 2.0F, 1.2F);
		e.setCancelled(true);
		return;
	}
	public void manageSignCreation(RepairSign s, SignChangeEvent e) {
		RepairTarget target = s.getTarget();
		RepairCost c = s.getCost();
		Sign sign = s.getSign();
		e.setLine(0, Utility.TransColor("&6&l• &1&l[Repairs] &6&l•"));
		e.setLine(1, Utility.TransColor("Repairs: &l" + target.toString()));
		Utility.PlaySoundAt(sign.getWorld(), sign.getLocation(), XSound.FIREWORK_LAUNCH.bukkitSound(), 3.0F, 0.5F);
		if (c.getAmount() == 0) return;
		if (c.getRepairCostType() == null) return;
		RepairCostType type = c.getRepairCostType();
		switch (type) {
		case Money:
			e.setLine(2, Utility.TransColor("Cost: "));
			e.setLine(3, Utility.TransColor("&o" + cfg.getCurrency() + "&0&l " + c.getAmount()));
			break;
		case XP:
			e.setLine(2, Utility.TransColor("Cost: "));
			e.setLine(3, Utility.TransColor(c.getAmount() + "&1&lXP &1"));
			break;
		case XPLevel:
			e.setLine(2, Utility.TransColor("Cost: "));
			e.setLine(3, Utility.TransColor(c.getAmount() + "&1&lXP Levels"));
			break;
		default: break;
		}
	}
	public void manageTabComplete(PlayerChatTabCompleteEvent e, String[] cmds) {
		switch (cmds[1]) {
		case "add":
		case "addrepair":
		default:
			break;
		}
	}
	private void fixItem(ItemStack item, Player p, int totaldamage, PlayerHand hand) {
		CustomDurabilityUsageEvent event = new CustomDurabilityUsageEvent(p, item, totaldamage, hand);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled()) return;
		totaldamage = event.getTotalDamage();
		if (item.containsEnchantment(Enchantment.DURABILITY)) {
			int level = item.getEnchantmentLevel(Enchantment.DURABILITY);
			int chance = 100 / level + 1;
			Random rand = new Random();
			int realchan = rand.nextInt(99);
			if (chance <= realchan) {
				totaldamage = 0;
			}
		}
		NBTItem nbt = new NBTItem(item);
		Durability dur = cfg.getDurability(nbt.getString("durabilitytype"));
		int curdur = nbt.getInteger("customdur.currentDur");
		if (curdur <= totaldamage) {
			p.getInventory().remove(item);
			Utility.PlaySound(p, XSound.ITEM_BREAK.bukkitSound(), 2.0F, 0.7F);
			return;
		}
		int maxdur = nbt.getInteger("customdur.maxDur");
		curdur = curdur - totaldamage;
		if (dur.hasNotifier()) {
		Utility.sendActionBar(p, dur.getActionBar(curdur, maxdur));
		}
		int atlore = 0;
		nbt.setInteger("customdur.currentDur", curdur);
		ArrayList<String> lores = new ArrayList<String>(item.getItemMeta().getLore());
		String empty = dur.getEmptyFormat();
		for (String str : item.getItemMeta().getLore()) {
			if (!str.contains(empty)) {
				atlore++;
				continue;
			}
			break;
		}
		item = nbt.getItem();
		ItemMeta meta = item.getItemMeta();
		String newlore = dur.getFormatted(curdur, maxdur);
		lores.set(atlore - 1, newlore);
		meta.setLore(lores);
		item.setItemMeta(meta);
		int durab = item.getDurability();
		int maxdurab = item.getType().getMaxDurability();
		double percentage = curdur / maxdur;
		if (percentage > 1) percentage = 1;
		durab = (int) (maxdurab * percentage);
		item.setDurability(Short.valueOf("" + durab));
		if (hand == PlayerHand.RIGHT) {
		p.getInventory().setItemInMainHand(item);
		return;
		}
		p.getInventory().setItemInOffHand(item);
	}
	/*
	 * NBT DATA
	 * 
	 * "durabilitytype" returns String of Durability Type Name
	 * 
	 * "customdur.currentDur" returns Current Custom Durability
	 * "customdur.maxDur" returns Maximum Durability
	 */
}
