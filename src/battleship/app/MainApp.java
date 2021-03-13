package battleship.app;

import battleship.data.BattleField;
import battleship.data.GameInterface;
import battleship.data.PvP;

/**
 * Battleship App
 *
 * @author Michał Zubrzycki
 *
 * */

public class MainApp {

    public static void main(String[] args) {

        BattleField bfPlayer1 = new BattleField("Player 1");
        BattleField bfPlayer2 = new BattleField("Player 2");

        PvP pvp = new PvP(bfPlayer1, bfPlayer2);

        GameInterface gameInterface = new GameInterface(pvp);

        gameInterface.run();

    }
}
