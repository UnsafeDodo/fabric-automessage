package unsafedodo.fabricautomessage.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import unsafedodo.fabricautomessage.AutoMessage;
import unsafedodo.fabricautomessage.config.ConfigManager;

import java.util.concurrent.TimeUnit;

import static unsafedodo.fabricautomessage.AutoMessage.*;

public class AutoMessageReload {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess  commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment){
        dispatcher.register(CommandManager.literal("automessage")
                .then(CommandManager.literal("reload")
                        .requires(Permissions.require("automessage.reload", 3))
                            .executes(AutoMessageReload::run)));

    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if (ConfigManager.loadConfig()) {
            context.getSource().sendFeedback(() -> Text.literal("Reloaded config!").formatted(Formatting.GREEN), false);
        } else {
            context.getSource().sendError(Text.literal("Error accrued while reloading config!").formatted(Formatting.RED));
        }

        //executorService.schedule(messagePrint,  AutoMessage.timeout, TimeUnit.SECONDS);
        scheduledFuture.cancel(false);
        scheduledFuture = executorService.scheduleAtFixedRate(messagePrint, 0, timeout, TimeUnit.SECONDS);
        return 1;
    }

}
