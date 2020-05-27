package player;

import game.QuantityAnomalyException;
import properties.Configure;

import java.util.ArrayList;

public class PlayerFactory {

    public ArrayList<Player> initPlayer(int pattern){
        ArrayList<Player> players = new ArrayList<>();
        int interactivePlayerNum;
        int randomPlayerNum;
        int legalPlayerNum;
        int smartPlayerNum;
        switch (pattern){
            case 1:
                interactivePlayerNum = Integer.parseInt(Configure.values("interactivePlayerNum"));
                randomPlayerNum = Integer.parseInt(Configure.values("randomPlayerNum"));
                if (interactivePlayerNum + randomPlayerNum != 4){
                    try {
                        throw(new QuantityAnomalyException());
                    } catch (QuantityAnomalyException e) {
                        e.printStackTrace();
                        System.out.println("The total number of people playing the game should be four");
                        System.exit(0);
                    }
                }
                while(interactivePlayerNum >0){
                    players.add(new InteractivePlayer());
                    interactivePlayerNum--;
                }
                while (randomPlayerNum>0){
                    players.add(new RandomPlayer());
                    randomPlayerNum--;
                }
                break;

            case 2:
                legalPlayerNum = Integer.parseInt(Configure.values("legalPlayerNum"));
                if (legalPlayerNum != 4){
                    try {
                        throw(new QuantityAnomalyException());
                    } catch (QuantityAnomalyException e) {
                        e.printStackTrace();
                        System.out.println("The total number of people playing the game should be four");
                        System.exit(0);
                    }
                }
                while (legalPlayerNum>0){
                    players.add(new LegalPlayer());
                    legalPlayerNum--;
                }
                break;

            case 3:
                interactivePlayerNum = Integer.parseInt(Configure.values("interactivePlayerNum"));
                randomPlayerNum = Integer.parseInt(Configure.values("randomPlayerNum"));
                smartPlayerNum = Integer.parseInt(Configure.values("smartPlayerNum"));

                if (interactivePlayerNum+randomPlayerNum+smartPlayerNum != 4){
                    try {
                        throw(new QuantityAnomalyException());
                    } catch (QuantityAnomalyException e) {
                        e.printStackTrace();
                        System.out.println("The total number of people playing the game should be four");
                        System.exit(0);
                    }
                }

                while (interactivePlayerNum>0){
                    players.add(new InteractivePlayer());
                    interactivePlayerNum--;
                }
                while (smartPlayerNum > 0){
                    players.add(new SmartPlayer());
                    smartPlayerNum--;
                }
                while (randomPlayerNum>0){
                    players.add(new RandomPlayer());
                    randomPlayerNum--;
                }
                break;

        }
        return players;
    }

}
