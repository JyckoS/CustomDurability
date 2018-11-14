package com.gmail.JyckoSianjaya.customdurability.commands;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.JyckoSianjaya.customdurability.customdurability;
import com.gmail.JyckoSianjaya.customdurability.Utility.Utility;
import com.gmail.JyckoSianjaya.customdurability.Utility.XMaterial;
import com.gmail.JyckoSianjaya.customdurability.Utility.NBT.NBTItem;
import com.gmail.JyckoSianjaya.customdurability.durability.Durability;
import com.gmail.JyckoSianjaya.customdurability.storage.ConfigStorage;

public class CDCommand implements CommandExecutor {
	private ConfigStorage cfg = ConfigStorage.getInstance();
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("customdurability.help")) {
			return true;
		}
		reManage(sender, cmd, args);
		// TODO Auto-generated method stub
		return true;
	}
	/*
	 * NBT DATA
	 * 
	 * "durabilitytype" returns String of Durability Type Name
	 * 
	 * "customdur.currentDur" returns Current Custom Durability
	 * "customdur.maxDur" returns Maximum Durability
	 */
	/**
	 * @param snd
	 * @param cmd
	 * @param args
	 */
	private void reManage(CommandSender snd, Command cmd, String[] args) {
		Boolean isPlayer = false;
		if (snd instanceof Player) {
			isPlayer = true;
		}
		if (!snd.hasPermission("customdurability.admin")) {
			if (!isPlayer) {
				Utility.sendMsg(snd, "&cOnly players!");
				return;
			}
			Player p = (Player) snd;
			try {
				p.getItemInHand().getItemMeta();
			} catch (Exception e) {
				Utility.sendMsg(p, "&cThat, have nothing to do with durabilities..");
				return;
			}
			if (p.getItemInHand() == null) {
				Utility.sendMsg(p, "&cAir is too thin..");
				return;
			}
			ItemStack itm = p.getItemInHand();
			NBTItem nbt = new NBTItem(itm);
			if (!nbt.hasKey("durabilitytype")) {
				Utility.sendMsg(p, "&cThat is not awesome enough..");
				return;
			}
			int mindurs = nbt.getInteger("customdur.currentDur");
			int maxdurs = nbt.getInteger("customdur.maxDur");
			Utility.sendMsg(p, "&f&l&nItem Details");
			Utility.sendMsg(p, "&7Current Durability: &e" + mindurs);
			Utility.sendMsg(p, "&7Maximum Durability: &e" + maxdurs);
			return;
		}
		if (args.length == 0) {
			Utility.sendMsg(snd, "&e&lCustom&c&lDurability &7by &a&oGober");
			Utility.sendMsg(snd, "&7Type &f\"/cd help\" &7for help!");
			return;
		}
		switch (args[0].toLowerCase()) {
		case "help":
		default:
			Utility.sendMsg(snd, "&e&n   &n&6&lCustom&c&lDurability&e&n  ");
			Utility.sendMsg(snd, "&8&l > &7/customdurability &fadd &e<type> &a<MinDur> &2<MaxDur>");
			Utility.sendMsg(snd, "&8&l > &7/customdurability &fremoveDurability");
			Utility.sendMsg(snd, "&8&l > &7/customdurability &creload");
			Utility.sendMsg(snd, "&8&l > &7/customdurability &flist");
			Utility.sendMsg(snd, "&8&l > &7/customdurability &fcheck");
			Utility.sendMsg(snd, "&e&m                             ");
			return;
		case "check":
			if (!isPlayer) {
				Utility.sendMsg(snd, "&cOnly players!");
				return;
			}
			Player pp = (Player) snd;
			try {
				pp.getItemInHand().getItemMeta();
			} catch (Exception e) {
				Utility.sendMsg(pp, "&cThat, have nothing to do with durabilities..");
				return;
			}
			if (pp.getItemInHand() == null) {
				Utility.sendMsg(pp, "&cAir is too thin..");
				return;
			}
			ItemStack itmr = pp.getItemInHand();
			if (itmr.getDurability() < 16) {
				Utility.sendMsg(pp, "&cThat doesn't look like an appropriate tool/armor. Please use item with durability more than 15.");
				return;
			}
			NBTItem nbt = new NBTItem(itmr);
			if (!nbt.hasKey("durabilitytype")) {
				Utility.sendMsg(pp, "&cThat is not awesome enough..");
				return;
			}
			int mindurs = nbt.getInteger("customdur.currentDur");
			int maxdurs = nbt.getInteger("customdur.maxDur");
			Utility.sendMsg(pp, "&f&l&nItem Details");
			Utility.sendMsg(pp, "&7Current Durability: &e" + mindurs);
			Utility.sendMsg(pp, "&7Maximum Durability: &e" + maxdurs);
			return;
		case "addcustom":
		case "add":
			if (!isPlayer) {
				Utility.sendMsg(snd, "&cOnly Players!");
				return;
			}
			if (args.length < 4) {
				Utility.sendMsg(snd, "&c&lOops! &7Use &f/cd add <type> <minDur> <maxDur>");
				return;
			}
			if (!cfg.containsKey(args[1].toLowerCase())) {
				Utility.sendMsg(snd, "&c&lOops! &7That Durability Type doesn't exist. Use &f/cd list &7to get durability types.");
				return;
			}
			int mindur;
			int maxdur;
			String targetdurability = args[1].toLowerCase();
			try {
				mindur = Integer.parseInt(args[2]);
				maxdur = Integer.parseInt(args[3]);
			} catch (NumberFormatException e) {
				Utility.sendMsg(snd, "&c&lOops! &7Remember that &f<MinDur> &7and &f<maxDur> &7need to be an integer.");
				return;
			}
			
			Player p = (Player) snd;
			try {
				p.getItemInHand().getItemMeta();
			} catch (Exception e) {
				Utility.sendMsg(p, "&cThat, have nothing to do with durabilities..");
				return;
			}
			if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR) {
				Utility.sendMsg(p, "&cAir is too thin..");
				return;
			}
			ItemStack itm = p.getItemInHand();
			switch (XMaterial.fromString(itm.getType().toString())) {
			case IRON_SHOVEL:
			case GOLDEN_SHOVEL:
			case STONE_SHOVEL:
			case WOODEN_SHOVEL:
			case DIAMOND_SHOVEL:
			case IRON_AXE:
			case GOLDEN_AXE:
			case WOODEN_AXE:
			case STONE_AXE:
			case DIAMOND_AXE:
			case IRON_PICKAXE:
			case GOLDEN_PICKAXE:
			case STONE_PICKAXE:
			case WOODEN_PICKAXE:
			case DIAMOND_PICKAXE:
			case IRON_HOE:
			case GOLDEN_HOE:
			case STONE_HOE:
			case WOODEN_HOE:
			case DIAMOND_HOE:
			case IRON_SWORD:
			case GOLDEN_SWORD:
			case STONE_SWORD:
			case WOODEN_SWORD:
			case DIAMOND_SWORD:
			case FISHING_ROD:
			case FLINT_AND_STEEL:
			case BOW:
			case TRIDENT:
			case ELYTRA:
			case SHIELD:
			case SHEARS:
				break;
				default:
					Utility.sendMsg(p, "&cYou can't get that item to have a CustomDurability");
					return;
			}
			NBTItem item = new NBTItem(itm);
			Durability type;
			if (item.hasKey("durabilitytype")) {
				Utility.sendMsg(p, "&c&lAw.. &7That item has a custom durability already. Remove it with &f/cd remove&7!");
				return;
			}
			type = cfg.getDurability(targetdurability);
			item.setString("durabilitytype", targetdurability);
			item.setInteger("customdur.currentDur", mindur);
			item.setInteger("customdur.maxDur", maxdur);
			itm = item.getItem();
			ArrayList<String> lores;
			try {
			lores = new ArrayList<String>(itm.getItemMeta().getLore());
			} catch (Exception e) {
				lores = new ArrayList<String>();
				ItemMeta mt = itm.getItemMeta();
				mt.setLore(lores);
				itm.setItemMeta(mt);
			}
			lores.add(type.getFormatted(mindur, maxdur));
			ItemMeta imeta = itm.getItemMeta();
			imeta.setLore(Utility.TransColor(lores));
			imeta.setUnbreakable(true);
			imeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			itm.setItemMeta(imeta);
			p.setItemInHand(itm);
			Utility.sendMsg(p, "&7Successfully applied durability type &a" + targetdurability + "&7 to your current item.");
			return;
		case "remove":
		case "removeDurability":
			Player pr = (Player) snd;
			try {
				pr.getItemInHand().getItemMeta().getLore();
			} catch (Exception e) {
				Utility.sendMsg(pr, "&cThat, have nothing to do with durabilities..");
				return;
			}
			if (pr.getItemInHand() == null) {
				Utility.sendMsg(pr, "&cAir is too thin..");
				return;
			}
			ItemStack items = pr.getItemInHand();
			switch (XMaterial.fromString(items.getType().toString())) {
			case IRON_SHOVEL:
			case GOLDEN_SHOVEL:
			case STONE_SHOVEL:
			case WOODEN_SHOVEL:
			case DIAMOND_SHOVEL:
			case IRON_AXE:
			case GOLDEN_AXE:
			case WOODEN_AXE:
			case STONE_AXE:
			case DIAMOND_AXE:
			case IRON_PICKAXE:
			case GOLDEN_PICKAXE:
			case STONE_PICKAXE:
			case WOODEN_PICKAXE:
			case DIAMOND_PICKAXE:
			case IRON_HOE:
			case GOLDEN_HOE:
			case STONE_HOE:
			case WOODEN_HOE:
			case DIAMOND_HOE:
			case IRON_SWORD:
			case GOLDEN_SWORD:
			case STONE_SWORD:
			case WOODEN_SWORD:
			case DIAMOND_SWORD:
			case FISHING_ROD:
			case FLINT_AND_STEEL:
			case BOW:
			case TRIDENT:
			case ELYTRA:
			case SHIELD:
			case SHEARS:
				break;
				default:
					Utility.sendMsg(pr, "&cYou can't get that item to have a CustomDurability");
					return;
			}
			NBTItem itemss = new NBTItem(items);
			if (!itemss.hasKey("durabilitytype")) {
				Utility.sendMsg(pr, "&7That item is not a custom durability item!");
				return;
			}
			Durability dtype = cfg.getDurability(itemss.getString("durabilitytype"));
			ArrayList<String> lorez = new ArrayList<String>(items.getItemMeta().getLore());
			int target = 0;
			for (String str : lorez) {
				if (!str.contains(dtype.getEmptyFormat())) {
					target++;
					continue;
				}
				break;
			}
			lorez.remove(target - 1);
			itemss.removeKey("durabilitytype");
			itemss.removeKey("customdur.currentDur");
			itemss.removeKey("customdur.maxDur");
			items = itemss.getItem();
			ItemMeta meta = items.getItemMeta();
			meta.setLore(lorez);
			if (meta.isUnbreakable()) {
			meta.setUnbreakable(false);
			meta.removeItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			}
			items.setItemMeta(meta);
			pr.setItemInHand(items);
			Utility.sendMsg(pr, "&7Successfully removed the Custom Durability from your current item.");
			return;
		case "reload":
			customdurability.getInstance().reloadConfig();
			cfg.reloadConfig();
			Utility.sendMsg(snd, "&cConfig reloaded.");
			return;
		case "list":
			String tosend = "&e&lDurabilities: &a";
			for (String str : cfg.getTypes()) {
				tosend = tosend + str + "&8, &a"; 
			}
			Utility.sendMsg(snd, tosend);
			return;
		}
		
	}

}
