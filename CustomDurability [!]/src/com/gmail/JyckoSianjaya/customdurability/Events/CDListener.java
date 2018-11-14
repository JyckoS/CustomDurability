package com.gmail.JyckoSianjaya.customdurability.Events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.JyckoSianjaya.customdurability.Utility.XMaterial;
import com.gmail.JyckoSianjaya.customdurability.Utility.NBT.NBTItem;
import com.gmail.JyckoSianjaya.customdurability.storage.ConfigStorage;
import com.gmail.JyckoSianjaya.customdurability.storage.ConfigStorage.ServerVersion;

public class CDListener implements Listener {
	private CDEHandler handler = CDEHandler.getInstance();
	private Material grass = XMaterial.GRASS_BLOCK.parseMaterial();
	private ConfigStorage cfg = ConfigStorage.getInstance();
	
	@EventHandler(priority = EventPriority.HIGHEST)
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
	@EventHandler(priority = EventPriority.HIGHEST)
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
	public void bowShootEvent(EntityShootBowEvent e) {
		if (e.isCancelled()) return;
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		if (e.getBow() == null) {
			return;
		}
		NBTItem it = new NBTItem(e.getBow());
		if (!it.hasKey("durabilitytype")) {
			return;
		}
		handler.manageBowshootEvent(e);
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void flintBurnEvent(PlayerInteractEvent e) {
		if (e.isCancelled()) return;
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		if (e.getItem() == null) {
			return;
		}
		if (e.getItem().getType() != Material.FLINT_AND_STEEL) {
			return;
		}
		Block b = e.getClickedBlock();
		if (b.isLiquid()) return;
		NBTItem it = new NBTItem(e.getItem());
		if (!it.hasKey("durabilitytype")) {
			return;
		}
		handler.manageFlintBurn(e);
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
		if (mat == Material.DIRT) {
			if (mat.getMaxDurability() > 0) {
				return;
			}
		}
		NBTItem it = new NBTItem(e.getItem());
		if (!it.hasKey("durabilitytype")) {
			return;
		}
		handler.grassTiling(e);
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onSheepShear(PlayerShearEntityEvent e) {
		if (!(e.getEntity() instanceof Sheep)) {
			return;
		}
		final Player p = e.getPlayer();
		if (p.getItemInHand() == null) {
			return;
		}
		ItemStack pit = p.getItemInHand();
		if (pit.getType() == Material.AIR) return;
		NBTItem nbt = new NBTItem(pit);
		if (!nbt.hasKey("durabilitytype")) return;
		handler.manageShearEvent(e);
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onFishEvent(PlayerFishEvent e) {
		final Player p = e.getPlayer();
		if (p.getItemInHand() == null) {
			return;
		}
		ItemStack pit = p.getItemInHand();
		if (pit.getType() == Material.AIR) return;
		NBTItem nbt = new NBTItem(pit);
		if (!nbt.hasKey("durabilitytype")) return;
		State st = e.getState();
		Entity caught = e.getCaught();
		switch (st) {
		case CAUGHT_ENTITY:
			if (caught.getType() == EntityType.DROPPED_ITEM) {
				handler.manageFishingReelItem(e);
				return;
			}
			handler.manageFishingReelEntityEvent(e);
			return;
		case CAUGHT_FISH:
			handler.manageFishingCaughtEvent(e);
			return;
		case FAILED_ATTEMPT:
		case IN_GROUND:
			handler.manageFishingEmptyReelEvent(e);
			return;
		default:
			return;
		}
	}
}
