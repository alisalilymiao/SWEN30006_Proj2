package player;

import properties.Configure;

import java.util.ArrayList;

public class PlayerFactory {

    public ArrayList<Player> initPlayer(int pattern){
        ArrayList<Player> players = new ArrayList<>();
        switch (pattern){
            case 1:
                int interactivePlayerNum = Integer.parseInt(Configure.values("interactivePlayerNum"));
                int randomPlayerNum = Integer.parseInt(Configure.values("randomPlayerNum"));
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
                int legalPlayerNum = Integer.parseInt(Configure.values("legalPlayerNum"));
                int index2 = 0;
                while (legalPlayerNum>0){

                }
        }
        return players;
    }

}
