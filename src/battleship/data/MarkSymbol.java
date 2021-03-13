package battleship.data;

public enum MarkSymbol {

    EMPTY("~"),
    SHIP("O"),
    HIT("X"),
    MISS("M");

    private final char symbol;

    MarkSymbol(String symbol) {

        this.symbol = symbol.charAt(0);

    }

    @Override
    public String toString() {
        return String.valueOf(symbol);
    }
}
