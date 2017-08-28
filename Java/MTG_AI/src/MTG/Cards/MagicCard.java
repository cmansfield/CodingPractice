package MTG.Cards;

import MTG.*;
import MTG.Cards.CardTypes.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import static java.lang.Math.toIntExact;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class MagicCard implements IMagicCard {

    private static final int VALUE_NOT_FOUND = -1;

    private Layouts layout;
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
    private EnumMap<Formats,Boolean> legalities;
    private Colors colorIdentity;


    public static class CardBuilder implements IBuilder {
        // required
        private Layouts _layout;
        private String _name;
        private String _type;
        private EnumMap<Formats,Boolean> _legalities;

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
            this._legalities = new EnumMap<>(Formats.class);
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

            if(!(layout instanceof String))
                throw new IllegalArgumentException("Invalid argument for card layout");

            Layouts foundLayout = Layouts.find((String)layout);

            if(foundLayout == Layouts.UNKNOWN)
                throw new IllegalArgumentException("Invalid argument for card layout");

            this._layout = foundLayout;
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

            String strLegal;

            for(Object elem : (JSONArray)legal) {

                strLegal = (String)((JSONObject)elem).get("legality");

                if(!strLegal.equalsIgnoreCase("Legal")) continue;

                this._legalities.put(
                        Formats.find((String)((JSONObject)elem).get("format")),
                        true);
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

            if(!(colors instanceof JSONArray))
                throw new IllegalArgumentException("Invalid argument for card colors");

            JSONArray colorsArr = (JSONArray)colors;

            for(Object color : colorsArr) {
                if(!(color instanceof String))
                    throw new IllegalArgumentException("Invalid argument for card color");

                Colors foundColor = Colors.find((String)color);
                if(foundColor == Colors.UNKNOWN)
                    throw new IllegalArgumentException("Invalid argument for card color");

                this._colors.addElement(foundColor);
            }

            return this;
        }

        public CardBuilder superTypes(final Object superTypes) {

            if(!(superTypes instanceof JSONArray))
                throw new IllegalArgumentException("Invalid argument for card super type");

            JSONArray typesArr = (JSONArray)superTypes;

            for(Object type : typesArr) {
                if(!(type instanceof String))
                    throw new IllegalArgumentException("Invalid argument for card super type");

                SuperTypes foundType = SuperTypes.find((String)type);
                if(foundType == SuperTypes.UNKNOWN)
                    throw new IllegalArgumentException("Invalid argument for card super type");

                this._superTypes.addElement(foundType);
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

                this._types.addElement(Types.find((String)type));
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

//            Pattern manaP = Pattern.compile(MANA_PATTERN);
//            String remaining = (String)mana;
//            Matcher match = manaP.matcher(remaining);
//
//            while(match.find()) {
//                remaining = remaining.substring(match.end());
//                Colors selectedColor = Colors.COLORLESS;
//
//                switch (match.group(1).toUpperCase()) {
//                    case "W":
//                        selectedColor = Colors.WHITE;
//                        break;
//                    case "R":
//                        selectedColor = Colors.RED;
//                        break;
//                    case "B":
//                        selectedColor = Colors.BLACK;
//                        break;
//                    case "U":
//                        selectedColor = Colors.BLUE;
//                        break;
//                    case "G":
//                        selectedColor = Colors.GREEN;
//                        break;
//                    default:
//                        selectedColor = Colors.COLORLESS;
//
//                        try {
//                            int numMana = Integer.parseInt(match.group(1));
//                            this._manaCost.put(Colors.COLORLESS, numMana);
//                        } catch (NumberFormatException e) {
//                            throw new IllegalArgumentException("Invalid card mana cost argument");
//                        }
//                }
//
//                if(selectedColor != Colors.COLORLESS) {
//                    if(this._manaCost.containsKey(COLORS[selectedColor.ordinal()]))
//                        this._manaCost.put(selectedColor, this._manaCost.get(COLORS[selectedColor.ordinal()]));
//                    else
//                        this._manaCost.put(selectedColor, 1);
//                }
//
//                match = manaP.matcher(remaining);
//            }

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
            str += String.format("%d-%s ", pair.getValue(), pair.getKey().stringify());
        }

        return str;
    }

    public Layouts getLayout() {
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

    public Iterator<EnumMap.Entry<Formats, Boolean>> getLegalities() {
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
                this.layout.stringify(),
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