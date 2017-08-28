package MTG.Cards.Filters;

import MTG.Cards.IMagicCard;
import java.util.List;


public interface ICardFilter {

    List<IMagicCard> query(List<IMagicCard> cards);
}
