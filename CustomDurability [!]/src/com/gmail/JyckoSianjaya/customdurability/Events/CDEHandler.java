package com.gmail.JyckoSianjaya.customdurability.Events;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.JyckoSianjaya.customdurability.Utility.Utility;
import com.gmail.JyckoSianjaya.customdurability.Utility.XSound;
import com.gmail.JyckoSianjaya.customdurability.Utility.NBT.NBTItem;
import com.gmail.JyckoSianjaya.customdurability.damages.DamageConfig;
import com.gmail.JyckoSianjaya.customdurability.durability.Durability;
import com.gmail.JyckoSianjaya.customdurability.storage.ConfigStorage;

public class CDEHandler {
	private static CDEHandler instance;
	private ConfigStorage cfg = ConfigStorage.getInstance();
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
		ItemStack d = p.getItemInHand();
		if (!cfg.containsDamage(d.getType())) {
			return;
		}
		DamageConfig dg = cfg.getDamage(d.getType());
		int blockdmg = dg.getBlockDamage();
		if (blockdmg == 0) {
			return;
		}
		fixItem(d, p, blockdmg);
	}
	public void manageFishingReelItem(PlayerFishEvent e) {
		Player p = e.getPlayer();
		ItemStack it = p.getItemInHand();
		DamageConfig rodcfg = cfg.getDamage(it.getType());
		int mobreeldmg = rodcfg.getFrod_Yanks_item();
		if (mobreeldmg == 0) {
			return;
		}
		fixItem(it, p, mobreeldmg);
	}
	public void manageFishingEmptyReelEvent(PlayerFishEvent e) {
		Player p = e.getPlayer();
		ItemStack it = p.getItemInHand();
		DamageConfig rodcfg = cfg.getDamage(it.getType());
		int mobreeldmg = rodcfg.getFrod_reels_none();
		if (mobreeldmg == 0) {
			return;
		}
		fixItem(it, p, mobreeldmg);
	}
	public void manageFishingCaughtEvent(PlayerFishEvent e) {
		Player p = e.getPlayer();
		ItemStack it = p.getItemInHand();
		DamageConfig rodcfg = cfg.getDamage(it.getType());
		int mobreeldmg = rodcfg.getFrod_reels_item();
		if (mobreeldmg == 0) {
			return;
		}
		fixItem(it, p, mobreeldmg);
	}
	public void manageFishingReelEntityEvent(PlayerFishEvent e) {
		Player p = e.getPlayer();
		ItemStack it = p.getItemInHand();
		DamageConfig rodcfg = cfg.getDamage(it.getType());
		int mobreeldmg = rodcfg.getFrod_Yanks_entity();
		if (mobreeldmg == 0) {
			return;
		}
		fixItem(it, p, mobreeldmg);
	}
	public void manageEntityHitEvent(EntityDamageByEntityEvent e) {
		Player p = (Player) e.getDamager();
		ItemStack d = p.getItemInHand();
		DamageConfig dg = cfg.getDamage(d.getType());
		int hitdmg = dg.getEntityHitDmg();
		if (hitdmg == 0) {
			return;
		}
		fixItem(d, p, hitdmg);
	}
	public void manageBowshootEvent(EntityShootBowEvent e) {
		Player p = (Player) e.getEntity();
		ItemStack it = e.getBow();
		DamageConfig dg = cfg.getDamage(Material.BOW);
		int dmg = dg.getBowShootDmg();
		if (dmg == 0) return;
		fixItem(it, p, dmg);
	}
	public void manageFlintBurn(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		DamageConfig dg = cfg.getDamage(it.getType());
		int dmg = dg.getFlintBurnDmg();
		if (dmg == 0) return;
		fixItem(it, e.getPlayer(), dmg);
	}
	public void manageShearEvent(PlayerShearEntityEvent e) {
		Player p = (Player) e.getPlayer();
		ItemStack d = p.getItemInHand();
		DamageConfig dg = cfg.getDamage(d.getType());
		int hitdmg = dg.getShearSheepDmg();
		if (hitdmg == 0) {
			return;
		}
		fixItem(d, p, hitdmg);	}
	public void grassTiling(PlayerInteractEvent e) {
		ItemStack it = e.getItem();
		DamageConfig dg = cfg.getDamage(it.getType());
		int dmg = dg.getGrassTilingDmg();
		if (dmg == 0) return;
		fixItem(it, e.getPlayer(), dmg);
	}
	private void fixItem(ItemStack item, Player p, int totaldamage) {
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
		p.getInventory().setItemInHand(item);
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
