package me.flungo.bukkit.MegaJump;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MagaJump extends JavaPlugin {
	
	public static MagaJump plugin;
	
	public final Logger logger = Logger.getLogger("MineCraft");
	
	public final PlayerListeners playerListener = new PlayerListeners(this);
	
	public ArrayList<String> activePlayers = new ArrayList<String>();
	
	public int defaultMultiplier;
	
	public boolean debug;
	
	public void onDisable() {
		PluginDescriptionFile pdffile = this.getDescription();
		this.logger.info(pdffile.getName() + " is now disabled");
	}
	
	public void onEnable() {
		enable();
		PluginDescriptionFile pdffile = this.getDescription();
		this.logger.info(pdffile.getName() + " version " + pdffile.getVersion() + " is enabled.");
	}
	
	private void enable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this.playerListener, this);
		getConfig().options().copyDefaults(true);
		saveConfig();
		defaultMultiplier = getConfig().getInt("default-multiplier");
		debug = getConfig().getBoolean("debug");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("mj")) {			
			if (sender instanceof Player == false) {
				logMessage("The /mj command can only be used in game.");
				//TODO: Check for a player name argument and set the amplifier argument.
				
				return true;
			} else {
				Player p = (Player) sender;
				String pName = p.getName();
				
				if (activePlayers.contains(pName)) {
					p.sendMessage(ChatColor.RED + "MegaJump disabled");
					activePlayers.remove(pName);
					return true;
				} else {
					p.sendMessage(ChatColor.RED + "MegaJump enabled");
					activePlayers.add(pName);
					return true;
				}
			}
		}
		
		return false;
	}

	public void logMessage(String msg) {
		PluginDescriptionFile pdFile = this.getDescription();
		this.logger.info(pdFile.getName() + " " + pdFile.getVersion() + " " + msg);
	}
}
