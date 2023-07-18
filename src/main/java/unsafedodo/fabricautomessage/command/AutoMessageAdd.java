package unsafedodo.fabricautomessage.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import unsafedodo.fabricautomessage.util.JsonHandler;

import java.io.FileNotFoundException;

public class AutoMessageAdd {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment){
        dispatcher.register(CommandManager.literal("automessage")
                .then(CommandManager.literal("add")
                        .requires(Permissions.require("automessage.add", 3))
                            .then(CommandManager.argument("message", StringArgumentType.greedyString())
                                    .executes(AutoMessageAdd::run))));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        try{
            if(JsonHandler.addString(StringArgumentType.getString(context, "message")))
                context.getSource().sendFeedback(() -> Text.literal("Message added!").formatted(Formatting.GREEN), false);

        } catch (FileNotFoundException e){
            context.getSource().sendFeedback(() -> Text.literal("Config file not found").formatted(Formatting.RED), false);
        }
        return 0;
    }
}
