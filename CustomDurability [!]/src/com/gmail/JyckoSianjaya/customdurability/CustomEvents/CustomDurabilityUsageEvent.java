package com.gmail.JyckoSianjaya.customdurability.CustomEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import com.gmail.JyckoSianjaya.customdurability.Events.CDEHandler.PlayerHand;

public class CustomDurabilityUsageEvent extends Event implements Cancellable {
	private final Player p;
	private final ItemStack item;
	private int totaldamage;
	private PlayerHand hand;
	private boolean isCancelled = false;
	private static final HandlerList handlers = new HandlerList();
	public CustomDurabilityUsageEvent(Player p, ItemStack item, int totaldamage, PlayerHand hand) {
		this.p = p;
		this.item = item;
		this.totaldamage = totaldamage;
		this.hand = hand;
	}
	public Player getPlayer() {
		return p;
	}
	public ItemStack getItem() {
		return item;
	}
	public int getTotalDamage() {
		return totaldamage;
	}
	public void setTotalDamage(int dmg) {
		this.totaldamage = dmg;
	}
	@SuppressWarnings("deprecation")
	public void setPlayerHand(PlayerHand h) {
		hand = h;
	}
	public PlayerHand getPlayerHand() {
		return hand;
	}
	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		// TODO Auto-generated method stub
		isCancelled = arg0;
	}

	@Override
	public HandlerList getHandlers() {
		// TODO Auto-generated method stub
		return handlers;
	}

}
