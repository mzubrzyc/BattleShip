package battleship.data;

public enum HitMessage {

    HIT("You hit a ship!\n"),

    MiSS("You missed!\n"),

    SANK("You sank a ship!\n"),

    LAST_SHIP_SANK("You sank the last ship. You won. Congratulations!\n"),

    ERROR("Some error!\n");

    String message;

    HitMessage(String message) {
        this.message = message;
    }

    public static HitMessage getHitMessage(HitCode hitCode, boolean isShipSank, boolean isAllShipsSank) {

        if (isAllShipsSank) {
            return LAST_SHIP_SANK;
        } else if (isShipSank) {
            return SANK;
        } else if (hitCode == HitCode.HIT) {
            return HIT;
        } else {
            return MiSS;
        }

    }

    public String get() {
        return this.message;
    }

}
