package com.gmail.JyckoSianjaya.customdurability;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.JyckoSianjaya.customdurability.Events.CDEHandler;
import com.gmail.JyckoSianjaya.customdurability.Events.CDListener;
import com.gmail.JyckoSianjaya.customdurability.Experimental.AwesomeClass;
import com.gmail.JyckoSianjaya.customdurability.commands.CDCommand;
import com.gmail.JyckoSianjaya.customdurability.commands.CDTab;
import com.gmail.JyckoSianjaya.customdurability.magicanvil.MagicAnvilGUI;
import com.gmail.JyckoSianjaya.customdurability.storage.ConfigStorage;
import com.gmail.JyckoSianjaya.customdurability.storage.RepairStorage;

import net.milkbowl.vault.economy.Economy;

import com.gmail.JyckoSianjaya.customdurability.Utility.*;

public class customdurability extends JavaPlugin {
	private Metrics m;
	private CDEHandler hand;
	private ConfigStorage cfg;
	private ActionBarAPI aba;
	private RepairStorage repair;
	private MagicAnvilGUI gui;
	private static Boolean isVault = false;
	private static Economy econ;
	private static customdurability instance;
	@Override
	public void onEnable() {
		experiment();
		instance = this;
		m = new Metrics(this);
		loadConfig();
		registerCommand();
		registerEvent();
		registerInstance();
		logConsole();
		setupEconomy();
	}
	private void experiment() {
		try {
			Constructor<AwesomeClass> awesomeconstructor = AwesomeClass.class.getConstructor(new Class[] {Integer.class});
			// Getting the AwesomeClass constructor with parameter of Integer.class
			/*
			 * 
			 * NOTE: int.class (primitive data type) && Integer.class are different objects.
			 * 
			 */
			AwesomeClass instance = null;
			try {
				instance = (AwesomeClass) awesomeconstructor.newInstance(1002);
				// Creates new instance with parameter 1002.
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Field cost = AwesomeClass.class.getDeclaredField("cost"); // Gets the private Field Cost.
			cost.setAccessible(true); // Make the private variable accessible.
			Field privatecoste = AwesomeClass.class.getDeclaredField("coste"); // Gets the Field coste (can private).
			privatecoste.setAccessible(true); // make it accessible.
			Field publiccoste = AwesomeClass.class.getField("coste"); // Gets the Field coste (Only public)
			publiccoste.setAccessible(true); // Make it accessible.
			
			int realcost = cost.getInt(instance);  // Gets the Object from the Instance.
			int realcoste = privatecoste.getInt(instance); // Same thing
			int realcoste2 = publiccoste.getInt(instance); // Same thing
			Utility.sendConsole("DEBUG XX: " + realcost + ", " + realcoste + ", " + realcoste2);
			Method m = instance.getClass().getMethod("setCost", new Class[] {int.class});
			// Get the method 'setCost' with parameter 'int'
			m.setAccessible(true); // Make it accessible, good practice.
			Method getcost = instance.getClass().getMethod("getCost");
			// get the method getCost, has no parameter.
			getcost.setAccessible(true);
			m.invoke(instance, 10023);
			// invoke the method on the instance.
			int amount = (int) getcost.invoke(instance);
			// get the object back.
			Utility.sendConsole("S DEBUG: " + amount);
					
		}
		catch (NoSuchFieldException e) {
			e.printStackTrace();
			Utility.sendConsole("LMAO!");
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Boolean isVaultEnabled() {
		return isVault;
	}
	public static Economy getEconomy() { return econ; }
    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        isVault = true;
        econ = rsp.getProvider();
        return econ != null;
    }
	public static customdurability getInstance() {
		return instance;
	}
	private void logConsole() {
		String v = this.getDescription().getVersion();
		Utility.sendConsole("&e&lCustomDurability &7running V&f" + v);
		Utility.sendConsole("&7Quick Message: &aThere are truly countless of young childrens struggling out there, help spread this awareness or help them directly");
		
	}
	private void loadConfig() {
		this.getConfig().options().copyDefaults(true);
		saveConfig();
	}
	private void registerCommand() {
		TabExecutor cmd = new CDCommand();
		this.getCommand("customdurability").setExecutor(cmd);
		this.getCommand("customdurability").setTabCompleter(cmd);
	}
	private void registerEvent() {
		Bukkit.getServer().getPluginManager().registerEvents(new CDListener(), this);
	}
	private void registerInstance() {
		hand = CDEHandler.getInstance();
		cfg = ConfigStorage.getInstance();
		aba = ActionBarAPI.getInstance();
		repair = RepairStorage.getInstance();
		gui = MagicAnvilGUI.getInstance();
	}
	@Override
	public void onDisable() {
	}
}
