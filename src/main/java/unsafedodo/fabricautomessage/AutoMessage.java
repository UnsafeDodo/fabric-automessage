package unsafedodo.fabricautomessage;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import unsafedodo.fabricautomessage.config.ConfigManager;
import unsafedodo.fabricautomessage.util.CircularLinkedList;
import unsafedodo.fabricautomessage.util.Register;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AutoMessage implements ModInitializer {
	public static CircularLinkedList<String> messages;
	public static int timeout;

	private static String currentMessage;

	private static MinecraftServer server;

	@Override
	public void onInitialize() {

		ServerLifecycleEvents.SERVER_STARTED.register(minecraftServer -> {
			server = minecraftServer;
		});

		if(!ConfigManager.loadConfig())
			throw new RuntimeException("Could not load config");

		Register.registerCommands();
		if(messages.size() > 0){
			currentMessage = messages.getFirst();
		}

		ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
		executorService.scheduleAtFixedRate(messagePrint, 0, AutoMessage.timeout, TimeUnit.SECONDS);
	}

	public static MinecraftServer getServer(){
		return server;
	}

	Runnable messagePrint = new Runnable() {
		@Override
		public void run() {
			if(messages.size() > 0){
				server = getServer();
				if(currentMessage.matches("(https:\\/\\/www\\.|http:\\/\\/www\\.|https:\\/\\/|http:\\/\\/)?[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})(\\.[a-zA-Z]{2,})?\\/[a-zA-Z0-9]{2,}|((https:\\/\\/www\\.|http:\\/\\/www\\.|https:\\/\\/|http:\\/\\/)?[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})(\\.[a-zA-Z]{2,})?)|(https:\\/\\/www\\.|http:\\/\\/www\\.|https:\\/\\/|http:\\/\\/)?[a-zA-Z0-9]{2,}\\.[a-zA-Z0-9]{2,}\\.[a-zA-Z0-9]{2,}(\\.[a-zA-Z0-9]{2,})? ")){
					server.getPlayerManager().broadcast(Text.literal(currentMessage).styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "netflix.com"))), true);
				}
				else{
					server.getPlayerManager().broadcast(Text.literal(currentMessage), true);
				}
				currentMessage = messages.getNextData(currentMessage);

				System.out.println(currentMessage);
			}
		}
	};
}