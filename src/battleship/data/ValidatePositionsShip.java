package battleship.data;

public class ValidatePositionsShip {

    Position position1;
    int x1;
    int y1;

    Position position2;
    int x2;
    int y2;

    Ship ship;

    BattleField battleField;

    public ValidatePositionsShip(BattleField battleField) {
        this.battleField = battleField;
    }

    public ValidatePositionsShip(BattleField battleField, Ship ship, Position position1, Position position2) {
        this.battleField = battleField;
        setShip(ship);
        setPositions(position1, position2);
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public void setPositions(Position position1, Position position2) {
        this.position1 = position1;
        this.position2 = position2;
        this.x1 = position1.getX();
        this.y1 = position1.getY();
        this.x2 = position2.getX();
        this.y2 = position2.getY();

    }

    public boolean readyCheckToBeDeployed() {

        boolean isReady = false;

        Position posMin = setPositionMin(position1, position2);
        Position posMax = posMin == position1 ? position2 : position1;

        try {

            double length = posMin.calcLength(posMax);

            if (length - (int) length == 0 && ship.getShipType().size() != length) {
                throw new ErrorWrongLength();
            } else if (length - (int) length != 0) {
                throw new ErrorWrongLocation();
            } else if (isConflict(posMin, posMax)) {
                throw new ErrorTooClose();
            }

            isReady = true;

        } catch (ErrorWrongLocation e) {
            System.out.println("Error! Wrong ship location! Try again: ");
        } catch (ErrorWrongLength e) {
            System.out.printf("Error! Wrong length of the %s! Try again:%n", ship.getName());
        } catch (ErrorTooClose e) {
            System.out.println("Error! You placed it too close to another one. Try again:");
        }

        return isReady;

    }

    /**
     * This method calculates and returns position which distance is closer to the (0,0) coordinate.
     *
     * @param position1 first position
     * @param position2 second position
     * @return position which distance is closer to the (0,0) coordinate
     * */
    public Position setPositionMin(Position position1, Position position2) {

        double dist1 = Math.sqrt(x1 * x1 + y1 * y1);
        double dist2 = Math.sqrt(x2 * x2 + y2 * y2);

        return dist1 < dist2 ? position1 : position2;

    }

    private boolean isConflict(Position posMin, Position posMax) {

        int fieldSize = battleField.FIELD_SIZE();

        Position[][] positions = battleField.getBattlefieldPositions();

        int x1e = Math.max(posMin.getX() - 1, 0);
        int y1e = Math.max(posMin.getY() - 1, 0);
        int x2e = Math.min(posMax.getX() + 1, fieldSize - 1);
        int y2e = Math.min(posMax.getY() + 1, fieldSize - 1);

        for (int i = y1e; i <= y2e; i++) {
            for (int j = x1e; j <= x2e; j++) {

                Ship ship = positions[i][j].getShip();

                if (ship != null) {
                    return true;
                }

            }

        }

        return false;

    }

}
