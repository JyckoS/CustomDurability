package com.gmail.JyckoSianjaya.customdurability;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.JyckoSianjaya.customdurability.Events.CDEHandler;
import com.gmail.JyckoSianjaya.customdurability.Events.CDListener;
import com.gmail.JyckoSianjaya.customdurability.commands.CDCommand;
import com.gmail.JyckoSianjaya.customdurability.storage.ConfigStorage;
import com.gmail.JyckoSianjaya.customdurability.Utility.*;

public class customdurability extends JavaPlugin {
	private Metrics m;
	private CDEHandler hand;
	private ConfigStorage cfg;
	private static customdurability instance;
	@Override
	public void onEnable() {
		instance = this;
		m = new Metrics(this);
		loadConfig();
		registerCommand();
		registerEvent();
		registerInstance();
		logConsole();
	}
	public static customdurability getInstance() {
		return instance;
	}
	private void logConsole() {
		String v = this.getDescription().getVersion();
		Utility.sendConsole("&e&lCustomDurability &7running V&f" + v);
		Utility.sendConsole("&7Quick Message: &eThere's are truly countless of young childrens struggling out there, help spread this awareness or help them directly");
		
	}
	private void loadConfig() {
		this.getConfig().options().copyDefaults(true);
		saveConfig();
	}
	private void registerCommand() {
		this.getCommand("customdurability").setExecutor(new CDCommand());
	}
	private void registerEvent() {
		Bukkit.getServer().getPluginManager().registerEvents(new CDListener(), this);
	}
	private void registerInstance() {
		hand = CDEHandler.getInstance();
		cfg = ConfigStorage.getInstance();
	}
	@Override
	public void onDisable() {
	}
}
