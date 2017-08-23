package MTG.Cards.Filters;

import MTG.Cards.CardTypes.Format;
import MTG.Cards.IMagicCard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;


public class CardLegalityFilter implements ICardFilter {

    private Format format;

    public CardLegalityFilter(Format format) {
        this.format = format;
    }

    @Override
    public List<IMagicCard> query(List<IMagicCard> cards) {

        List<IMagicCard> filteredList = new ArrayList<IMagicCard>();

        for(IMagicCard card : cards) {
            Iterator<Entry<Format,Boolean>> iter = card.getLegalities();
            while(iter.hasNext()) {
                if(iter.next().getKey() == this.format) {
                    filteredList.add(card);
                    break;
                }
            }
        }

        return filteredList;
    }
}
