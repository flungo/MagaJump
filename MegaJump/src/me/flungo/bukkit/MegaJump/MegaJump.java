package me.flungo.bukkit.MegaJump;

import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MegaJump extends JavaPlugin {
	
	public static MegaJump plugin;
    
    public PluginManager pm;
    
    public PluginDescriptionFile pdf;
	
	public final Log log = new Log(this);
	
	public final PlayerListeners playerListener = new PlayerListeners(this);
	
	public HashMap<Player, Integer> activePlayers = new HashMap<Player, Integer>();
	
	public int defaultMultiplier;
	
	public boolean debug;
	
	public void onDisable() {
		this.log.logMessage(pdf.getName() + " is now disabled");
	}
	
	public void onEnable() {
		pdf = this.getDescription();
		enable();
		this.log.logMessage(pdf.getName() + " version " + pdf.getVersion() + " is enabled.");
	}
	
	private void enable() {
		pm = getServer().getPluginManager();
		pm.registerEvents(this.playerListener, this);
		getConfig().options().copyDefaults(true);
		saveConfig();
		defaultMultiplier = getConfig().getInt("default-multiplier");
		debug = getConfig().getBoolean("debug");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("mj")) {	
			int amp;		
			if (sender instanceof Player == false) {
				logMessage("The /mj command can only be used in game.");
				//TODO: Check for a player name argument and set the amplifier argument.
				
				return true;
			} else {
				if (args.length == 0) {
					Player p = (Player) sender;
					amp = defaultMultiplier;
					if (activePlayers.containsKey(p)) {
						p.sendMessage(ChatColor.RED + "MegaJump disabled");
						activePlayers.remove(p);
						return true;
					} else {
						p.sendMessage(ChatColor.RED + "MegaJump enabled");
						activePlayers.put(p,amp);
						return true;
					}
				} else if (args.length == 1) {
					String pName = null;
					try {
						amp = Integer.parseInt(args[0]);
					} catch (NumberFormatException e1) {
						amp = defaultMultiplier;
						pName = args[0];
					}
					if (pName == null) {
						Player p = (Player) sender;
						if (activePlayers.containsKey(p)) {
							p.sendMessage(ChatColor.RED + "MegaJump set to amplification factor " + amp);
						} else {
							p.sendMessage(ChatColor.RED + "MegaJump enabled with amplification factor " + amp);
						}
						activePlayers.put(p,amp);
						return true;
					} else {
						Player p1 = (Player) sender;
						Player p2 = getServer().getPlayerExact(pName);
						if (p2 == null) {
							p1.sendMessage("MegaJump cannot find specified user");
						} else {
							if (activePlayers.containsKey(p2)) {
								p2.sendMessage(ChatColor.RED + "MegaJump disabled");
								p1.sendMessage("MegaJump disabled for " + pName);
								activePlayers.remove(p2);
								return true;
							} else {
								p2.sendMessage(ChatColor.RED + "MegaJump enabled");
								p1.sendMessage("MegaJump enabled for " + pName);
								activePlayers.put(p2,amp);
								return true;
							}
						}
						return true;
					}
				} else if (args.length == 2) {
					String pName = args[0];
					Player p1 = (Player) sender;
					Player p2 = getServer().getPlayerExact(pName);
					if (p2 == null) {
						p1.sendMessage("MegaJump cannot find specified user");
					} else {
						try {
							amp = Integer.parseInt(args[1]);
						} catch (NumberFormatException e1) {
							return false;
						}
						if (activePlayers.containsKey(p2)) {
							p1.sendMessage("MegaJump enabled set to amplification factor " + amp + " for " + pName);
							p2.sendMessage(ChatColor.RED + "MegaJump set to amplification factor " + amp);
						} else {
							p1.sendMessage("MegaJump enabled for " + pName + " with amplification factor " + amp);
							p2.sendMessage(ChatColor.RED + "MegaJump enabled with amplification factor " + amp);
						}
						activePlayers.put(p2,amp);
						return true;
					}
				}
			}
		}
		
		return false;
	}

	public void logMessage(String msg) {
		PluginDescriptionFile pdFile = this.getDescription();
		this.log.logMessage(pdFile.getName() + " " + pdFile.getVersion() + " " + msg);
	}
}
