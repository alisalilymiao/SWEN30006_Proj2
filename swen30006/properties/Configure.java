package properties;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configure {
    private static Configure configure = new Configure();

    private Configure(){

    }

    public static Configure getInstance(){
        return configure;
    }

    private Properties gameProperties;

    public void setGameProperties() throws IOException {
        if (gameProperties == null){
            gameProperties = new Properties();
        }
        FileReader fps = new FileReader("whist.properties");

       //fps = Configure.class.getClassLoader().getResourceAsStream("whist.properties");
        gameProperties.load(fps);

    }

    public String values(String key){
        return gameProperties.getProperty(key);
    }

}
