package me.flungo.bukkit.MegaJump;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class PlayerListeners implements Listener {
	public static MagaJump plugin;
	
	public PlayerListeners(MagaJump instance) {
		plugin = instance;
	}

	@EventHandler
	public void onPlayerJump(PlayerMoveEvent event) {
		Player p = event.getPlayer();

		Location from = event.getFrom();
		Location to = event.getTo();
		
		if(to.getY() > from.getY() && plugin.activePlayers.contains(p.getName())) {
			Location loc = p.getLocation();
			double loc_y = loc.getY();
			loc.setY(loc_y);
			Block floor = loc.getBlock();
			if (floor.getTypeId() != 0) {
				double velo_x = (to.getX() - from.getX()) * 1;
				//double velo_x = 1;
				double velo_y = (to.getX() - from.getX()) * 1;
				//double velo_y = 2;
				double velo_z = (to.getX() - from.getX()) * 1;
				//double velo_z = 1;
				
				//Vector velo_new = new Vector(velo_x, velo_y, velo_z);
				//p.setVelocity(velo_new);
				
				p.sendMessage(ChatColor.GREEN + "Your velocity is: " + ChatColor.RED + velo_x + ", " + velo_y + ", " + velo_z);
			}
			
		}
	}
	
	@EventHandler
	public void onFallDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player p = (Player) event.getEntity();
			if (plugin.activePlayers.contains(p.getName())) {
				event.setCancelled(true);
			}
		}
	}
}
