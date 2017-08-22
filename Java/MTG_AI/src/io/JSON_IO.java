package io;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Vector;


public class JSON_IO {

    public static Object loadJSON(final File file) {
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

            if(card.get(keyToQuery) instanceof String) {
                if(!queryResults.contains((String)card.get(keyToQuery))) {
                    queryResults.add((String)card.get(keyToQuery));
                }
                continue;
            }

            JSONArray queryArr = (JSONArray)card.get(keyToQuery);

            for ( Object elem : queryArr ) {
                if(!queryResults.contains((String)elem)) {
                    queryResults.add((String)elem);
                }
            }
        }

//        for (String result : queryResults) {
//            System.out.printf("\"%s\", ", result);
//        }
//
//        System.out.println();
//
//        for (String result : queryResults) {
//            System.out.printf("%s, ", result.toUpperCase());
//        }
//
//        System.out.println();

        return queryResults.toString();
    }
}
