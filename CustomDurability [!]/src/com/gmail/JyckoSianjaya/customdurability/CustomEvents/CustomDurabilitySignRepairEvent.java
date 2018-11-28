package com.gmail.JyckoSianjaya.customdurability.CustomEvents;


import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.gmail.JyckoSianjaya.customdurability.repairsign.RepairSign;

public class CustomDurabilitySignRepairEvent extends Event implements Cancellable {
	private Boolean isCancelled = false;
	private OfflinePlayer player = null;
	private static final HandlerList handlers = new HandlerList();
	private RepairSign sign = null;
	public CustomDurabilitySignRepairEvent(Player p, RepairSign sign) {
		this.player = p;
		this.sign = sign;
	}
	public RepairSign getRepairSign() {
		return sign;
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
	public Player getPlayer() {
		if (player != null && player.isOnline()) return (Player) player;
		return null;
	}
	

}
