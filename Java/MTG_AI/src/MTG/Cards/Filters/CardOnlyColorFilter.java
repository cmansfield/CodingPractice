package MTG.Cards.Filters;

import MTG.Cards.CardTypes.Colors;
import MTG.Cards.IMagicCard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;


public class CardOnlyColorFilter implements ICardFilter {

    private final Colors color;


    private boolean isCardColor(Iterator<Entry<Colors,Integer>> iter) {
        boolean isFound = false;

        while(iter.hasNext()) {
            Colors curColor = iter.next().getKey();
            if(curColor != this.color && curColor != Colors.COLORLESS) return false;
            if(curColor == this.color) isFound = true;
        }
        return isFound;
    }

    public CardOnlyColorFilter(Colors color) {
        this.color = color;
    }

    @Override
    public List<IMagicCard> query(List<IMagicCard> cards) {


        List<IMagicCard> filteredList = new ArrayList<>();

        for(IMagicCard card : cards) {
            Iterator<Entry<Colors,Integer>> iter = card.getManaCostIterator();
            if(isCardColor(iter)) filteredList.add(card);
        }

        return filteredList;
    }
}
