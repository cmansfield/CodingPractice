package MTG.Cards;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map.Entry;
import MTG.Cards.CardTypes.*;


public interface IMagicCard {
    Layouts getLayout();
    String getName();
    Iterator<Entry<Colors,Integer>> getManaCostIterator();
    double getCmc();
    Iterator<Colors> getColorsIterator();
    String getType();
    Iterator<SuperTypes> getSuperTypes();
    Iterator<Types> getTypes();
    Iterator<String> getSubTypes();
    String getText();
    String getPower();
    String getToughness();
    Iterator<EnumMap.Entry<Formats, Boolean>> getLegalities();
    Colors getColorIdentity();
    String toSimpleString();
    String toString();
}
