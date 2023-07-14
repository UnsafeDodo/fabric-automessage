package unsafedodo.fabricautomessage.util;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class Register {
    public static void registerCommands(){
        CommandRegistrationCallback.EVENT.register(unsafedodo.fabricautomessage.command.AutoMessageAdd::register);
        CommandRegistrationCallback.EVENT.register(unsafedodo.fabricautomessage.command.AutoMessageRemove::register);
        CommandRegistrationCallback.EVENT.register(unsafedodo.fabricautomessage.command.AutoMessageReload::register);
        CommandRegistrationCallback.EVENT.register(unsafedodo.fabricautomessage.command.AutoMessageSetTimeout::register);
        CommandRegistrationCallback.EVENT.register(unsafedodo.fabricautomessage.command.AutoMessageMain::register);
        CommandRegistrationCallback.EVENT.register(unsafedodo.fabricautomessage.command.AutoMessageList::register);
    }
}
