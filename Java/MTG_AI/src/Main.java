

import java.io.File;
import java.util.Vector;


public class Main {

    public static void main(String[] args) {

        Vector<Card> cards = new Vector<>(1000);
        Object jsonObj;


        jsonObj = JSON_IO.loadJSON(new File("").getAbsolutePath()
                + "/resources/AllCards-x.json");

        String query = JSON_IO.stringifyQuery("subtypes", jsonObj);

        System.out.println(query);
    }
}
