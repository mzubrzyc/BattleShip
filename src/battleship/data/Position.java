package battleship.data;

public class Position {

    private int x;

    private int y;

    private Ship ship;

    private MarkSymbol mark;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.ship = null;
        mark = MarkSymbol.EMPTY;

    }

    public double calcLength(Position otherPosition) {

        int otherX = otherPosition.getX();
        int otherY = otherPosition.getY();
        int dx = Math.abs(otherX - x);
        int dy = Math.abs(otherY - y);

        dx = dx == 0 ? dx : dx + 1;
        dy = dy == 0 ? dy : dy + 1;

        return Math.sqrt(dx * dx + dy * dy);

    }

    public Position[] fillPositions(Position posMax, BattleField battleField) {

        if (this == posMax) {
            return new Position[]{this};
        }

        Position[][] battlefieldPositions = battleField.getBattlefieldPositions();

        int otX = posMax.getX();
        int otY = posMax.getY();

        boolean isVertical = x - otX == 0;

        if (isVertical) {

            int fillCount = otY - y - 1;

            int posSize = 2 + fillCount;

            Position[] shipPositions = new Position[posSize];
            shipPositions[0] = this;
            shipPositions[posSize - 1] = posMax;

            for (int i = 0; i < fillCount; i++) {
                shipPositions[i + 1] = battlefieldPositions[y + i + 1][x];
            }

            return shipPositions;

        } else {

            int fillCount = otX - x - 1;

            int posSize = 2 + fillCount;

            Position[] shipPositions = new Position[posSize];
            shipPositions[0] = this;
            shipPositions[posSize - 1] = posMax;

            for (int i = 0; i < fillCount; i++) {
                shipPositions[i + 1] = battlefieldPositions[y][x + i + 1];
            }

            return shipPositions;

        }

    }

    /**
     * handleShot() marks symbol hit or miss for this position
     * @return {@link HitCode HitCode}
     *
     * */
    public HitCode handleShot() {

        if (mark == MarkSymbol.HIT) {
            return HitCode.MISS;
        }

        if (ship == null) {
            mark = MarkSymbol.MISS;
            return HitCode.MISS;
        }

        mark = MarkSymbol.HIT;
        ship.hitCounter();
        return HitCode.HIT;

    }

    public MarkSymbol getMark() {
        return mark;
    }

    public void setMark(MarkSymbol mark) {
        this.mark = mark;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    @Override
    public String toString() {
        return "Position{" +
               "x=" + x +
               ", y=" + y +
               ", ship=" + ship +
               ", mark=" + mark +
               '}';
    }
}
