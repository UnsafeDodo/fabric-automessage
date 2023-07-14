package unsafedodo.fabricautomessage;

import net.fabricmc.api.ModInitializer;

import unsafedodo.fabricautomessage.config.ConfigManager;
import unsafedodo.fabricautomessage.util.Register;

public class AutoMessage implements ModInitializer {

	@Override
	public void onInitialize() {
		if(!ConfigManager.loadConfig())
			throw new RuntimeException("Could not load config");

		Register.registerCommands();
	}
}