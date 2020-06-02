package properties;

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
        InputStream fps = null;
        fps = Configure.class.getClassLoader().getResourceAsStream("properties/whist.properties");
        gameProperties.load(fps);

    }

    public String values(String key){
        return gameProperties.getProperty(key);
    }

}
