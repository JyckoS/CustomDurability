package com.gmail.JyckoSianjaya.customdurability.Events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.JyckoSianjaya.customdurability.Utility.NBT.NBTItem;

public class CDListener implements Listener {
	private CDEHandler handler = CDEHandler.getInstance();
	@EventHandler
	public void ManageBlockBreak(BlockBreakEvent e) {
		if (e.getPlayer().getItemInHand() == null) {
			return;
		}
		ItemStack pit = e.getPlayer().getItemInHand();
		if (pit.getType() == Material.AIR) return;
		NBTItem nbt = new NBTItem(pit);
		if (!nbt.hasKey("durabilitytype")) return;
		handler.manageBlockBreakEvent(e);
	}
	@EventHandler
	public void ManageEntityHit(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getDamager();
		if (p.getItemInHand() == null) {
			return;
		}
		ItemStack pit = p.getItemInHand();
		if (pit.getType() == Material.AIR) return;
		NBTItem nbt = new NBTItem(pit);
		if (!nbt.hasKey("durabilitytype")) return;
		handler.manageEntityHitEvent(e);
	}
}
