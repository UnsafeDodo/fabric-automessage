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
import unsafedodo.fabricautomessage.config.ConfigManager;

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
        /*var old = ConfigManager.getConfig().allPossibleAutoCompletionKeys;
        if (ConfigManager.loadConfig()) {
            context.getSource().sendFeedback(() -> Text.literal("Reloaded config!"), false);

            for (var player : context.getSource().getServer().getPlayerManager().getPlayerList()) {
                StyledChatUtils.sendAutoCompletion(player, old);
            }
        } else {
            context.getSource().sendError(Text.literal("Error occurred while reloading config! Check console for more information!").formatted(Formatting.RED));
        }*/
        return 1;
    }

}
