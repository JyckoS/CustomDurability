package com.gmail.JyckoSianjaya.customdurability.Events;

import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.gmail.JyckoSianjaya.customdurability.Utility.Utility;
import com.gmail.JyckoSianjaya.customdurability.Utility.XMaterial;
import com.gmail.JyckoSianjaya.customdurability.Utility.NBT.NBTItem;
import com.gmail.JyckoSianjaya.customdurability.magicanvil.MAGUIHolder;
import com.gmail.JyckoSianjaya.customdurability.repairsign.RepairCost;
import com.gmail.JyckoSianjaya.customdurability.repairsign.RepairCost.RepairCostType;
import com.gmail.JyckoSianjaya.customdurability.repairsign.RepairSign;
import com.gmail.JyckoSianjaya.customdurability.repairsign.RepairSign.RepairTarget;
import com.gmail.JyckoSianjaya.customdurability.storage.ConfigStorage;
import com.gmail.JyckoSianjaya.customdurability.storage.ConfigStorage.ServerVersion;

public class CDListener implements Listener {
	private CDEHandler handler = CDEHandler.getInstance();
	private Material grass = XMaterial.GRASS_BLOCK.parseMaterial();
	private Material shieldmat = XMaterial.SHIELD.parseMaterial();
	private Material podzol = XMaterial.PODZOL.parseMaterial();
	private Material signmat = XMaterial.WALL_SIGN.parseMaterial();
	private Material standsignmat = XMaterial.SIGN.parseMaterial();
	private ConfigStorage cfg = ConfigStorage.getInstance();
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void ManageBlockBreak(BlockBreakEvent e) {
		PlayerInventory inv = e.getPlayer().getInventory();
		if (inv.getItemInMainHand() == null) {
			return;
		}
		ItemStack pit = inv.getItemInHand();
		if (pit.getType() == Material.AIR) return;
		NBTItem nbt = new NBTItem(pit);
		if (!nbt.hasKey("durabilitytype")) return;
		handler.manageBlockBreakEvent(e);
	}
/*	
 *      Shield Event
 * 
 * @EventHandler(priority = EventPriority.HIGHEST)
	public void ManageEntityDefend(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getEntity();
		if (!p.isHandRaised()) return;
		PlayerInventory inv = p.getInventory();
		if (inv.getItemInHand() == null && inv.getItemInOffHand() == null) {
			return;
		}
		ItemStack it = inv.getItemInOffHand();
		ItemStack pit = inv.getItemInHand();
		if (pit.getType() != shieldmat && it.getType() != shieldmat) return;
		NBTItem nbt = new NBTItem(pit);
		if (!nbt.hasKey("durabilitytype")) return;
		handler.manageShieldBlockEvent(e);
		
	}
	*/
	
	
	/*
	 *  TAB COMPLETE EVENT
	 *  @EventHandler(priority = EventPriority.HIGHEST)
	public void manageTabComplete(PlayerChatTabCompleteEvent e) {
		if (e.getPlayer().hasPermission("customdurability.admin")) return;
		String msg = e.getChatMessage().toLowerCase();
		if (!msg.contains("/cd") && !msg.contains("/customdurability")) {
			return;
		}
		String[] cmds = msg.split(" ");
		if (cmds.length < 1) {
			Collection<String> compl = e.getTabCompletions();
			compl.clear();
			Utility.sendMsg(e.getPlayer(), "&b&lTIP: &7Use &f\"/cd help\"&7.");
			return;
		}
		handler.manageTabComplete(e, cmds);
	}
	*/
	@EventHandler
	public void closeEvent(InventoryCloseEvent e) {
		if (!(e.getInventory().getHolder() instanceof MAGUIHolder)) return;
		handler.manageAnvilClose(e);
	}
	@EventHandler(priority = EventPriority.NORMAL)
	public void onMagicRepair(InventoryClickEvent e) {
		if (!(e.getInventory().getHolder() instanceof MAGUIHolder)) return;
		if (e.getRawSlot() > 26) {
			if (e.getAction().toString().contains("COLLECT")) {
				e.setCancelled(true);
				return;
			}
			return;
		}
		if (e.getSlot() != 10 && e.getSlot() != 13 && e.getSlot() != 16) {
			e.setCancelled(true);
			return;
		}
		int slot = e.getSlot();
		InventoryAction act = e.getAction();
		switch (slot) {
		case 10:
			handler.manageAnvilPlaceItem(e);
			return;
		case 13:
			handler.manageAnvilPlaceMaterial(e);
			return;
		case 16:
			ItemStack res = e.getCurrentItem();
			if (res.getType().toString().contains("ANVIL")) {
				e.setCancelled(true);
				handler.manageAnvilStartForge(e);
				return;
			}
			if (act.toString().contains("PICKUP"))
			handler.manageAnvilClaim(e);
			}
		return;
	}
	@EventHandler(priority = EventPriority.LOWEST)
	public void onSignCreate(SignChangeEvent e) {
		Block b = e.getBlock();
		if (!e.getPlayer().hasPermission("customdurability.createrepair")) return;
		Sign sign = (Sign) b.getState();
	
		Player p = e.getPlayer();
		if (!e.getLine(0).toLowerCase().contains("[repairs]")) {
			return;
		}
		e.setLine(0, "&6[repairs]");
		RepairTarget target = null;
		String tr = e.getLine(1).toLowerCase();
		if (!tr.contains("hand") && !tr.contains("all")) {
			Utility.sendMsg(p, "&f&lTIP: &7Use either &b&oALL &7or &b&oHAND &7for line 2.");
			e.setCancelled(true);
			return;
		}
		if (tr.contains("hand")) {
			tr = "hand";
		}
		if (tr.contains("all")) tr = "all";
		switch (tr.toLowerCase()) {
		case "hand":
			target = RepairTarget.HAND;
			break;
		case "all":
			target = RepairTarget.ALL;
			break;
		}
		int cost;
		RepairCost rcost;
		RepairCostType type = RepairCostType.None;
		String costs = e.getLine(2);
		costs = costs.replaceAll(" ", "");
		if (costs.length() > 0) {
		if (costs.contains(cfg.getCurrency())) {
			costs = costs.replace(cfg.getCurrency(), "");
			Utility.sendConsole("DEBUG: " + costs);
			type = RepairCostType.Money;
		}
		if (costs.contains("XP")) {
			costs = costs.replaceAll("XP", "");
			type = RepairCostType.XP;
		}
		if (costs.contains("LVL")) {
			costs = costs.replaceAll("LVL", "");
			type = RepairCostType.XPLevel;
		}
		try {
			Integer.parseInt(costs);
		} catch (NumberFormatException er) {
			Utility.sendMsg(p, "&c&lOops! &7You set the repair sign wrong!");
			Utility.sendMsg(p, "&7Use only numbers and currencies!");
			Utility.sendMsg(p, "&aAvailable Currencies: &f&l" + cfg.getCurrency() + "&7, " + "&f&lXP" + "&7 and " + "&f&lLVL&7.");
			e.setCancelled(true);
			return;
		}
		rcost = new RepairCost(type, Integer.parseInt(costs));
		}
		else {
			rcost = new RepairCost(type, 0);
		}
		RepairSign resign = new RepairSign(sign, rcost, target);
		handler.manageSignCreation(resign, e);
	}
	/*
	 * 
	 *     CASE EXAMPLE
	 * 	HAVING HOE IN MAIN AND OFF HAND
	 * WILL ONLY USE THE HOE IN MAIN HAND.
	 * 
	 * 	USING HOE IN THE OFFHAND
	 * 
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void ManageEntityHit(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getDamager();
		PlayerInventory inv = p.getInventory();
		ItemStack pit = inv.getItemInHand();
		if (pit == null) {
			return;
		}
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
	private void openingAnvil(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (p.getItemInHand() == null) return;
		if (!new NBTItem(p.getItemInHand()).hasKey("durabilitytype")) return;
		handler.manageAnvilOpening(e);
		
	}
	Material podz = XMaterial.PODZOL.parseMaterial();
	Material dirt = XMaterial.DIRT.parseMaterial();
	Material grassz = XMaterial.GRASS_BLOCK.parseMaterial();
	@EventHandler(priority = EventPriority.HIGHEST)
	public void rightclickEvent(PlayerInteractEvent e) {
		if (e.isCancelled()) return;
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		if (e.getClickedBlock().getType().toString().contains("SIGN")) {
			recheckRepairUse(e);
			return;
		}
		if (e.getClickedBlock().getType().toString().contains("ANVIL")) {
			openingAnvil(e);
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
		World w = e.getPlayer().getWorld();
		Location loc = e.getClickedBlock().getLocation();
		loc.add(0.0, 1.0, 0.0);
		Block b = w.getBlockAt(loc);
		if (b != null && b.getType() != Material.AIR) return;
		if (mat != grassz && mat != dirt) {
			return;
		}
			if (mat == podzol) return;
		else {
			if (e.getClickedBlock().getBlockPower() == 2) return;
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
	private void recheckRepairUse(PlayerInteractEvent e) {
		Sign s = (Sign) e.getClickedBlock().getState();
		if (!s.getLine(0).equals(Utility.TransColor("&6&l• &1&l[Repairs] &6&l•"))) {
			return;
		}
		NBTItem nbt;
		RepairSign sign;
		RepairCost cost;
		RepairCostType type = RepairCostType.None;
		RepairTarget target = RepairTarget.HAND;
		if (s.getLine(1).contains("ALL")) {
			target = RepairTarget.ALL;
		}
		if (s.getLine(1).contains("HAND")) {
			target = RepairTarget.HAND;
			if (e.getItem() == null || e.getItem().getType() == Material.AIR) {
				return;
			}
			nbt = new NBTItem(e.getItem());
			if (!nbt.hasKey("durabilitytype")) {
				Utility.sendMsg(e.getPlayer(), "&cWooopsy! &7That's not a custom-durability item!");
				return;
			}
		}
		if (s.getLine(2).contains("Cost")) {
			String l3 = s.getLine(3);
			if (l3.contains(cfg.getCurrency())) {
				type = RepairCostType.Money;
				l3 = l3.replace(cfg.getCurrency(), "");
			}
			if (l3.contains("XP") && !l3.contains("Levels")) {
				type = RepairCostType.XP;
				l3 = l3.replaceAll("XP", "");
			}
			if (l3.contains("XP") && l3.contains("Levels")) {
				type = RepairCostType.XPLevel;
				l3 = l3.replaceAll("XP Levels", "");
			}
			l3 = l3.replaceAll(" ", "");
			l3 = ChatColor.stripColor(l3);
			int dcost = Integer.parseInt(l3);
			cost = new RepairCost(type, dcost);
		}
		else {
			cost = new RepairCost(type, 0);
		}
		sign = new RepairSign(s, cost, target);
		handler.manageSignRepair(sign, e);
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onSheepShear(PlayerShearEntityEvent e) {
		if (!(e.getEntity() instanceof Sheep)) {
			return;
		}
		handler.manageShearEvent(e);
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onFishEvent(final PlayerFishEvent e) {
		final Player p = e.getPlayer();
		final PlayerInventory inv = p.getInventory();
		if (inv.getItemInMainHand() == null) {
			return;
		}
		ItemStack pit = inv.getItemInMainHand();
		if (pit.getType() == Material.AIR) return;
		NBTItem nbt = new NBTItem(pit);
		if (!nbt.hasKey("durabilitytype")) return;
		final State st = e.getState();
		final Entity caught = e.getCaught();
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
