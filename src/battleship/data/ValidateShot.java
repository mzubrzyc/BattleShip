package battleship.data;

public class ValidateShot {

    private BattleField battleField;

    public ValidateShot(BattleField battleField) {
        this.battleField = battleField;
    }

    public boolean areCoordsValid(int x, int y) {

        int fieldSize = battleField.FIELD_SIZE();

        boolean isShotValid = false;

        try {
            if (x < 0 || y < 0 || x >= fieldSize || y >= fieldSize) {
                throw new ErrorEnteredWrongCoordinates();
            }

            isShotValid = true;

        } catch (ErrorEnteredWrongCoordinates errorEnteredWrongCoordinates) {
            System.out.println("Error! You entered the wrong coordinates! Try again:\n");
        }

        return isShotValid;

    }

    public BattleField getBattleField() {
        return battleField;
    }

    public void setBattleField(BattleField battleField) {
        this.battleField = battleField;
    }
}
