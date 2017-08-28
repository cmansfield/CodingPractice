package MTG.Cards.CardTypes;


public enum Layouts implements ICardType<Layouts> {
    NORMAL("normal"),
    DOUBLE_FACED("double-faced"),
    VANGUARD("vanguard"),
    SPLIT("split"),
    TOKEN("token"),
    SCHEME("scheme"),
    LEVELER("leveler"),
    MELD("meld"),
    AFTERMATH("aftermath"),
    FLIP("flip"),
    PLANE("plane"),
    PHENOMENON("phenomenon"),
    UNKNOWN("unknown");

    private final String value;


    Layouts(String value) {
        this.value = value;
    }

    public static boolean contains(final String val) {

        return ICardType.contains(Layouts.class, val);
    }

    public static Layouts find(final String val) {

        Layouts layout = ICardType.find(Layouts.class, val);
        return layout == null ? Layouts.UNKNOWN : layout;
    }

    public String stringify() {
        return this.value;
    }
}