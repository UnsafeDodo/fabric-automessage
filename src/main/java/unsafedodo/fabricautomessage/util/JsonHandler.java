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
                /*ConfigData configData = GSON.fromJson(new InputStreamReader(new FileInputStream(configFile), "UTF-8"), ConfigData.class);
                CircularLinkedList<String> jsonStrings = new CircularLinkedList<String>(Arrays.asList(configData.messages));
                jsonStrings.add(messageToAdd);
                //configData.messages = (String[]) jsonStrings.toArray();*/
                AutoMessage.messages.add(messageToAdd);
                System.out.println("============================ DEBUGGHINO TATTICO ============================");
                System.out.println("VOGLIO I MSGS");
                Object[] msgs = AutoMessage.messages.toArray(); // Il tipo dichiarato di msgs deve essere uguale al tipo di ritorno di AutoMessage.messages.toArray()
                System.out.println(Arrays.toString(msgs)); // Qui controlli pure che i messaggi sono quelli che devono essere ;)
                System.out.println("HO I MSGS. ORA VOGLIO IL TIMEOUT");
                int timeout = AutoMessage.timeout;
                System.out.printf("Timeout = %d", timeout);
                System.out.println("HO IL TIMEOUT. ORA VOGLIO CONFIGDATA");
                ConfigData data = new ConfigData(timeout, (String[]) msgs);
                System.out.println("HO CONFIGDATA. ORA VOGLIO JSON");
                System.out.println(GSON.toJson(data));
                System.out.println("HO JSON. PERCHÉ NON FUNZIONO?!");
                System.out.println("========================= END OF DEBUGGHINO TATTICO ========================");
                {
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFile), "UTF-8"));
                    //GSON.toJson(configData, writer);
                    writer.write(GSON.toJson(new ConfigData(AutoMessage.timeout, (String[]) AutoMessage.messages.toArray())));
                    //GSON.toJson(new ConfigData(configData.intervalInSeconds, (String[])jsonStrings.toArray()));
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
                    writer.write(GSON.toJson(new ConfigData(AutoMessage.timeout, (String[]) AutoMessage.messages.toArray())));
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
        String mess = "";
        mess = AutoMessage.messages.get(index);
        if(mess != ""){
            try{
                File configDir = Paths.get("", "config").toFile();
                File configFile = new File(configDir, "automessage.json");

                if(configFile.exists()){
                    AutoMessage.messages.remove(index);
                    {
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(configFile), "UTF-8"));
                        writer.write(GSON.toJson(new ConfigData(AutoMessage.timeout, (String[]) AutoMessage.messages.toArray())));
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
        return false;
    }
}
