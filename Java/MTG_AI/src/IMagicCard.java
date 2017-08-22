import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map.Entry;


interface IMagicCard {
    public Layout getLayout();
    public String getName();
    public Iterator<Entry<Colors,Integer>> getManaCostIterator();
    public double getCmc();
    public Iterator<Colors> getColorsIterator();
    public String getType();
    public Iterator<SuperTypes> getSuperTypes();
    public Iterator<Types> getTypes();
    public Iterator<String> getSubTypes();
    public String getText();
    public String getPower();
    public String getToughness();
    public Iterator<EnumMap.Entry<Format, Boolean>> getLegalities();
    public Colors getColorIdentity();
    public String toSimpleString();
    public String toString();
}
