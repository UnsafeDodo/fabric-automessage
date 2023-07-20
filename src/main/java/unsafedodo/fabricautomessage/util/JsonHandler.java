package unsafedodo.fabricautomessage.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import unsafedodo.fabricautomessage.AutoMessage;
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
                AutoMessage.messages.add(messageToAdd);

                {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFile), "UTF-8"));
                    writer.write(GSON.toJson(new ConfigData(AutoMessage.timeout, AutoMessage.messages.objToStringArray(AutoMessage.messages.toArray()))));
                    writer.close();
                }
            }
            else {
                throw new FileNotFoundException("Config file not found");
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Unsupported Encoding");
        } catch (IOException e) {
            throw new RuntimeException("Runtime exception");
        }

        return true;
    }
    public static boolean addInt(int timeout) throws FileNotFoundException {
        try{
            File configDir = Paths.get("", "config").toFile();
            File configFile = new File(configDir, "automessage.json");

            if(configFile.exists()){
                AutoMessage.timeout = timeout;

                {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFile), "UTF-8"));
                    writer.write(GSON.toJson(new ConfigData(AutoMessage.timeout, AutoMessage.messages.objToStringArray(AutoMessage.messages.toArray()))));
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

    public static boolean removeString(int index){
        //check if works
        if(index+1 <= AutoMessage.messages.getSize()){

            String mess = AutoMessage.messages.get(index);
            if(mess != null){
                try{
                    File configDir = Paths.get("", "config").toFile();
                    File configFile = new File(configDir, "automessage.json");

                    if(configFile.exists()){
                        //check if .remove works
                        AutoMessage.messages.remove(index);

                        {
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFile), "UTF-8"));
                            writer.write(GSON.toJson(new ConfigData(AutoMessage.timeout, AutoMessage.messages.objToStringArray(AutoMessage.messages.toArray()))));
                            writer.close();
                            return true;
                        }
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return false;
    }
}
