package unsafedodo.fabricautomessage.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import unsafedodo.fabricautomessage.config.ConfigData;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;

public class JsonHandler {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public static boolean addString(String messageToAdd) throws FileNotFoundException {
        try{
            File configDir = Paths.get("", "config").toFile();
            File configFile = new File(configDir, "automessage.json");

            if(configFile.exists()){
                ConfigData configData = GSON.fromJson(new InputStreamReader(new FileInputStream(configFile), "UTF-8"), ConfigData.class);
                CircularLinkedList<String> jsonStrings = new CircularLinkedList<String>(Arrays.asList(configData.messages));
                jsonStrings.add(messageToAdd);
                {
                    Writer writer = new FileWriter(configFile);
                   // GSON.toJson(, writer);
                    writer.close();
                }


            }
            else {
                throw new FileNotFoundException("Config file not found");
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported Encoding");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
    public static boolean addInt(int timeout) throws FileNotFoundException {
        try{
            File configDir = Paths.get("", "config").toFile();
            File configFile = new File(configDir, "automessage.json");

            if(configFile.exists()){
                ConfigData configData = GSON.fromJson(new InputStreamReader(new FileInputStream(configFile), "UTF-8"), ConfigData.class);
            }
            else {
                throw new FileNotFoundException("Config file not found");
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported Encoding");
        }

        return true;
    }
}
