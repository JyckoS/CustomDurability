package com.gmail.JyckoSianjaya.customdurability.Events;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.JyckoSianjaya.customdurability.Utility.XMaterial;
import com.gmail.JyckoSianjaya.customdurability.Utility.NBT.NBTItem;

public class CDListener implements Listener {
	private CDEHandler handler = CDEHandler.getInstance();
	private Material grass = XMaterial.GRASS_BLOCK.parseMaterial();
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
	@EventHandler(priority = EventPriority.HIGHEST)
	public void rightclickEvent(PlayerInteractEvent e) {
		if (e.isCancelled()) return;
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		if (e.getItem() == null) {
			return;
		}
		if (e.getItem().getType() == Material.AIR) {
			return;
		}
		String mattype = e.getItem().getType().toString();
		if (!mattype.contains("HOE") && !mattype.contains("SHOVEL")) {
			return;
		}
		Material mat = e.getClickedBlock().getType();
		if (mat != Material.DIRT && mat != grass) {
			return;
		}
		NBTItem it = new NBTItem(e.getItem());
		if (!it.hasKey("durabilitytype")) {
			return;
		}
		handler.grassTiling(e);
	}
}
