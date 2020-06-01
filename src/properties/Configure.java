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

    public void setGameProperties(int index) throws IOException
    {
        if (gameProperties == null){
            gameProperties = new Properties();
        }
        InputStream fps = null;
        switch (index)
        {
            case 1:
                fps = Configure.class.getClassLoader().getResourceAsStream("properties/original.properties");
                gameProperties.load(fps);
                break;
            case 2:
                fps = Configure.class.getClassLoader().getResourceAsStream("properties/legal.properties");
                gameProperties.load(fps);
                break;
            case 3:
                fps = Configure.class.getClassLoader().getResourceAsStream("properties/smart.properties");
                gameProperties.load(fps);
                break;
        }

    }

    public String values(String key){
        return gameProperties.getProperty(key);
    }

}
