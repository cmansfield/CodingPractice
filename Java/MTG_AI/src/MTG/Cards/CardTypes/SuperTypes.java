package MTG.Cards.CardTypes;


public enum SuperTypes {
    LEGENDARY("Legendary"),
    BASIC("Basic"),
    SNOW("Snow"),
    WOLRD("World"),
    ONGOING("Ongoing"),
    UNKNOWN("Unknown");

    private final String value;


    SuperTypes(String value) {
        this.value = value;
    }

    public static boolean contains(final String val) {

        return ICardType.contains(SuperTypes.class, val);
    }

    public static SuperTypes find(final String val) {

        SuperTypes type = ICardType.find(SuperTypes.class, val);
        return type == null ? SuperTypes.UNKNOWN : type;
    }

    public String stringify() {
        return this.value;
    }
}