package MTG.Cards.Filters;

import MTG.Cards.CardTypes.Formats;
import MTG.Cards.IMagicCard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;


public class CardLegalityFilter implements ICardFilter {

    private final Formats formats;

    public CardLegalityFilter(Formats formats) {
        this.formats = formats;
    }

    @Override
    public List<IMagicCard> query(List<IMagicCard> cards) {

        List<IMagicCard> filteredList = new ArrayList<>();

        for(IMagicCard card : cards) {
            Iterator<Entry<Formats,Boolean>> iter = card.getLegalities();
            while(iter.hasNext()) {
                if(iter.next().getKey() == this.formats) {
                    filteredList.add(card);
                    break;
                }
            }
        }

        return filteredList;
    }
}
