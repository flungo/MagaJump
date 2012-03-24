package me.flungo.bukkit.MegaJump;

import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.permission.Permission;

public class Permissions {
	public static MegaJump plugin;
	
	public Permissions(MegaJump instance) {
		plugin = instance;
	}
	
	private static Log log = plugin.log;
	
	private static boolean op;
	
	private static boolean bukkit;
	
	private static boolean vault;
	
	private static Permission vaultPermission = null;
	
	private void setupOPPermissions() {
		if (plugin.getConfig().getBoolean("permissions.op")) {
			log.logMessage("Attempting to configure OP permissions");
			op = true;
		} else {
			log.logMessage("OP permissions disabled by config");
			op = false;
		}
	}
	
	private void setupBukkitPermissions() {
		if (plugin.getConfig().getBoolean("permissions.bukkit")) {
			log.logMessage("Attempting to configure Bukkit Super Permissions");
			bukkit = true;
		} else {
			log.logMessage("Bukkit Super Permissions disabled by config");
			bukkit = false;
		}
	}
	
	private void setupVaultPermissions() {
		if (plugin.getConfig().getBoolean("permissions.vault")) {
			log.logMessage("Attempting to configure Vault permissions");
			if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
				log.logMessage("Vault could not be found", Level.SEVERE);
				vault = false;
			} else {
				RegisteredServiceProvider<Permission> permissionProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		        if (permissionProvider != null) {
		        	vaultPermission = permissionProvider.getProvider();
		        }
				if (vaultPermission != null)
					vault = true;
				else 
					vault = false;
			}
		} else {
			log.logMessage("Vault permissions disabled by config");
			vault = false;
		}
    }
	
	public void setupPermissions() {
		setupOPPermissions();
		if (op) {
			log.logMessage("OP permissions set up");
		} else {
			log.logMessage("OP permissions not set up", Level.WARNING);
		}
		setupBukkitPermissions();
		if (bukkit) {
			log.logMessage("Bukkit Super Permissions set up");
		} else {
			log.logMessage("Bukkit Super Permissions not set up", Level.WARNING);
		}
		setupVaultPermissions();
		if (vault) {
			log.logMessage("Vault permissions set up");
		} else {
			log.logMessage("Vault permissions not set up", Level.WARNING);
		}
		if (!vault && !bukkit) {
			log.logMessage("No permission systems have been set up. Default permissions will be used.", Level.WARNING);
			if (!op) {
				log.logMessage("Additionally, OP permissions disabled.", Level.WARNING);
			}
		}
	}
	
	private boolean hasNode(Player p, String node) {
		if (bukkit && p.hasPermission(node)) return true;
		if (vault && vaultPermission.has(p, node)) return true;
		return false;
	}
	
	public boolean isAdmin(Player p) {
		if (plugin.getConfig().getBoolean("permissions.default.admin")) return true;
		if (p.isOp() && op) return true;
		String node = "voidwarp.admin";
		if (hasNode(p, node)) return true;
		return false;
	}
	
	public boolean isUser(Player p) {
		if (!plugin.getConfig().getBoolean("enable")) return false;
		if (plugin.getConfig().getBoolean("permissions.nodes.user")) {
			String node = "voidwarp.user";
			if (hasNode(p, node)) return true;
		}
		if (isAdmin(p)) return true;
		if (!bukkit && !vault) return true;
		return false;
	}
	
	public boolean isJumper(Player p) {
		if (!plugin.getConfig().getBoolean("enable")) return false;
		if (plugin.getConfig().getBoolean("permissions.nodes.jumper")) {
			String node = "voidwarp.user";
			if (hasNode(p, node)) return true;
		}
		if (isAdmin(p)) return true;
		if (!bukkit && !vault) return true;
		return false;
	}
	
}
