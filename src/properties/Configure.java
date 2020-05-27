package properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configure {

    private static Properties gameProperties;

    public static void setGameProperties(int index) throws IOException {
        if (gameProperties == null){
            gameProperties = new Properties();
        }
        InputStream fps = null;
        switch (index){
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

    public static String values(String key){
        return gameProperties.getProperty(key);
    }

}
