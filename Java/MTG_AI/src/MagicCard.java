
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import static java.lang.Math.toIntExact;

import java.awt.*;
import java.text.Normalizer;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


enum SuperTypes { LEGENDARY, BASIC, SNOW, WOLRD, ONGOING }
enum Types { INSTANT, SORCERY, LAND, CREATURE, TRIBAL, ARTIFACT, ENCHANTMENT, VANGUARD, PLANESWALKER, SCHEME, PLANE, CONSPIRACY, PHENOMENON, EATURECRAY, ENCHANT, PLAYER }
enum Colors { WHITE, BLACK, RED, BLUE, GREEN, COLORLESS }
enum Format { STANDARD, COMMANDER, LEGACY, MODERN, VINTAGE, UNSETS }
enum Layout { NORMAL, DOUBLE_FACED, VANGUARD, SPLIT, TOKEN, SCHEME, LEVELER, MELD, AFTERMATH, FLIP, PLANE, PHENOMENON, UNKNOWN }


public class MagicCard implements IMagicCard {

    private final static String[] SUPER_TYPES = { "Legendary", "Basic", "Snow", "World", "Ongoing" };
    private final static String[] TYPES = { "Instant", "Sorcery", "Land", "Creature", "Tribal", "Artifact", "Enchantment", "Vanguard", "Planeswalker",
            "Scheme", "Plane", "Conspiracy", "Phenomenon", "Eaturecray", "Enchant", "Player" };
    private final static String[] COLORS = { "White", "Black", "Red", "Blue", "Green", "Colorless" };
    private final static String[] FORMAT = { "Standard", "Commander", "Legacy", "Modern", "Vintage", "Un-Sets" };
    private final static String[] LAYOUT = { "normal", "double-faced", "vanguard", "split", "token", "scheme", "leveler", "meld", "aftermath", "flip",
            "plane", "phenomenon" };

    private static final int VALUE_NOT_FOUND = -1;

    private Layout layout;
    private String name;
    private Map<Colors,Integer> manaCost;
    private int cmc;
    private Vector<Colors> colors;
    private String type;
    private Vector<SuperTypes> superTypes;
    private Vector<Types> types;
    private Vector<String> subTypes;
    private String text;
    private int power;
    private int toughness;
    private EnumMap<Format,Boolean> legalities;
    private Colors colorIdentity;


    public static class CardBuilder implements IBuilder {
        // required
        private Layout _layout;
        private String _name;
        private String _type;
        private EnumMap<Format,Boolean> _legalities;

        // optional
        private Map<Colors,Integer> _manaCost;
        private int _cmc;
        private Vector<Colors> _colors;
        private Vector<SuperTypes> _superTypes;
        private Vector<Types> _types;
        private Vector<String> _subTypes;
        private String _text;
        private int _power;
        private int _toughness;
        private Colors _colorIdentity;

        static final String MANA_PATTERN = "(?:\\{(\\d+|[wrbugWRBUG])\\})";
        static final String IS_DIGIT_PATTERN = "^(?:\\d+)$";


        public CardBuilder(Object jsonCard) {

            if(!(jsonCard instanceof JSONObject)) throw new IllegalArgumentException("JSONObject required for card creation");
            JSONObject card = (JSONObject)jsonCard;

            this._manaCost = new HashMap<>();
            this._legalities = new EnumMap<Format, Boolean>(Format.class);

            Map<String, Consumer<Object>> requiredConsumers = new HashMap<>();
            requiredConsumers.put("layout", this::layout);
            requiredConsumers.put("name", this::name);
            requiredConsumers.put("type", this::type);
            requiredConsumers.put("legalities", this::legalities);

            Map<String, Consumer<Object>> optionalConsumers = new HashMap<>();
            optionalConsumers.put("manaCost", this::manaCost);
            optionalConsumers.put("cmc", this::cmc);
//            optionalConsumers.put("colors", this::colors);
//            optionalConsumers.put("supertypes", this::superTypes);
//            optionalConsumers.put("types", this::types);
//            optionalConsumers.put("subtypes", this::subTypes);
            optionalConsumers.put("text", this::text);
            optionalConsumers.put("power", this::power);
            optionalConsumers.put("toughness", this::toughness);
//            optionalConsumers.put("colorIdentity", this::colorIdentity);


            requiredConsumers.forEach((key, value) -> {
                if(!card.containsKey(key)) throw new IllegalArgumentException("Missing required card parameter");
                value.accept(card.get(key));
            });

            optionalConsumers.forEach((key, value) -> {
                if(!card.containsKey(key)) return;
                value.accept(card.get(key));
            });
        }

        public CardBuilder layout(final Object layout) {

            if(!(layout instanceof String)) throw new IllegalArgumentException("Invalid argument for card layout");
            int index = Arrays.asList(LAYOUT).indexOf(layout);

            if(index == VALUE_NOT_FOUND) {
                this._layout = Layout.UNKNOWN;
                return this;
            }

            this._layout = Layout.values()[index];
            return this;
        }

        public CardBuilder name(final Object name) {

            if(!(name instanceof String)) throw new IllegalArgumentException("Invalid argument for card name");
            if(name.equals("")) throw new IllegalArgumentException("Invalid name given");

            this._name = (String)name;
            return this;
        }

        public CardBuilder type(final Object type) {

            if(!(type instanceof String)) throw new IllegalArgumentException("Invalid argument for card type");

            this._type = (String)type;
            return this;
        }

        public CardBuilder legalities(final Object legal) {

            if(!(legal instanceof JSONArray)) throw new IllegalArgumentException("Invalid argument for card legalities");

            String strFormat = "";
            int index = 0;

            for(Object elem : (JSONArray)legal) {

                strFormat = (String)((JSONObject)elem).get("format");
                index = Arrays.asList(FORMAT).indexOf(strFormat);

                if(index == VALUE_NOT_FOUND) continue;

                this._legalities.put(Format.values()[index], true);
            }

            return this;
        }

        public CardBuilder text(final Object text) {

            if(!(text instanceof String)) throw new IllegalArgumentException("Invalid argument for card text");

            this._text = (String)text;
            return this;
        }

        public CardBuilder power(final Object power) {

            if(!(power instanceof String)) throw new IllegalArgumentException("Invalid argument for card power");

            Pattern digitP = Pattern.compile(IS_DIGIT_PATTERN);
            Matcher match = digitP.matcher((String)power);

            if(!match.find()) throw new IllegalArgumentException("Invalid argument for card power");

            this._power = Integer.parseInt((String)power);

            return this;
        }

        public CardBuilder toughness(final Object toughness) {

            if(!(toughness instanceof String)) throw new IllegalArgumentException("Invalid argument for card toughness");

            Pattern digitP = Pattern.compile(IS_DIGIT_PATTERN);
            Matcher match = digitP.matcher((String)toughness);

            if(!match.find()) throw new IllegalArgumentException("Invalid argument for card toughness");

            this._toughness = Integer.parseInt((String)toughness);

            return this;
        }

        public CardBuilder cmc(final Object cmc) {

            if(!(cmc instanceof Long)) throw new IllegalArgumentException("Invalid argument for card cmc");

            this._cmc = toIntExact((long)cmc);

            return this;
        }

        public CardBuilder manaCost(final Object mana) {

            if(!(mana instanceof String)) throw new IllegalArgumentException("Invalid argument for card mana cost");

            Pattern manaP = Pattern.compile(MANA_PATTERN);
            String remaining = (String)mana;
            Matcher match = manaP.matcher(remaining);

            while(match.find()) {
                remaining = remaining.substring(match.end());
                Colors selectedColor = Colors.COLORLESS;

                switch (match.group(1).toUpperCase()) {
                    case "W":
                        selectedColor = Colors.WHITE;
                        break;
                    case "R":
                        selectedColor = Colors.RED;
                        break;
                    case "B":
                        selectedColor = Colors.BLACK;
                        break;
                    case "U":
                        selectedColor = Colors.BLUE;
                        break;
                    case "G":
                        selectedColor = Colors.GREEN;
                        break;
                    default:
                        selectedColor = Colors.COLORLESS;

                        try {
                            int numMana = Integer.parseInt(match.group(1));
                            this._manaCost.put(Colors.COLORLESS, numMana);
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Invalid card mana cost argument");
                        }
                }

                if(selectedColor != Colors.COLORLESS) {
                    if(this._manaCost.containsKey(COLORS[selectedColor.ordinal()]))
                        this._manaCost.put(selectedColor, this._manaCost.get(COLORS[selectedColor.ordinal()]));
                    else
                        this._manaCost.put(selectedColor, 1);
                }

                match = manaP.matcher(remaining);
            }

            return this;
        }

        public MagicCard build() {
            return new MagicCard(this);
        }
    }

    private MagicCard(CardBuilder builder) {

        this.layout = builder._layout;
        this.name = builder._name;
        this.manaCost = builder._manaCost;
        this.cmc = builder._cmc;
        this.colors = builder._colors;
        this.type = builder._type;
        this.superTypes = builder._superTypes;
        this.types = builder._types;
        this.subTypes = builder._subTypes;
        this.text = builder._text;
        this.power = builder._power;
        this.toughness = builder._toughness;
        this.legalities = builder._legalities;
        this.colorIdentity = builder._colorIdentity;
    }

    @Override
    public String toString() {

        return String.format(
                "\t\"%s\" Layout: %s, CMC: %d, %s, [%d/%d], %s %n%s",
                this.name,
                LAYOUT[this.layout.ordinal()],
                this.cmc,
                String.format("ManaCost: %s", stringifyManaCost()),
                this.power,
                this.toughness,
                this.legalities.toString(),
                this.text
        );
    }

    private String stringifyManaCost() {

        String str = "";

        for(Entry<Colors,Integer> pair : this.manaCost.entrySet()) {
            str += String.format("%d-%s ", pair.getValue(), COLORS[pair.getKey().ordinal()]);
        }

        return str;
    }
}