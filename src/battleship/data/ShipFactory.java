package battleship.data;

public class ShipFactory {

    public ShipFactory() {
    }

    public Ship createShip(ShipTypes shipType) {

        if (shipType == null) {
            return null;
        }

        switch (shipType) {
            case CARRIER:
                return new ShipCarrier();
            case BATTLESHIP:
                return new ShipBattleship();
            case CRUISER:
                return new ShipCruiser();
            case SUBMARINE:
                return new ShipSubmarine();
            case DESTROYER:
                return new ShipDestroyer();
            default:
                return null;
        }

    }

}
