package MTG.Cards.Filters;

import MTG.Cards.IMagicCard;

import java.util.ArrayList;
import java.util.List;


public class CardGreatestCmcFilter implements ICardFilter {

    public CardGreatestCmcFilter() {}

    @Override
    public List<IMagicCard> query(List<IMagicCard> cards) {

        List<IMagicCard> filteredList = new ArrayList<IMagicCard>();
        double max = -1;


        for(IMagicCard card : cards) {
            double cmc = card.getCmc();
            if(cmc == max) filteredList.add(card);
            else if(cmc > max) {
                filteredList.clear();
                filteredList.add(card);
                max = cmc;
            }
        }

        return filteredList;
    }
}
