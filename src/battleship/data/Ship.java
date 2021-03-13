package battleship.data;

import java.util.Arrays;

public class Ship {

    private String name;

    private ShipTypes shipType;

    private BattleField battleField;

    private Position[] positions;

    private int hitCount;

    public Ship(String name, ShipTypes shipType) {
        this.name = name;
        this.shipType = shipType;
        this.hitCount = shipType.size();
    }

    public ShipTypes getShipType() {
        return shipType;
    }

    public void setShipType(ShipTypes shipType) {
        this.shipType = shipType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Position[] getPositions() {
        return positions;
    }

    public boolean setPositions(Position[] positions) {

        if (positions.length != shipType.size()) {

            this.positions = null;
            return false;

        }

        this.positions = positions;

        for (Position position : positions) {

            position.setShip(this);
            position.setMark(MarkSymbol.SHIP);

        }

        return true;

    }

    public void hitCounter() {
        if (this.hitCount > 0) {
            this.hitCount--;
        }
    }

    public int getHitCount() {
        return hitCount;
    }

    @Override
    public String toString() {
        return "Ship{" +
               "name='" + name + '\'' +
               ", shipType=" + shipType +
               ", battleField=" + battleField +
               ", positions=" + Arrays.toString(positions) +
               '}';
    }
}
