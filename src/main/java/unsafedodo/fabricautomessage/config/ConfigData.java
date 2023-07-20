package unsafedodo.fabricautomessage.config;

public class ConfigData {
    public int intervalInSeconds;
    public String[] messages;


    public ConfigData(int interval, String[] mess){
        this.intervalInSeconds = interval;
        this.messages = mess;
    }

    public ConfigData(){
        this.intervalInSeconds = 120;
        this.messages = new String[0];
    }
}
