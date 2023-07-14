package unsafedodo.fabricautomessage.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class AutoMessageSetTimeout {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment){
        dispatcher.register(CommandManager.literal("automessage")
                .then(CommandManager.literal("settimeout")
                        .requires(Permissions.require("automessage.settimeout", 3))
                            .then(CommandManager.argument("timeout", IntegerArgumentType.integer(0))
                                    .executes(AutoMessageSetTimeout::run))));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        System.out.println("run settimeout command");
        return 0;
    }
}
