package MTG.Cards.CardTypes;


public enum Types {
    INSTANT("Instant"),
    SORCERY("Sorcery"),
    LAND("Land"),
    CREATURE("Creature"),
    TRIBAL("Tribal"),
    ARTIFACT("Artifact"),
    ENCHANTMENT("Enchantment"),
    VANGUARD("Vanguard"),
    PLANESWALKER("Planeswalker"),
    SCHEME("Scheme"),
    PLANE("Plane"),
    CONSPIRACY("Conspiracy"),
    PHENOMENON("Phenomenon"),
    EATURECRAY("Eaturecray"),
    ENCHANT("Enchant"),
    PLAYER("Player"),
    UNKNOWN("Unknown");

    private final String value;


    Types(String value) {
        this.value = value;
    }

    public static boolean contains(final String val) {

        return ICardType.contains(Types.class, val);
    }

    public static Types find(final String val) {

        Types type = ICardType.find(Types.class, val);
        return type == null ? Types.UNKNOWN : type;
    }

    public String stringify() {
        return this.value;
    }
}