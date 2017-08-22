
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import static java.lang.Math.toIntExact;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


enum SuperTypes { LEGENDARY, BASIC, SNOW, WOLRD, ONGOING }
enum Types { INSTANT, SORCERY, LAND, CREATURE, TRIBAL, ARTIFACT, ENCHANTMENT, VANGUARD, PLANESWALKER, SCHEME, PLANE, CONSPIRACY, PHENOMENON, EATURECRAY, ENCHANT, PLAYER, UNKNOWN_TYPE }
enum Colors { WHITE, BLACK, RED, BLUE, GREEN, COLORLESS }
enum Format { STANDARD, COMMANDER, LEGACY, MODERN, VINTAGE, UNSETS }
enum Layout { NORMAL, DOUBLE_FACED, VANGUARD, SPLIT, TOKEN, SCHEME, LEVELER, MELD, AFTERMATH, FLIP, PLANE, PHENOMENON, UNKNOWN }


public class MagicCard implements IMagicCard {

    private final static String[] SUPER_TYPES = { "Legendary", "Basic", "Snow", "World", "Ongoing" };
    private final static String[] TYPES = { "Instant", "Sorcery", "Land", "Creature", "Tribal", "Artifact", "Enchantment", "Vanguard", "Planeswalker",
            "Scheme", "Plane", "Conspiracy", "Phenomenon", "Eaturecray", "Enchant", "Player", "UnknownType" };
    private final static String[] COLORS = { "White", "Black", "Red", "Blue", "Green", "Colorless" };
    private final static String[] FORMAT = { "Standard", "Commander", "Legacy", "Modern", "Vintage", "Un-Sets" };
    private final static String[] LAYOUT = { "normal", "double-faced", "vanguard", "split", "token", "scheme", "leveler", "meld", "aftermath", "flip",
            "plane", "phenomenon" };

    private static final int VALUE_NOT_FOUND = -1;

    private Layout layout;
    private String name;
    private Map<Colors,Integer> manaCost;
    private double cmc;
    private Vector<Colors> colors;
    private String type;
    private Vector<SuperTypes> superTypes;
    private Vector<Types> types;
    private Vector<String> subTypes;
    private String text;
    private String power;
    private String toughness;
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
        private double _cmc;
        private Vector<Colors> _colors;
        private Vector<SuperTypes> _superTypes;
        private Vector<Types> _types;
        private Vector<String> _subTypes;
        private String _text;
        private String _power;
        private String _toughness;
        private Colors _colorIdentity;

        static final String MANA_PATTERN = "(?:\\{(\\d+|[wrbugWRBUG])\\})";
        static final String POWER_TOUGH_PATTERN = "^(?:(?:(?:[0-9](?:\\.[0-9])?)+)|(?:\\*))$";


        public CardBuilder(Object jsonCard) {

            if(!(jsonCard instanceof JSONObject)) throw new IllegalArgumentException("JSONObject required for card creation");
            JSONObject card = (JSONObject)jsonCard;

            this._manaCost = new HashMap<>();
            this._legalities = new EnumMap<Format, Boolean>(Format.class);
            this._colors = new Vector<>();
            this._superTypes = new Vector<>();
            this._types = new Vector<>();
            this._subTypes = new Vector<>();

            Map<String, Consumer<Object>> requiredConsumers = new HashMap<>();
            requiredConsumers.put("layout", this::layout);
            requiredConsumers.put("name", this::name);
            requiredConsumers.put("type", this::type);

            Map<String, Consumer<Object>> optionalConsumers = new HashMap<>();
            optionalConsumers.put("manaCost", this::manaCost);
            optionalConsumers.put("cmc", this::cmc);
            optionalConsumers.put("colors", this::colors);
            optionalConsumers.put("supertypes", this::superTypes);
            optionalConsumers.put("types", this::types);
            optionalConsumers.put("subtypes", this::subTypes);
            optionalConsumers.put("text", this::text);
            optionalConsumers.put("power", this::power);
            optionalConsumers.put("toughness", this::toughness);
            optionalConsumers.put("colorIdentity", this::colorIdentity);
            optionalConsumers.put("legalities", this::legalities);


            requiredConsumers.forEach((key, value) -> {
                if(!card.containsKey(key))
                    throw new IllegalArgumentException(String.format("Missing required parameter for card %s", this._name));
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

            if(!(power instanceof String))
                throw new IllegalArgumentException(String.format("Invalid argument for card %s's power", this._name));

//            Pattern digitP = Pattern.compile(POWER_TOUGH_PATTERN);
//            Matcher match = digitP.matcher((String)power);
//
//            if(!match.find())
//                throw new IllegalArgumentException(String.format("Invalid argument for card %s's power", this._name));

            this._power = (String)power;

            return this;
        }

        public CardBuilder toughness(final Object toughness) {

            if(!(toughness instanceof String))
                throw new IllegalArgumentException(String.format("Invalid argument for card %s's toughness", this._name));

//            Pattern digitP = Pattern.compile(POWER_TOUGH_PATTERN);
//            Matcher match = digitP.matcher((String)toughness);
//
//            if(!match.find())
//                throw new IllegalArgumentException(String.format("Invalid argument for card %s's toughness", this._name));

            this._toughness = (String)toughness;

            return this;
        }

        public CardBuilder colors(final Object colors) {

            if(!(colors instanceof JSONArray)) throw new IllegalArgumentException("Invalid argument for card colors");

            JSONArray colorsArr = (JSONArray)colors;

            for(Object color : colorsArr) {
                if(!(color instanceof String)) throw new IllegalArgumentException("Invalid argument for card color");

                int colorIndex = Arrays.asList(COLORS).indexOf((String)color);
                if(colorIndex == VALUE_NOT_FOUND) throw new IllegalArgumentException("Invalid argument for card color");

                this._colors.addElement(Colors.values()[colorIndex]);
            }

            return this;
        }

        public CardBuilder superTypes(final Object superTypes) {

            if(!(superTypes instanceof JSONArray)) throw new IllegalArgumentException("Invalid argument for card super type");

            JSONArray typesArr = (JSONArray)superTypes;

            for(Object type : typesArr) {
                if(!(type instanceof String)) throw new IllegalArgumentException("Invalid argument for card super type");

                int typeIndex = Arrays.asList(SUPER_TYPES).indexOf((String)type);
                if(typeIndex == VALUE_NOT_FOUND) throw new IllegalArgumentException("Invalid argument for card super type");

                this._superTypes.addElement(SuperTypes.values()[typeIndex]);
            }

            return this;
        }

        public CardBuilder types(final Object types) {

            if(!(types instanceof JSONArray))
                throw new IllegalArgumentException(String.format("Invalid argument for card %s's type", this._name));

            JSONArray typesArr = (JSONArray)types;

            for(Object type : typesArr) {
                if(!(type instanceof String))
                    throw new IllegalArgumentException(String.format("Invalid argument for card %s's type", this._name));

                int typeIndex = Arrays.asList(TYPES).indexOf((String)type);
                if(typeIndex == VALUE_NOT_FOUND)
                    this._types.addElement(Types.UNKNOWN_TYPE);
                else
                    this._types.addElement(Types.values()[typeIndex]);
            }

            return this;
        }

        public CardBuilder subTypes(final Object subTypes) {

            if(!(subTypes instanceof JSONArray)) throw new IllegalArgumentException("Invalid argument for card sub-type");

            JSONArray types = (JSONArray)subTypes;

            for(Object type : types) {
                if(!(type instanceof String)) throw new IllegalArgumentException("Invalid argument for sub-type");

                this._subTypes.addElement((String)type);
            }

            return this;
        }

        public CardBuilder cmc(final Object cmc) {

            if(cmc instanceof Long)
                this._cmc = toIntExact((long)cmc);
            else if(cmc instanceof Double)
                this._cmc = (double)cmc;
            else
                throw new IllegalArgumentException(String.format("Invalid argument for card %s's cmc", this._name));

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

        public CardBuilder colorIdentity(final Object colorIdent) {

            if(!(colorIdent instanceof JSONArray)) throw new IllegalArgumentException("Invalid argument for card color identity");

            JSONArray colorsArr = (JSONArray)colorIdent;

            for(Object color : colorsArr) {
                if(!(color instanceof String)) throw new IllegalArgumentException("Invalid argument for card identity");

                switch((String)color) {
                    case "W":
                        this._colorIdentity = Colors.WHITE;
                        break;
                    case "R":
                        this._colorIdentity = Colors.RED;
                        break;
                    case "B":
                        this._colorIdentity = Colors.BLACK;
                        break;
                    case "U":
                        this._colorIdentity = Colors.BLUE;
                        break;
                    case "G":
                        this._colorIdentity = Colors.GREEN;
                        break;
                    default:
                    this._colorIdentity = Colors.COLORLESS;
                }
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

    private String stringifyManaCost() {

        String str = "";

        for(Entry<Colors,Integer> pair : this.manaCost.entrySet()) {
            str += String.format("%d-%s ", pair.getValue(), COLORS[pair.getKey().ordinal()]);
        }

        return str;
    }

    public Layout getLayout() {
        return layout;
    }

    public final String getName() {
        return name;
    }

    public Iterator<Entry<Colors,Integer>> getManaCostIterator() {
        return manaCost.entrySet().iterator();
    }

    public double getCmc() {
        return cmc;
    }

    public Iterator<Colors> getColorsIterator() {
        return colors.iterator();
    }

    public final String getType() {
        return type;
    }

    public Iterator<SuperTypes> getSuperTypes() {
        return superTypes.iterator();
    }

    public Iterator<Types> getTypes() {
        return types.iterator();
    }

    public Iterator<String> getSubTypes() {
        return subTypes.iterator();
    }

    public final String getText() {
        return text;
    }

    public final String getPower() {
        return power;
    }

    public final String getToughness() {
        return toughness;
    }

    public Iterator<EnumMap.Entry<Format, Boolean>> getLegalities() {
        return legalities.entrySet().iterator();
    }

    public Colors getColorIdentity() {
        return colorIdentity;
    }

    public String toSimpleString() {

        return String.format(
                "\"%s\" [%s] Layout: %s, CMC: %.1f%s%s ",
                this.name,
                this.colorIdentity,
                LAYOUT[this.layout.ordinal()],
                this.cmc,
                ((this.manaCost.isEmpty()) ? "" : String.format(", ManaCost: %s", stringifyManaCost())),
                ((this.power == null) ? "" : String.format(", [%s/%s]", this.power, this.toughness ))
        );
    }

    @Override
    public String toString() {

        return String.format(
                "\t%s %n%s",
                toSimpleString(),
                this.text
        );
    }
}