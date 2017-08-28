package MTG.Cards.CardTypes;

public enum Colors implements ICardType<Layouts> {
    WHITE("White"),
    BLACK("Black"),
    RED("Red"),
    BLUE("Blue"),
    GREEN("Green"),
    COLORLESS("Colorless"),
    UNKNOWN("Unknown");

    private final String value;


    Colors(String value) {
        this.value = value;
    }

    private static Colors shortHand(final String val) {

        switch (val.toUpperCase()) {
            case "W":
                return Colors.WHITE;
            case "R":
                return Colors.RED;
            case "B":
                return Colors.BLACK;
            case "U":
                return Colors.BLUE;
            case "G":
                return Colors.GREEN;
            default:
                try {
                    Integer.parseInt(val);
                } catch (NumberFormatException e) {
                    return Colors.UNKNOWN;
                }
        }

        return Colors.COLORLESS;
    }

    public static boolean contains(final String val) {

        return ICardType.contains(Colors.class, val)
                || Colors.shortHand(val) != Colors.UNKNOWN;
    }

    public static Colors find(final String val) {

        Colors color = ICardType.find(Colors.class, val);
        if(color == null) color = Colors.shortHand(val);

        return color;
    }

    public String stringify() {
        return this.value;
    }
}