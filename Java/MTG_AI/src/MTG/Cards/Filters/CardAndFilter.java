package MTG.Cards.Filters;


import MTG.Cards.IMagicCard;

import java.util.List;

public class CardAndFilter implements ICardFilter {

    private ICardFilter filter;
    private ICardFilter filter2;


    public CardAndFilter(ICardFilter filter, ICardFilter filter2) {
        this.filter = filter;
        this.filter2 = filter2;
    }

    @Override
    public List<IMagicCard> query(List<IMagicCard> cards) {
        List<IMagicCard> filteredList = filter.query(cards);
        return filter2.query(filteredList);
    }
}
