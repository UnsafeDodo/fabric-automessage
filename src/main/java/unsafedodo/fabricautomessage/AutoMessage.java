package unsafedodo.fabricautomessage;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.TellRawCommand;
import net.minecraft.util.Unit;
import net.minecraft.util.thread.ThreadExecutor;
import org.intellij.lang.annotations.RegExp;
import unsafedodo.fabricautomessage.config.ConfigManager;
import unsafedodo.fabricautomessage.util.CircularLinkedList;
import unsafedodo.fabricautomessage.util.CircularListNode;
import unsafedodo.fabricautomessage.util.Register;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class AutoMessage implements ModInitializer {
	public static CircularLinkedList<String> messages;
	public static int timeout;

	private static String currentMessage;

	@Override
	public void onInitialize() {
		if(!ConfigManager.loadConfig())
			throw new RuntimeException("Could not load config");

		Register.registerCommands();
		if(messages.size() > 0){
			currentMessage = messages.getFirst();
		}
		//ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
		//executorService.scheduleAtFixedRate(messagePrint, 0, AutoMessage.timeout, TimeUnit.SECONDS);
	}

	/*Runnable messagePrint = new Runnable() {
		@Override
		public void run() {
			if(messages.size() > 0){
				if(currentMessage.matches("(https:\\/\\/www\\.|http:\\/\\/www\\.|https:\\/\\/|http:\\/\\/)?[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})(\\.[a-zA-Z]{2,})?\\/[a-zA-Z0-9]{2,}|((https:\\/\\/www\\.|http:\\/\\/www\\.|https:\\/\\/|http:\\/\\/)?[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})(\\.[a-zA-Z]{2,})?)|(https:\\/\\/www\\.|http:\\/\\/www\\.|https:\\/\\/|http:\\/\\/)?[a-zA-Z0-9]{2,}\\.[a-zA-Z0-9]{2,}\\.[a-zA-Z0-9]{2,}(\\.[a-zA-Z0-9]{2,})? ")){

				}
				else{

				}
				currentMessage = messages.getNextData(currentMessage);
			}
		}
	};*/
}