package battleship.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class GameInterface {

    private final Scanner scanner = new Scanner(System.in);

    private final PvP pvp;

    private final ShipFactory shipFactory;

    private final ShipTypes[] shipTypes;

    boolean isExit = false;

    public GameInterface(PvP pvp) {
        this.pvp = pvp;
        this.shipFactory = new ShipFactory();
        this.shipTypes = new ShipTypes[]{
                ShipTypes.CARRIER,
                ShipTypes.BATTLESHIP,
                ShipTypes.SUBMARINE,
                ShipTypes.CRUISER,
                ShipTypes.DESTROYER
        };

    }

    public void run() {

        initializeShips(pvp.getBattleFieldPlayer1());
        passTheMove();
        initializeShips(pvp.getBattleFieldPlayer2());

        if (!isExit) {
            startTheGame();
        }

    }

    private void initializeShips(BattleField battleField) {

        System.out.printf("%s, place your ships on the game field%n%n", battleField.getPlayerName());

        int ordinal = 0;

        battleField.printBattleField(BattlefieldType.NORMAL);
        System.out.println();

        do {

            ShipTypes shipType = null;
            String inputMsg = null;

            if (ordinal < shipTypes.length) {
                shipType = shipTypes[ordinal];

                inputMsg = String.format("Enter the coordinates of the %s (%d cells):", shipType.getName(),
                                         shipType.size());
            }

            boolean isPositionHandled = handleInputShipsPositions(scanner, battleField, shipType, inputMsg);
            if (isPositionHandled) {
                battleField.printBattleField(BattlefieldType.NORMAL);
                System.out.println();
                ordinal++;
            }

        } while (!isExit && ordinal != shipTypes.length);

    }

    private void startTheGame() {

        BattleField currentBattleField;
        BattleField enemyBattleField;

        System.out.println("The game starts!\n");

        passTheMove();

        do {

            currentBattleField = pvp.currentBattleField();
            enemyBattleField = pvp.getEnemyBattleField();

            enemyBattleField.printBattleField(BattlefieldType.FOG_OF_WAR);
            System.out.println("---------------------");
            currentBattleField.printBattleField(BattlefieldType.NORMAL);
            System.out.println();

            System.out.printf("%s, it's your turn:%n", currentBattleField.getPlayerName());

            handleInputShot(scanner, enemyBattleField);

            if (!isExit) {
                passTheMove();
            }

        } while (!isExit);

    }

    private void handleInputShot(Scanner scanner, BattleField battleField) {

        HitMessage hitMessage;

        Position shotPosition = getPositionFromTheBattleField(scanner, battleField);

        if (shotPosition == null) {
            return;
        }

        HitCode hitCode = battleField.markPositionForShot(shotPosition);
        boolean isShipSank = battleField.removeShipIfSank(shotPosition.getShip());
        boolean isAllShipsSank = battleField.isAllShipsSank();

        hitMessage = HitMessage.getHitMessage(hitCode, isShipSank, isAllShipsSank);

        battleField.printBattleField(BattlefieldType.FOG_OF_WAR);
        System.out.println();

        System.out.println(hitMessage.get());

        if (isAllShipsSank) {
            isExit = true;
        }

    }

    private Position getPositionFromTheBattleField(Scanner scanner, BattleField battleField) {

        boolean isShotValid;

        Position shotPosition;

        do {

            String inputShotCoords = scanner.nextLine();

            System.out.println();
            isExit = "exit".equalsIgnoreCase(inputShotCoords);

            if (isExit) {
                return null;
            }

            Integer[] shotCoords = convertInputShotCoords(inputShotCoords);

            shotPosition = getBattlefieldPositionFromInput(shotCoords, battleField);

            isShotValid = shotPosition != null;

        } while (!isShotValid);

        return shotPosition;

    }

    private boolean handleInputShipsPositions(Scanner scanner, BattleField battleField, ShipTypes shipType,
                                              String inputMsg) {

        boolean isShipSentToTheBattlefield = false;

        System.out.println(inputMsg);

        while (!isExit && !isShipSentToTheBattlefield) {

            String input = scanner.nextLine();
            System.out.println();

            isExit = "exit".equalsIgnoreCase(input);

            if (isExit) {
                return false;
            }

            List<Integer[]> inputPositions = convertInputShipCoordsMinMax(input);

            Position position1 = getBattlefieldPositionFromInput(inputPositions.get(0), battleField);
            Position position2 = getBattlefieldPositionFromInput(inputPositions.get(1), battleField);

            Ship ship = shipFactory.createShip(shipType);

            isShipSentToTheBattlefield = battleField.sendShipToTheBattlefield(ship, position1, position2);

        }

        return true;

    }

    private Integer[] convertInputShotCoords(String input) {

        Integer[] shotCoords = new Integer[2];

        shotCoords[0] = Integer.parseInt(input.substring(1)) - 1;
        shotCoords[1] = (int) input.charAt(0) - 65;

        return shotCoords;

    }

    /**
     * This method converts input (e.g. F3 F7) into proper X Y coordinates to use as positions at the battlefield array
     *
     * @param input coordinates from the input, min max e.g. F3 F7
     * @return List wrapping two Integer arrays of size 2 each
     * */
    private List<Integer[]> convertInputShipCoordsMinMax(String input) {

        return Arrays.stream(input.split(" "))
                     .map(e -> {
                         Integer[] tempInt = new Integer[2];
                         tempInt[0] = Integer.parseInt(e.substring(1)) - 1;
                         tempInt[1] = (int) e.charAt(0) - 65;

                         return tempInt;
                     })
                     .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

    }

    private Position getBattlefieldPositionFromInput(Integer[] input, BattleField battleField) {

        ValidateShot validateShot = new ValidateShot(battleField);

        int x = input[0];
        int y = input[1];

        boolean coordsValid = validateShot.areCoordsValid(x, y);

        return coordsValid ? battleField.getBattlefieldPositions()[y][x] : null;

    }

    private boolean passTheMove() {

        System.out.println("Press Enter and pass the move to another player");

        String input = scanner.nextLine();

        return input.isEmpty() || passTheMove();
    }

}
