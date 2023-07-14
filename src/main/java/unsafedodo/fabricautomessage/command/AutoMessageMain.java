package unsafedodo.fabricautomessage.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class AutoMessageMain {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment){
        dispatcher.register(CommandManager.literal("automessage")
                .requires(Permissions.require("automessage.main", 3))
                        .then(CommandManager.literal("help")
                                .requires(Permissions.require("automessage.help", 3))
                                    .executes(AutoMessageMain::runHelp))
                    .executes(AutoMessageMain::run));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String git = "https://github.com/UnsafeDodo/fabric-automessage";
        context.getSource().sendFeedback(()->Text.literal("AutoChat by UnsafeDodo is running!\nCheck the GitHub repository for usage:").formatted(Formatting.GREEN), false);
        context.getSource().sendFeedback(()->Text.literal(git).formatted(Formatting.GREEN).styled(style -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, git))), false);
        return 0;
    }

    public static int runHelp(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String msg = "/automessage add <message here>\n/automessage list\n/automessage remove <index>\n/automessage settimeout <time in seconds>\n/automessage reload";
        context.getSource().sendFeedback(()->Text.literal(msg).formatted(Formatting.YELLOW), false);
        return 0;
    }
}
