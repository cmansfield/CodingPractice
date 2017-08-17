
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Vector;


public class JSON_IO {

    public static Object loadJSON(final String file) {
        JSONParser parser = new JSONParser();

        try {
            return parser.parse(new FileReader(file));

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        return null;
    }

    public static String stringifyQuery(final String keyToQuery, final Object jsonRaw) {

        if(!(jsonRaw instanceof JSONObject)) throw new IllegalArgumentException("JSONObject required for query");
        if(keyToQuery.equals("")) return "";

        Iterator keys = ((JSONObject)jsonRaw).keySet().iterator();
        Vector<String> queryResults = new Vector<>(10);


        while(keys.hasNext()) {

            String key = (String)keys.next();
            JSONObject card = (JSONObject) ((JSONObject)jsonRaw).get(key);

            if(!card.containsKey(keyToQuery)) continue;

            JSONArray queryArr = (JSONArray)card.get(keyToQuery);
            Iterator superTypeIter = queryArr.iterator();

            for ( Object elem : queryArr ) {
                if(!queryResults.contains((String)elem)) {
                    queryResults.add((String)elem);
                }
            }
        }

        return queryResults.toString();
    }
}
