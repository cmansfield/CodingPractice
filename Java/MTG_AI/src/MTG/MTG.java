package MTG;

import MTG.Cards.Filters.*;
import MTG.Cards.IMagicCard;
import MTG.Cards.MagicCard;
import MTG.Cards.CardTypes.*;

import io.JSON_IO;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Vector;


public class MTG {

    private Vector<IMagicCard> cards;

    public MTG(final String strFile) throws FileNotFoundException {

        File file = new File(strFile);
        if(!file.exists() || file.isDirectory()) throw new FileNotFoundException(strFile);

        this.cards = new Vector<>(1000);
        JSONObject jsonObj = (JSONObject) JSON_IO.loadJSON(file);

        jsonObj.keySet().iterator();

        for(Object key : jsonObj.keySet()) {
            this.cards.addElement(new MagicCard.CardBuilder(jsonObj.get(key)).build());
        }

        System.out.println(cards.size());

        ICardFilter greatestPowerFilter = new CardGreatestPowerOrToughnessFilter("toughness");

        for(Colors color : Colors.values()) {
            ICardFilter onlyColorFilter = new CardOnlyColorFilter(color);
            ICardFilter andFilter = new CardAndFilter(onlyColorFilter, greatestPowerFilter);
            System.out.print(color + " ");
            List<IMagicCard> filteredCards = andFilter.query(cards);
            System.out.println(filteredCards.size());
//            if(!filteredCards.isEmpty())
//                System.out.println("\t" + filteredCards.get(0).toSimpleString());
            for(IMagicCard card : filteredCards) {
                System.out.println(card.toSimpleString());
            }
        }

    }
}


