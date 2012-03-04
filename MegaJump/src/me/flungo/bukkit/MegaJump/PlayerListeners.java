package me.flungo.bukkit.MegaJump;

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
		if(event.getTo().getY() > event.getFrom().getY()) {
			Player p = event.getPlayer();
			Location loc = p.getLocation();
			Vector velo = p.getVelocity();
			double velo_x = velo.getX() * 2;
			double velo_y = velo.getY() * 2;
			double velo_z = velo.getZ() * 2;
			Vector velo_new = new Vector(velo_x, velo_y, velo_z);
			p.setVelocity(velo_new);
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
