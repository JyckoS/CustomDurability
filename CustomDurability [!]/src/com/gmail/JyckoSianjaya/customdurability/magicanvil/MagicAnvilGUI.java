package com.gmail.JyckoSianjaya.customdurability.magicanvil;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.JyckoSianjaya.customdurability.Utility.Utility;
import com.gmail.JyckoSianjaya.customdurability.Utility.XMaterial;

public class MagicAnvilGUI {
	private static Inventory magicanvilgui;
	private static MagicAnvilGUI instance;
	private static ItemStack finish;
	private static ItemStack goback;
	private static ItemStack anvil;
	private MagicAnvilGUI() {
		loadGUI();
	}
	public static MagicAnvilGUI getInstance() {
		if (instance == null) instance = new MagicAnvilGUI();
		return instance;
	}
	private static Inventory getClone() {
		Inventory inv = Bukkit.createInventory(new MAGUIHolder(), 27, Utility.TransColor("             Magic Anvil"));
		for (int i = 0; i < 27; i++) {
			ItemStack item = magicanvilgui.getItem(i);
			if (item == null) continue;
			inv.setItem(i, item);
		}
		return inv;
	}
	private void loadGUI() {
		magicanvilgui = Bukkit.createInventory(new MAGUIHolder(), 27, Utility.TransColor("          Magic Anvil"));
		final ItemStack windows = new ItemStack(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem());
		final ItemMeta meta = windows.getItemMeta();
		meta.setDisplayName(" ");
		windows.setItemMeta(meta);
		for (int i = 0; i < 27; i++) {
			magicanvilgui.setItem(i, windows.clone());
		}
		magicanvilgui.setItem(10, null);
		magicanvilgui.setItem(13, null);
		magicanvilgui.setItem(16, null);
		final ItemStack bluewindows = new ItemStack(XMaterial.BLUE_STAINED_GLASS_PANE.parseItem());
		final ItemMeta bwmet = bluewindows.getItemMeta();
		bwmet.setDisplayName(" ");
		ArrayList<String> bwlor = new ArrayList<String>();
		bwlor.add(" ");
		bwlor.add("&7Put your &e&lRepairables &7here.");
		bwlor.add(" ");
		bwmet.setLore(Utility.TransColor(bwlor));
		bluewindows.setItemMeta(bwmet);
		magicanvilgui.setItem(9, bluewindows);
		magicanvilgui.setItem(11, bluewindows);
		final ItemStack orangewind = new ItemStack(XMaterial.ORANGE_STAINED_GLASS_PANE.parseItem());
		final ItemMeta ormet = orangewind.getItemMeta();
		ormet.setDisplayName(" ");
		ArrayList<String> orlor = new ArrayList<String>();
		orlor.add(" ");
		orlor.add("&7Put your &a&lRepair Materials &7here.");
		orlor.add(" ");
		ormet.setLore(Utility.TransColor(orlor));
		orangewind.setItemMeta(ormet);
		magicanvilgui.setItem(12, orangewind);
		magicanvilgui.setItem(14, orangewind);
		final ItemStack resultwind = new ItemStack(XMaterial.GLASS.parseItem());
		final ItemMeta resultmt = resultwind.getItemMeta();
		resultmt.setDisplayName(" ");
		ArrayList<String> rlor = new ArrayList<String>();
		rlor.add(" ");
		rlor.add("&7Your repair &d&lResult &7will be shown below here.");
		rlor.add(" ");
		resultmt.setLore(Utility.TransColor(rlor));
		resultwind.setItemMeta(resultmt);
		goback = resultwind.clone();
		finish = new ItemStack(XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE.parseItem());
		ItemMeta fmeta = finish.getItemMeta();
		fmeta.setDisplayName(Utility.TransColor("&e&lRepair Finished!"));
		ArrayList<String> lor = new ArrayList<String>();
		lor.add(" ");
		lor.add("&e&lHere is your result!");
		lor.add(" ");
		fmeta.setLore(Utility.TransColor(lor));
		fmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		finish.setItemMeta(fmeta);
		finish.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
		ItemStack emptyanvil = new ItemStack(XMaterial.ANVIL.parseMaterial());
		ItemMeta anvmeta = emptyanvil.getItemMeta();
		anvmeta.setDisplayName(Utility.TransColor("&7< &8&lForge &7>"));
		ArrayList<String> lmaos = new ArrayList<String>();
		lmaos.add(" ");
		lmaos.add("&e&oClick on this to &6&lrepair&e&o your item.");
		lmaos.add(" ");
		anvmeta.setLore(Utility.TransColor(lmaos));
		emptyanvil.setItemMeta(anvmeta);
		anvil = emptyanvil.clone();
		setBack(magicanvilgui);
	}
	public static void setDone(Inventory inv) {
		inv.setItem(7, finish);
		inv.setItem(15, finish);
		inv.setItem(17, finish);
		inv.setItem(25, finish);
	}
	public static void setBack(Inventory inv) {
		inv.setItem(7, goback);
		inv.setItem(15, goback);
		inv.setItem(17, goback);
		inv.setItem(25, goback);
		inv.setItem(16, anvil);
	}
	public static Inventory getGUI() {
		return getClone();
	}
}
