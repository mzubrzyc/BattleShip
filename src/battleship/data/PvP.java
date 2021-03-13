package battleship.data;

public class PvP {

    private final BattleField battleFieldPlayer1;
    private final BattleField battleFieldPlayer2;

    private BattleField currentBattleFieldTemp;

    public PvP(BattleField battleFieldPlayer1, BattleField battleFieldPlayer2) {
        this.battleFieldPlayer1 = battleFieldPlayer1;
        this.battleFieldPlayer2 = battleFieldPlayer2;
        this.currentBattleFieldTemp = battleFieldPlayer1;
    }

    public BattleField getBattleFieldPlayer1() {
        return battleFieldPlayer1;
    }

    public BattleField getBattleFieldPlayer2() {
        return battleFieldPlayer2;
    }

    public BattleField currentBattleField() {
        return currentBattleFieldTemp;
    }
    public BattleField getEnemyBattleField() {

        BattleField enemyBattleField = currentBattleFieldTemp == battleFieldPlayer1 ? battleFieldPlayer2 :
                                       battleFieldPlayer1;
        currentBattleFieldTemp = enemyBattleField;

        return enemyBattleField;
    }

}
