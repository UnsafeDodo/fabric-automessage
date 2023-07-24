package unsafedodo.fabricautomessage.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import unsafedodo.fabricautomessage.AutoMessage;

public class AutoMessageList {
    static MiniMessage mm = MiniMessage.miniMessage();
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment){
        dispatcher.register(CommandManager.literal("automessage")
                .then(CommandManager.literal("list")
                        .requires(Permissions.require("automessage.list", 3))
                            .executes(AutoMessageList::run)));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Audience player = context.getSource().getPlayer();

        if(AutoMessage.messages.getSize() != 0){
            String msg = "-----------------------------------------------------\n";
            msg += String.format("<aqua>Timeout: <yellow>%d<reset>\n\n", AutoMessage.timeout);
            for(int i = 0; i < AutoMessage.messages.getSize(); i++){
                msg += "<reset><aqua>Index "+i+":</aqua>"+" "+AutoMessage.messages.get(i)+"\n";
            }
            msg += "<reset>-----------------------------------------------------";
            Component finalMsg = mm.deserialize(msg);
            player.sendMessage(finalMsg);
        }
        else
            context.getSource().sendFeedback(() -> Text.literal("There are no messages saved").formatted(Formatting.RED), false);

        return 0;
    }
}
