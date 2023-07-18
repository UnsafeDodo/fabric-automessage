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
import unsafedodo.fabricautomessage.util.JsonHandler;

public class AutoMessageRemove {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment){
        dispatcher.register(CommandManager.literal("automessage")
                .then(CommandManager.literal("remove")
                        .requires(Permissions.require("automessage.remove", 3))
                            .then(CommandManager.argument("index", IntegerArgumentType.integer(0))
                                    .executes(AutoMessageRemove::run))));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if(JsonHandler.removeString(IntegerArgumentType.getInteger(context, "index")))
            context.getSource().sendFeedback(() -> Text.literal("Message removed").formatted(Formatting.GREEN), false);
        else
            context.getSource().sendFeedback(() -> Text.literal("Message not found").formatted(Formatting.RED), false);
        return 0;
    }
}
