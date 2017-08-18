
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Vector;


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

        System.out.println(cards.get(0).toString());
    }
}


