package unsafedodo.fabricautomessage;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.kyori.adventure.Adventure;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.Audiences;
import net.kyori.adventure.platform.fabric.FabricServerAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.Context;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.ArgumentQueue;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.json.LegacyHoverEventSerializer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import unsafedodo.fabricautomessage.config.ConfigManager;
import unsafedodo.fabricautomessage.util.CircularLinkedList;
import unsafedodo.fabricautomessage.util.CircularListNode;
import unsafedodo.fabricautomessage.util.Register;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class AutoMessage implements ModInitializer {

	static Tag createLink(final ArgumentQueue args, final Context ctx) {
		final String link = args.popOr("The <link> tag requires exactly one argument, the link to open").value();

		return Tag.styling(
				NamedTextColor.BLUE,
				TextDecoration.UNDERLINED,
				ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL, link),
				HoverEvent.showText(Component.text("Open " + link))
		);
	}

	Component linkTag(String input) {
		final MiniMessage extendedInstance = MiniMessage.builder()
				.tags(TagResolver.resolver("link", AutoMessage::createLink)).build();


		return extendedInstance.deserialize(input);
	}
	public static CircularLinkedList<String> messages;
	public static int timeout;

	static MiniMessage mm = MiniMessage.miniMessage();
	private static String currentMessage;

	/*private static final Pattern urlPattern = Pattern.compile(
			"(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
					+ "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
					+ "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
			Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);*/

	private static MinecraftServer runningServer;

	static ScheduledExecutorService executorService;

	static Runnable messagePrint = new Runnable() {
		@Override
		public void run() {
			if(messages.size() > 0){
				runningServer = getServer();
				if(runningServer.getPlayerManager().getCurrentPlayerCount() > 0){
					Component parsed = mm.deserialize(currentMessage);
					Audience players = FabricServerAudiences.of(runningServer).players();
					/*if(currentMessage.matches("(/https:\\/\\/www\\.|http:\\/\\/www\\.|https:\\/\\/|http:\\/\\/)?[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})(\\.[a-zA-Z]{2,})?\\/[a-zA-Z0-9]{2,}|((https:\\/\\/www\\.|http:\\/\\/www\\.|https:\\/\\/|http:\\/\\/)?[a-zA-Z]{2,}(\\.[a-zA-Z]{2,})(\\.[a-zA-Z]{2,})?)|(https:\\/\\/www\\.|http:\\/\\/www\\.|https:\\/\\/|http:\\/\\/)?[a-zA-Z0-9]{2,}\\.[a-zA-Z0-9]{2,}\\.[a-zA-Z0-9]{2,}(\\.[a-zA-Z0-9]{2,})? /gmi")){
						runningServer.getPlayerManager().broadcast(Text.literal(currentMessage).styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://netflix.com"))), false);
					}
					else{
						runningServer.getPlayerManager().broadcast(Text.literal(currentMessage), false);
					}*/
					players.sendMessage(parsed);
					//runningServer.getPlayerManager().broadcast(Text.literal(currentMessage), false);
					System.out.println(currentMessage);
					currentMessage = messages.getNextData(currentMessage);
				}
			}
		}
	};

	static {
		ServerLifecycleEvents.SERVER_STARTED.register(server -> {
			runningServer = server;

			executorService = new ScheduledThreadPoolExecutor(1);
			executorService.scheduleAtFixedRate(messagePrint, 0, AutoMessage.timeout, TimeUnit.SECONDS);
			System.out.println("Server started and instance saved");
		});
	}

	static {
		ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
			executorService.shutdown();
			System.out.println("Server stopped");
		});
	}

	@Override
	public void onInitialize() {
		if(!ConfigManager.loadConfig())
			throw new RuntimeException("Could not load config");

		Register.registerCommands();
		if(messages.size() > 0){
			currentMessage = messages.getFirst();
		}
	}

	public static MinecraftServer getServer(){
		return runningServer;
	}

}