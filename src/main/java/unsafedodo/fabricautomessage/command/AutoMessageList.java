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

public class AutoMessageList {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment){
        dispatcher.register(CommandManager.literal("automessage")
                .then(CommandManager.literal("list")
                        .requires(Permissions.require("automessage.list", 3))
                            .executes(AutoMessageList::run)));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String msg = "";
        for(int i = 0; i < AutoMessage.messages.getSize(); i++){
            msg += "Index "+i+": "+AutoMessage.messages.get(i)+"\n";
        }
        String finalMsg = msg;
        context.getSource().sendFeedback(()-> Text.literal(finalMsg).formatted(Formatting.AQUA), false);
        return 0;
    }
}
