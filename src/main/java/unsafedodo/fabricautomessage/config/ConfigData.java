package unsafedodo.fabricautomessage.config;

public class ConfigData {
    public int intervalInSeconds;
    public String[] messages;


    public ConfigData(int interv, String[] mess){
        this.intervalInSeconds = interv;
        this.messages = mess;
    }

    public ConfigData(){
        this.intervalInSeconds = 120;
        this.messages = new String[0];
    }
}
