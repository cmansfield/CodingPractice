package MTG.Cards.Filters;

import MTG.Cards.IMagicCard;

import java.util.ArrayList;
import java.util.List;


public class CardCmcFilter implements ICardFilter {

    private double cmc;

    public CardCmcFilter(double cmc) {
        this.cmc = cmc;
    }

    @Override
    public List<IMagicCard> query(List<IMagicCard> cards) {

        List<IMagicCard> filteredList = new ArrayList<IMagicCard>();

        for(IMagicCard card : cards) {
            if(card.getCmc() == this.cmc) filteredList.add(card);
        }

        return filteredList;
    }
}
