package MTG.Cards.CardTypes;


public enum Formats implements ICardType<Formats> {
    STANDARD("Standard"),
    COMMANDER("Commander"),
    LEGACY("Legacy"),
    MODERN("Modern"),
    VINTAGE("Vintage"),
    UNSETS("Un-Sets"),
    UNKNOWN("Unknown");
    
    private final String value;


    Formats(String value) {
        this.value = value;
    }

    public static boolean contains(final String val) {

        return ICardType.contains(Formats.class, val);
    }

    public static Formats find(final String val) {

        Formats format = ICardType.find(Formats.class, val);
        return format == null ? Formats.UNKNOWN : format;
    }

    public String stringify() {
        return this.value;
    }
}