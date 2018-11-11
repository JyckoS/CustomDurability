package com.gmail.JyckoSianjaya.customdurability.damages;

public class DamageConfig {
	int block_break = 0;
	int entity_hit = 0;
	int tile_grass = 0;
	int frod_item_reel = 0;
	int frod_empty_reel = 0;
	int frod_yank_item = 0;
	int frod_yank_entity = 0;
	int carrot_on_stick = 0;
	int flint_n_steel = 0;
	int bow_arrow = 0;
	int flying_per_sec = 0;
	int shielddamage = 0;
	public DamageConfig() {
	}
	public void setFrod_Item_reel_dmg(int dmg) {
		this.frod_item_reel = dmg;
	}
	public void setFrod_empty_reel_dmg(int dmg) {
		this.frod_empty_reel = dmg;
	}
	public void setFrod_Yank_Item_dmg(int dmg) {
		this.frod_yank_item = dmg;
	}
	public void setFrod_Yank_Entity_Dmg(int dmg) {
		this.frod_yank_entity = dmg;
	}
	public void setCarrotOSBoostDmg(int dmg) {
		this.carrot_on_stick = dmg;
	}
	public void setFlintLitDmg(int dmg) {
		this.flint_n_steel = dmg;
	}
	public void setBowShootDmg(int dmg) {
		this.bow_arrow = dmg;
	}
	public void setElytraDPS(int dmg) {
		this.flying_per_sec = dmg;
	}
	public void setShieldDmg(int dmg) {
		this.shielddamage = dmg;
	}
	public void setTilingDmg(int dmg) {
		this.tile_grass = dmg;
	}
	public void setEntityDmg(int dmg) {
		this.entity_hit = dmg;
	}
	public void setBlockDmg(int dmg) {
		this.block_break = dmg;
	}
	public int getBlockDamage() {
		return block_break;
	}
	public int getEntityHitDmg() {
		return entity_hit;
	}
	public int getGrassTilingDmg() {
		return tile_grass;
	}
	public int getFrod_reels_item() {
		return frod_item_reel;
	}
	public int getFrod_reels_none() {
		return frod_empty_reel;
	}
	public int getFrod_Yanks_item() {
		return frod_yank_item;
	}
	public int getFrod_Yanks_entity() {
		return frod_yank_entity;
	}
	public int getCarrotOSBoost() {
		return carrot_on_stick;
	}
	public int getFlintBurnDmg() {
		return flint_n_steel;
	}
	public int getBowShootDmg() {
		return bow_arrow;
	}
	public int getElytraDPS() {
		return flying_per_sec;
	}
	public int getShieldDamage() {
		return shielddamage;
	}
}
