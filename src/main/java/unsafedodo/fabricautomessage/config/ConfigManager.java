package unsafedodo.fabricautomessage.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import unsafedodo.fabricautomessage.AutoMessage;
import unsafedodo.fabricautomessage.util.CircularLinkedList;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static unsafedodo.fabricautomessage.AutoMessage.executorService;
import static unsafedodo.fabricautomessage.AutoMessage.messagePrint;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static boolean loadConfig(){
        boolean success;
        try {
            File configDir = Paths.get("", "config").toFile();
            File configFile = new File(configDir, "automessage.json");

            ConfigData configData = configFile.exists() ? GSON.fromJson(new InputStreamReader(new FileInputStream(configFile), "UTF-8"), ConfigData.class) : new ConfigData();

            {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFile), "UTF-8"));
                writer.write(GSON.toJson(configData));
                writer.close();
            }

            //new addition
            AutoMessage.messages = new CircularLinkedList<String>(Arrays.asList(configData.messages));
            AutoMessage.timeout = configData.intervalInSeconds;

            success = true;

        } catch (IOException e){
            success = false;
        }

        return success;
    }
}
