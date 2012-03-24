package me.flungo.bukkit.MegaJump;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
	public final Logger logger = Logger.getLogger("MineCraft");
	
	public static MegaJump plugin;
	
	public Log(MegaJump instance) {
		plugin = instance;
	}
	
	public void logMessage(String msg) {
		logMessage(msg, Level.INFO);
	}
	
	public void logMessage(String msg, Level level) {
		logger.log(level, "[" + plugin.pdf.getName() + "] " + msg);
	}
}
