package com.gmail.JyckoSianjaya.customdurability.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.JyckoSianjaya.customdurability.customdurability;
import com.gmail.JyckoSianjaya.customdurability.Utility.Utility;
import com.gmail.JyckoSianjaya.customdurability.Utility.XMaterial;
import com.gmail.JyckoSianjaya.customdurability.Utility.NBT.NBTItem;
import com.gmail.JyckoSianjaya.customdurability.durability.Durability;
import com.gmail.JyckoSianjaya.customdurability.magicanvil.RepairAmount;
import com.gmail.JyckoSianjaya.customdurability.magicanvil.RepairType;
import com.gmail.JyckoSianjaya.customdurability.magicanvil.Repairables;
import com.gmail.JyckoSianjaya.customdurability.storage.ConfigStorage;
import com.gmail.JyckoSianjaya.customdurability.storage.RepairStorage;

public class CDCommand implements CommandExecutor, TabExecutor {
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
	 * 
	 * "repairtype" returns Repair Type
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
			Utility.sendMsg(snd, "&e&n   &n&6&l&nCustom&c&l&nDurability&e&n  ");
			Utility.sendMsg(snd, "&8&l > &7&l/&7customdurability &fadd &e<type> &a<MinDur> &2<MaxDur>");
			Utility.sendMsg(snd, "&8&l > &7&l/&7customdurability &fremove");
			Utility.sendMsg(snd, "&8&l > &7&l/&7customdurability &creload");
			Utility.sendMsg(snd, "&8&l > &7&l/&7customdurability repair &7&o<Amount>");
			Utility.sendMsg(snd, "&8&l > &7&l/&7customdurability &brepairables");
			Utility.sendMsg(snd, "&8&l > &7&l/&7customdurability &crepairamount");
			Utility.sendMsg(snd, "&8&l > &7&l/&7customdurability &flist");
			Utility.sendMsg(snd, "&8&l > &7&l/&7customdurability &fcheck");
			Utility.sendMsg(snd, "&e&m                             ");
			return;
		case "repairables":{
			if (!isPlayer) {
				Utility.sendMsg(snd, "&cPlayers only!");
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
			ItemStack item = p.getItemInHand();
			NBTItem nbt = new NBTItem(item);
			if (!nbt.hasKey("durabilitytype")) {
				Utility.sendMsg(p, "&cThat is not awesome enough..");
				return;
			}
			RepairStorage rst = RepairStorage.getInstance();
			String repairables = item.getType() + "&7's Repairables: &a";
			Repairables rp = rst.getRepairables(item.getType());
			ArrayList<Material> mat = rp.getMaterials();
			for (Material m : mat) {
				String mats = m.toString();
				mats = mats.replaceAll("LEGACY_", "");
				mats = mats.replaceAll("LEGACY", "");
			repairables = repairables + mats + ", ";
			}
			Utility.sendMsg(p, repairables);
			return;
		}
		case "repairamount": {
			if (!isPlayer) {
				Utility.sendMsg(snd, "&cPlayers only!");
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
			ItemStack item = p.getItemInHand();
			NBTItem nbt = new NBTItem(item);
			Material mat = item.getType();
			RepairAmount amount = RepairStorage.getInstance().getRepairAmount(mat);
			if (amount == null) {
				Utility.sendMsg(p, "&7That has &cno repair amount&7, could be said that &e&l" + mat.toString() + " &7can't be used to repair.");
				return;
			}
			RepairType type = amount.getRepairType();
			double am = amount.getRepairAmount();
			String typess = item.getType().toString().replaceAll("LEGACY_", "").replaceAll("LEGACY", "");
			Utility.sendMsg(p, "&n" + typess + "&7's repair amount");
			switch (type) {
			case PERCENTAGE: {
				Utility.sendMsg(p, "&7Repair Type: &e&lPERCENTAGE");
				String percentage = "" + am * 100 + "%";
				String tosend = "&7Repair Amount: &b&l" + percentage;
				Utility.sendMsg(p, tosend);
				return;
			}
			case RANDOM_PERCENTAGE: {
				Utility.sendMsg(p, "&7Repair Type: &5&lRANDOM_PERCENTAGE");
				String percentage = "&e" + cfg.getMagicAnvil_minRandPercentage() * 100 + "%" + " &7until &c" + cfg.getMagicAnvil_maxRandPercentage() * 100 + "%";
				String tosend = "&7Repair Amount: &b&l" + percentage;
				Utility.sendMsg(p, tosend);
				return;
			}
			case INTEGER: {
				Utility.sendMsg(p, "&7Repair Type: &b&lINTEGER");
				String percentage = "&a&l" + am;
				String tosend = "&7Repair Amount: &b&l" + percentage;
				Utility.sendMsg(p, tosend);
				return;
			}
			case RANDOM_INTEGER: {
				Utility.sendMsg(p, "&7Repair Type: &d&lRANDOM_INTEGER");
				String percentage = "&e" + cfg.getMagicAnvil_minIntegerAmount() + " &7until &c" + cfg.getMagicAnvil_maxIntegerAmount();
				String tosend = "&7Repair Amount: &b&l" + percentage;
				Utility.sendMsg(p, tosend);
				return;
			}
			}
			
		}
		case "repair": {
			if (!isPlayer) {
				Utility.sendMsg(snd, "&cPlayers only!");
				return;
			}
			Player play = (Player) snd;
			Integer repairamount = null;
			if (args.length > 1) {
				try {
				repairamount = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					Utility.sendMsg(play, "&c&lOops! &7That's not a Number!");
					return;
				}
			}
			try {
				play.getItemInHand().getItemMeta();
			} catch (Exception e) {
				Utility.sendMsg(play, "&cThat, have nothing to do with durabilities..");
				return;
			}
			if (play.getItemInHand() == null) {
				Utility.sendMsg(play, "&cAir is too thin..");
				return;
			}
			ItemStack itmr = play.getItemInHand();
			NBTItem nbt = new NBTItem(itmr);
			if (!nbt.hasKey("durabilitytype")) {
				Utility.sendMsg(play, "&cThat is not awesome enough..");
				return;
			}
			int mindur = nbt.getInteger("customdur.currentDur");
			int maxdur = nbt.getInteger("customdur.maxDur");
			if (repairamount == null) repairamount = maxdur - mindur;
			while (mindur - repairamount > maxdur) repairamount--;
			int finale = mindur + repairamount;
			nbt.setInteger("customdur.currentDur", finale);
			ItemStack items = nbt.getItem();
			ArrayList<String> lorez = new ArrayList<String>(items.getItemMeta().getLore());
			int target = 0;
			Durability dtype = cfg.getDurability(nbt.getString("durabilitytype"));
			for (String str : lorez) {
				if (!str.contains(dtype.getEmptyFormat())) {
					target++;
					continue;
				}
				break;
			}
			lorez.set(target - 1, dtype.getFormatted(finale, maxdur));
			ItemMeta meta = items.getItemMeta();
			meta.setLore(lorez);
			items.setItemMeta(meta);
			play.setItemInHand(items);
			Utility.sendMsg(play, "&7Successfully repaired item durability!");
			return;
		}
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
			if (mindur > maxdur) mindur = maxdur;
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
			meta.removeItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			meta.removeItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			}
			items.setItemMeta(meta);
			pr.setItemInHand(items);
			Utility.sendMsg(pr, "&7Successfully removed the Custom Durability from your current item.");
			return;
		case "reload":
			customdurability.getInstance().reloadConfig();
			cfg.reloadConfig();
			RepairStorage.getInstance().reloadRepairs();
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
	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return null;
	}

}
