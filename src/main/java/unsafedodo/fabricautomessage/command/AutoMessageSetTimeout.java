package unsafedodo.fabricautomessage.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import unsafedodo.fabricautomessage.AutoMessage;
import unsafedodo.fabricautomessage.util.JsonHandler;

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;

import static unsafedodo.fabricautomessage.AutoMessage.*;

public class AutoMessageSetTimeout {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment){
        dispatcher.register(CommandManager.literal("automessage")
                .then(CommandManager.literal("settimeout")
                        .requires(Permissions.require("automessage.settimeout", 3))
                            .then(CommandManager.argument("timeout", IntegerArgumentType.integer(0))
                                    .executes(AutoMessageSetTimeout::run))));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        try{
            if(JsonHandler.addInt(IntegerArgumentType.getInteger(context, "timeout")))
                context.getSource().sendFeedback(() -> Text.literal("You have changed the timeout!").formatted(Formatting.GREEN), false);
        } catch (FileNotFoundException e){
            context.getSource().sendFeedback(() -> Text.literal("Config file not found").formatted(Formatting.RED), false);
        }

        scheduledFuture.cancel(false);
        scheduledFuture = executorService.scheduleAtFixedRate(messagePrint, 0, timeout, TimeUnit.SECONDS);
        return 0;
    }
}
