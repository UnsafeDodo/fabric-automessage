package unsafedodo.fabricautomessage.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class AutoMessageAdd {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment){
        dispatcher.register(CommandManager.literal("automessage")
                .then(CommandManager.literal("add")
                        .requires(Permissions.require("automessage.add", 3))
                            .then(CommandManager.argument("message", StringArgumentType.greedyString())
                                    .executes(AutoMessageAdd::run))));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        System.out.println("run add command");
        return 0;
    }
}