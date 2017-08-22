package MTG;

import MTG.Cards.Filters.CardColorFilter;
import MTG.Cards.Filters.ICardFilter;
import MTG.Cards.IMagicCard;
import MTG.Cards.MagicCard;
import MTG.Cards.CardTypes.*;

import io.JSON_IO;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.Map.Entry;


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

        for(Colors color : Colors.values()) {

            ICardFilter filterTest = new CardColorFilter(color);
            System.out.print(color + " ");
            List<IMagicCard> filteredCards = filterTest.query(cards);
            System.out.println(filteredCards.size());
            if(!filteredCards.isEmpty())
                System.out.println("\t" + filteredCards.get(0).toSimpleString());
        }

    }
}


