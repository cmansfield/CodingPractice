
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Vector;
import java.util.Map.Entry;


public class MTG {

    private Vector<IMagicCard> cards;

    public MTG(final String strFile) throws FileNotFoundException {

        File file = new File(strFile);
        if(!file.exists() || file.isDirectory()) throw new FileNotFoundException(strFile);

        this.cards = new Vector<>(1000);
        JSONObject jsonObj = (JSONObject)JSON_IO.loadJSON(file);

        jsonObj.keySet().iterator();

        for(Object key : jsonObj.keySet()) {
            this.cards.addElement(new MagicCard.CardBuilder(jsonObj.get(key)).build());
        }

        System.out.println(cards.get(0).toSimpleString());

        Iterator test = cards.get(0).getManaCostIterator();

        while(test.hasNext()) {

            Entry pair = (Entry)test.next();
            System.out.print(pair.getKey());
            System.out.print(" ");
            System.out.println(pair.getValue());
        }
    }
}


