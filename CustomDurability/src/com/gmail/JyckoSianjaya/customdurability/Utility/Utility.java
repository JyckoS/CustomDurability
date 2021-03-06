package com.gmail.JyckoSianjaya.customdurability.Utility;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public final class Utility {
	int time;
	int ctime;
	private Utility() {}
	public static Class<?> getClass(String classname) {
		String servversion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		try {
			return Class.forName("net.minecraft.server." + servversion + "." + classname);
		} catch (ClassNotFoundException e) {
			e.printStackTrace(); 
		} 
		return null;
	  }
	private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
	    List<Class> classes = new ArrayList<Class>();
	    if (!directory.exists()) {
	        return classes;
	    }
	    File[] files = directory.listFiles();
	    for (File file : files) {
	        if (file.isDirectory()) {
	            assert !file.getName().contains(".");
	            classes.addAll(findClasses(file, packageName + "." + file.getName()));
	        } else if (file.getName().endsWith(".class")) {
	            classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
	        }
	    }
	    return classes;
	}
	public static Class[] getClasses(String packageName)
	        throws ClassNotFoundException, IOException {
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    assert classLoader != null;
	    String path = packageName.replace('.', '/');
	    Enumeration<URL> resources = classLoader.getResources(path);
	    List<File> dirs = new ArrayList<File>();
	    while (resources.hasMoreElements()) {
	        URL resource = resources.nextElement();
	        dirs.add(new File(resource.getFile()));
	    }
	    ArrayList<Class> classes = new ArrayList<Class>();
	    for (File directory : dirs) {
	        classes.addAll(findClasses(directory, packageName));
	    }
	    return classes.toArray(new Class[classes.size()]);
	}
	public static void sendPacket(Player player, Object packet) {
		try {
			Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			playerConnection.getClass().getMethod("sendPacket", new Class[] { getClass("Packet") }).invoke(playerConnection, new Object[] { packet });
		} catch (Exception e) {
			e.printStackTrace();
	   }
	}

	public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {


	    try
	    {
	      if (title != null) {
	        title = TransColor(title);
	        Object e = getClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
	        Object chatTitle = getClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
	        Constructor titleConstructor = getClass("PacketPlayOutTitle").getConstructor(new Class[] { getClass("PacketPlayOutTitle").getDeclaredClasses()[0], getClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE });
	        Object titlePacket = titleConstructor.newInstance(new Object[] { e, chatTitle, fadeIn, stay, fadeOut });
	        sendPacket(player, titlePacket);

	        e = getClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
	        chatTitle = getClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
	        titleConstructor = getClass("PacketPlayOutTitle").getConstructor(new Class[] { getClass("PacketPlayOutTitle").getDeclaredClasses()[0], getClass("IChatBaseComponent") });
	        titlePacket = titleConstructor.newInstance(new Object[] { e, chatTitle });
	        sendPacket(player, titlePacket);
	      }

	      if (subtitle != null) {
	        subtitle = TransColor(subtitle);
	        Object e = getClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
	        Object chatSubtitle = getClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
	        Constructor subtitleConstructor = getClass("PacketPlayOutTitle").getConstructor(new Class[] { getClass("PacketPlayOutTitle").getDeclaredClasses()[0], getClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE });
	        Object subtitlePacket = subtitleConstructor.newInstance(new Object[] { e, chatSubtitle, fadeIn, stay, fadeOut });
	        sendPacket(player, subtitlePacket);

	        e = getClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
	        chatSubtitle = getClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + subtitle + "\"}" });
	        subtitleConstructor = getClass("PacketPlayOutTitle").getConstructor(new Class[] { getClass("PacketPlayOutTitle").getDeclaredClasses()[0], getClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE });
	        subtitlePacket = subtitleConstructor.newInstance(new Object[] { e, chatSubtitle, fadeIn, stay, fadeOut });
	        sendPacket(player, subtitlePacket);
	      }
	    } catch (Exception var11) {
	      var11.printStackTrace();
	    }
	}
	public static void executeConsole(String cmd) {
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
	}
	public static void sendMsg(Player b, String msg) {
		b.sendMessage(TransColor(msg));
	}
	public static void sendMsg(CommandSender b, String msg) {
		b.sendMessage(TransColor(msg));
	}
	public static void broadcast(String msg) {
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}
	public static void sendActionBar(Player b, String ActionBar) {
		ActionBarAPI.sendActionBar(b, ChatColor.translateAlternateColorCodes('&', ActionBar));
	}
	public static void sendConsole(String msg) {
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg));;
	}
	public static String TransColor(String c) {
		return ChatColor.translateAlternateColorCodes('&', c);
	}
	public static String[] TransColor(String[] c) {
		String strf = "";
		int length = c.length;
		int cr = 0;
		for (String str : c) {
			cr++;
			if (cr != length) {
				strf = strf + str + ";";
			}
			else {
				strf = strf + str;
			}
		}
		strf = TransColor(strf);
		return strf.split(";");
	}
	public static List<String> TransColor(List<String> strlist) {
		for (int x = 0; x < strlist.size(); x++) {
			strlist.set(x, TransColor(strlist.get(x)));
		}
		return strlist;
	}
	public static void PlaySoundAt(World w, Location p, Sound s, Float vol, Float pit) {
		w.playSound(p, s, vol, pit);;
	}
	public static void PlaySound(Player p, Sound s, Float vol, Float pit) {
		p.playSound(p.getLocation(), s, vol, pit);
	}
	public static ArrayList<Player> near(Entity loc, int radius) {
		ArrayList<Player> nearby = new ArrayList<>();
		for (Entity e : loc.getNearbyEntities(radius, radius, radius)) {
			if (e instanceof Player) {
				nearby.add((Player) e);
			}
		}
		return nearby;
	}
	public static void PlayParticle(World world, Location loc, Effect particle, int count) {
		world.playEffect(loc, particle, count);
	}
	public static void spawnParticle(World world, Particle particle, Location loc, Double Xoff, Double Yoff, Double Zoff, int count) {
		world.spawnParticle(particle, loc.getX(), loc.getY(), loc.getZ(), count, Xoff, Yoff, Zoff);
		
	}
	public static String normalizeTime(int seconds) {
		int sec = seconds;
		int min = 0;
		int hour = 0;
		int day = 0;
		while (sec >= 60) {
			min+=1;
			sec-=60;
		}
		while (min >= 60) {
			hour+=1;
			min-=60;
		}
		while (hour >= 24) {
			day+=1;
			hour-=24;
		}
		if (sec == 0 && min == 0 && hour == 0 && day == 0) {
			return "&a&lZERO!";
		}
		if (min == 0 && hour == 0 && day == 0) {
			return sec + " Seconds";
		}
		if (hour == 0 && day == 0 && min > 0) {
			return min + " Minutes " + sec + " Seconds"; 
		}
		if (day == 0 && hour > 0) {
			return hour + " Hours " + min + " Minutes " + sec + " Seconds";
		}
		if (day > 0) {
			return day + " Days " + hour + " Hours " + min + " Minutes " + sec + " Seconds";
		}
		return "&a&lZERO!";
	}
	public static String normalizeTime2(int seconds) {
		int sec = seconds;
		int min = 0;
		int hour = 0;
		int day = 0;
		while (sec >= 60) {
			min+=1;
			sec-=60;
		}
		while (min >= 60) {
			hour+=1;
			min-=60;
		}
		while (hour >= 24) {
			day+=1;
			hour-=24;
		}
		if (sec == 0 && min == 0 && hour == 0 && day == 0) {
			return "&a&lZERO!";
		}
		if (min == 0 && hour == 0 && day == 0) {
			return sec + " sec";
		}
		if (hour == 0 && day == 0 && min > 0) {
			return min + " min " + sec + " sec"; 
		}
		if (day == 0 && hour > 0) {
			return hour + " h " + min + " min " + sec + " sec";
		}
		if (day > 0) {
			return day + " day " + hour + " h " + min + " min " + sec + " sec";
		}
		return "&a&lZERO!";
	}
	private static String lakad() {
		Utility.sendConsole("Umm, I just wanna say, thank you for decompiiling <3");
		return "thank you for buying my plugin, (from jycko) <3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3 <3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v<3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3<3 <3 <3v";
	}
}
