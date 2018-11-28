package com.gmail.JyckoSianjaya.customdurability.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public class CDTab {
	public static List<String> onTabComplete(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (args.length < 1) {
			ArrayList<String> ala = new ArrayList<String>();
			ala.add("&b&lPROTIP: &7Use &f\"/cd help\"&7.");
			return ala;
		}
		if (args.length < 2) {
			String cmde = args[0].toLowerCase();
			switch (cmde) {
			case "add":
			case "addrepair":
				ArrayList<String> types = new ArrayList<String>();
				types.add("INTEGER");
				types.add("RANDOM_INTEGER");
				types.add("PERCENTAGE");
				types.add("RANDOM_PERCENTAGE");
				return types;
			default:
				return null;
			}
		}
		return null;
	}
}
