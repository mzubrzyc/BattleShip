package battleship.data;

import java.util.HashSet;
import java.util.Set;

public class BattleField {

    private final int FIELD_SIZE;

    private final String playerName;

    private final Position[][] battlefieldPositions;

    private final Set<Ship> shipsAtTheBattlefield;

    public BattleField(String playerName) {
        this.playerName = playerName;
        this.FIELD_SIZE = 10;
        this.battlefieldPositions = new Position[FIELD_SIZE][FIELD_SIZE];
        this.shipsAtTheBattlefield = new HashSet<>();
        initializePositions();
    }

    private void initializePositions() {
        for (int i = 0; i < battlefieldPositions.length; i++) {
            for (int j = 0; j < battlefieldPositions[i].length; j++) {
                battlefieldPositions[i][j] = new Position(j, i);

            }

        }

    }

    /**
     *
     * @param battlefieldType indicates different printing attribute to print battlefield positions
     *
     * */
    public void printBattleField(BattlefieldType battlefieldType) {

        boolean isFogOfWar = battlefieldType.equals(BattlefieldType.FOG_OF_WAR);

        String[] xAxis = new String[]{" ", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        char[] yAxis = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};

        for (String c : xAxis) {
            System.out.print(c + " ");
        }

        System.out.println();

        for (int j = 0; j < battlefieldPositions.length; j++) {
            System.out.print(yAxis[j] + " ");
            for (int i = 0; i < battlefieldPositions[j].length; i++) {
                MarkSymbol markSymbolWithShips = battlefieldPositions[j][i].getMark();
                MarkSymbol markSymbolMissHit =
                        markSymbolWithShips.equals(MarkSymbol.HIT) ? MarkSymbol.HIT : markSymbolWithShips.equals(
                                MarkSymbol.MISS) ? MarkSymbol.MISS : MarkSymbol.EMPTY;
                MarkSymbol markSymbol = isFogOfWar ? markSymbolMissHit : markSymbolWithShips;
                System.out.print(markSymbol + " ");
            }
            System.out.println();
        }

    }

    public boolean sendShipToTheBattlefield(Ship ship, Position position1, Position position2) {

        ValidatePositionsShip validatePositionsShip = new ValidatePositionsShip(this, ship, position1, position2);

        boolean canShipBeDeployed = validatePositionsShip.readyCheckToBeDeployed();

        boolean isShipDeployed = false;

        if (canShipBeDeployed) {

            Position posMin = validatePositionsShip.setPositionMin(position1, position2);
            Position posMax = posMin == position1 ? position2 : position1;

            Position[] shipPositions = posMin.fillPositions(posMax, this);

            isShipDeployed = ship.setPositions(shipPositions);
            if (isShipDeployed) {
                shipsAtTheBattlefield.add(ship);
            }

        }

        return isShipDeployed;

    }

    public HitCode markPositionForShot(Position position) {
        return position.handleShot();
    }

    public boolean isAllShipsSank() {
        return shipsAtTheBattlefield.size() == 0;
    }

    public boolean removeShipIfSank(Ship ship) {

        boolean isRemoved = false;

        if (ship == null) {

            return false;
        }

        if (ship.getHitCount() == 0) {
            shipsAtTheBattlefield.remove(ship);
            isRemoved = true;
        }

        return isRemoved;

    }

    public int FIELD_SIZE() {
        return FIELD_SIZE;
    }

    public Position[][] getBattlefieldPositions() {
        return battlefieldPositions.clone();
    }

    public String getPlayerName() {
        return playerName;
    }
}
