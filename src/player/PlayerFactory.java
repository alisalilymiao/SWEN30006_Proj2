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
                int index = 0;
                while(interactivePlayerNum >0){
                    players.add(new InteractivePlayer(index, false));
                    index++;
                    interactivePlayerNum--;
                }
                while (randomPlayerNum>0){
                    players.add(new RandomPlayer(index,false));
                    index++;
                    randomPlayerNum--;
                }
                break;

            case 2:
                legalPlayerNum = Integer.parseInt(Configure.values("legalPlayerNum"));
                int index2 = 0;
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
                    players.add(new LegalPlayer(index2, true));
                    index2++;
                    legalPlayerNum--;
                }
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

                int index3 = 0;
                while (interactivePlayerNum>0){
                    players.add(new InteractivePlayer(index3, false));
                    index3++;
                    interactivePlayerNum--;
                }
                while (smartPlayerNum > 0){
                    players.add(new SmartPlayer(index3,true));
                    smartPlayerNum--;
                    index3++;
                }
                while (randomPlayerNum>0){
                    players.add(new RandomPlayer(index3,false));
                    index3++;
                    randomPlayerNum--;
                }
                break;

        }
        return players;
    }

}
