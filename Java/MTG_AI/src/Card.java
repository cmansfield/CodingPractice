
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.util.List;
import java.util.Vector;
import java.util.Map.Entry;


public class Card {
    private enum SuperTypes { LEGENDARY, BASIC, SNOW, WOLRD, ONGOING }
    private enum Types { INSTANT, SORCERY, LAND, CREATURE, TRIBAL, ARTIFACT, ENCHANTMENT, VANGUARD, PLANESWALKER, SCHEME, PLANE, CONSPIRACY, PHENOMENON, EATURECRAY, ENCHANT, PLAYER }
    private enum Colors { WHITE, BLACK, RED, BLUE, GREEN, COLORLESS }
    private enum Legal { LEGAL, ILLEGAL }
    private enum Format { STANDARD, COMMANDER, LEGACY, MODERN, VINTAGE, UNSETS }
    private enum Layout { NORMAL, FLIP, SPLIT, TOKEN, DOUBLE_FACED }

    private final String[] SUPER_TYPES = { "Legendary", "Basic", "Snow", "World", "Ongoing" };
    private final String[] TYPES = { "Instant", "Sorcery", "Land", "Creature", "Tribal", "Artifact", "Enchantment", "Vanguard", "Planeswalker",
            "Scheme", "Plane", "Conspiracy", "Phenomenon", "Eaturecray", "Enchant", "Player" };
    private final String[] COLORS = { "White", "Black", "Red", "Blue", "Green", "Colorless" };
    private final String[] FORMAT = { "Standard", "Commander", "Legacy", "Modern", "Vintage", "Un-Sets" };

    private Layout layout;
    private String name;
    private Vector<Entry<Colors,Integer>> manaCost;
    private int cmc;
    private Vector<Colors> colors;
    private String type;
    private Vector<String> superTypes;
    private Vector<String> types;
    private Vector<String> subTypes;
    private String text;
    private int power;
    private int toughness;
    private List<Format> legalities;
    private Colors colorIdentity;

    public static class CardBuilder {
        // required
        private Layout layout;
        private String name;
        private String type;
        private String text;
        private List<Entry<Format,Boolean>> legalities;
        private Colors colorIdentity;

        // optional
        private Vector<Entry<Colors,Integer>> manaCost;
        private int cmc;
        private Vector<Colors> colors;
        private Vector<String> superTypes;
        private Vector<String> types;
        private Vector<String> subTypes;
        private int power;
        private int toughness;


        public CardBuilder(JSONObject jsonCard) {


        }
    }

    private Card(CardBuilder builder) {


    }
}