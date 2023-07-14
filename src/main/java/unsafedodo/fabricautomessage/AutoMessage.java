package unsafedodo.fabricautomessage;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unsafedodo.fabricautomessage.config.ConfigData;
import unsafedodo.fabricautomessage.config.ConfigManager;

public class AutoMessage implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.

	@Override
	public void onInitialize() {
		ConfigManager.loadConfig();
	}
}