package unsafedodo.fabricautomessage;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.fabric.FabricServerAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.server.MinecraftServer;
import unsafedodo.fabricautomessage.config.ConfigManager;
import unsafedodo.fabricautomessage.util.CircularLinkedList;
import unsafedodo.fabricautomessage.util.Register;

import java.util.concurrent.*;
public class AutoMessage implements ModInitializer {
	public static CircularLinkedList<String> messages;
	public static int timeout;
	static MiniMessage mm = MiniMessage.miniMessage();
	private static String currentMessage = null;
	private static MinecraftServer runningServer;
	static ScheduledThreadPoolExecutor ex = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
	public static ScheduledExecutorService executorService;
	public static ScheduledFuture<?> scheduledFuture;
	public static Runnable messagePrint = new Runnable() {
		@Override
		public void run() {
			if(messages.size() > 0){
				runningServer = getServer();
				if(runningServer.getPlayerManager().getCurrentPlayerCount() > 0){
					currentMessage = messages.getNextData(currentMessage);

					Component parsed = mm.deserialize(currentMessage);
					Audience players = FabricServerAudiences.of(runningServer).players();
					players.sendMessage(parsed);
				}
			}
		}
	};

	static {
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			runningServer = server;

			ex.setRemoveOnCancelPolicy(true);
			executorService = ex;

			scheduledFuture = executorService.scheduleAtFixedRate(messagePrint, 0, AutoMessage.timeout, TimeUnit.SECONDS);
		});
	}

	static {
		ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
			executorService.shutdown();
		});
	}

	@Override
	public void onInitialize() {
		if(!ConfigManager.loadConfig())
			throw new RuntimeException("Could not load config");

		Register.registerCommands();
	}

	public static MinecraftServer getServer(){
		return runningServer;
	}

}