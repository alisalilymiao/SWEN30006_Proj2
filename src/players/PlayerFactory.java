package players;

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
                interactivePlayerNum = Integer.parseInt(Configure.getInstance().values("interactivePlayerNum"));
                randomPlayerNum = Integer.parseInt(Configure.getInstance().values("randomPlayerNum"));
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
                    players.add(new Player("interactive"));
                    interactivePlayerNum--;
                }
                while (randomPlayerNum>0){
                    players.add(new Player("random"));
                    randomPlayerNum--;
                }
                break;

            case 2:
                legalPlayerNum = Integer.parseInt(Configure.getInstance().values("legalPlayerNum"));
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
                    players.add(new Player("legal"));
                    legalPlayerNum--;
                }
                break;

            case 3:
                interactivePlayerNum = Integer.parseInt(Configure.getInstance().values("interactivePlayerNum"));
                randomPlayerNum = Integer.parseInt(Configure.getInstance().values("randomPlayerNum"));
                smartPlayerNum = Integer.parseInt(Configure.getInstance().values("smartPlayerNum"));

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
                    players.add(new Player("interactive"));
                    interactivePlayerNum--;
                }
                while (smartPlayerNum > 0){
                    players.add(new Player("smart"));
                    smartPlayerNum--;
                }
                while (randomPlayerNum>0){
                    players.add(new Player("random"));
                    randomPlayerNum--;
                }
                break;

        }
        return players;
    }

}
