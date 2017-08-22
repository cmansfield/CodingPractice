package MTG.Cards.Filters;

import MTG.Cards.IMagicCard;
import MTG.Cards.CardTypes.*;

import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CardColorFilter implements ICardFilter {

    private Colors color;

    public CardColorFilter(Colors color) {
        this.color = color;
    }

    public List<IMagicCard> query(List<IMagicCard> cards) {

        List<IMagicCard> filteredList = new ArrayList<IMagicCard>();

        for(IMagicCard card : cards) {
            Iterator<Entry<Colors,Integer>> iter = card.getManaCostIterator();
            while(iter.hasNext()) {
                if(iter.next().getKey() == this.color) {
                    filteredList.add(card);
                    break;
                }
            }
        }

        return filteredList;
    }
}
