package utils;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

import java.util.ArrayList;
import java.util.Random;

public class RandomUtil {

    //随机生成的seed
    //static final Random random = ThreadLocalRandom.current();
    //使用固定seed生成的random
    public static final Random random = new Random();


    // return random Enum value
    public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

    // return random Card from Hand
    public static Card randomCard(Hand hand){
        int x = random.nextInt(hand.getNumberOfCards());
        return hand.get(x);
    }

    // return random Card from ArrayList
    public static Card randomCard(ArrayList<Card> list){
        int x = random.nextInt(list.size());
        return list.get(x);
    }

}
