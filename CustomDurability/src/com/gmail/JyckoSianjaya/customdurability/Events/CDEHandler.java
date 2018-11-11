package com.gmail.JyckoSianjaya.customdurability.Events;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
	public void grassTiling(BlockFormEvent e) {
	}
	private void fixItem(ItemStack item, Player p, int totaldamage) {
		NBTItem nbt = new NBTItem(item);
		Durability dur = cfg.getDurability(nbt.getString("durabilitytype"));
		int curdur = nbt.getInteger("customdur.currentDur");
		if (curdur <= 0 && totaldamage > 0) {
			p.getInventory().remove(item);
			Utility.PlaySound(p, XSound.ITEM_BREAK.bukkitSound(), 2.0F, 0.5F);
			return;
		}
		int maxdur = nbt.getInteger("customdur.maxDur");
		curdur = curdur - totaldamage;
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
