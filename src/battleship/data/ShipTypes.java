package battleship.data;

import java.util.ArrayList;
import java.util.List;

public enum ShipTypes {

    CARRIER("Aircraft Carrier", 5, 0),
    BATTLESHIP("Battleship", 4, 1),
    SUBMARINE("Submarine", 3, 2),
    CRUISER("Cruiser", 3, 3),
    DESTROYER("Destroyer", 2, 4);

    private final String name;

    private final int shipSize;

    private final int index;

    private final static List<String> messages = new ArrayList<>();

    static {
        messages.add("Enter the coordinates of the Aircraft Carrier (5 cells):");
        messages.add("Enter the coordinates of the Battleship (4 cells):");
        messages.add("Enter the coordinates of the Submarine (3 cells):");
        messages.add("Enter the coordinates of the Cruiser (3 cells):");
        messages.add("Enter the coordinates of the Destroyer (2 cells):");
    }

    ShipTypes(String name, int shipSize, int index) {
        this.name = name;
        this.shipSize = shipSize;
        this.index = index;

    }

    public String getName() {

        return this.name;

    }

    public int size() {

        return this.shipSize;

    }

    public static String getMessage(ShipTypes shipType) {

        return messages.get(shipType.index);

    }

}
