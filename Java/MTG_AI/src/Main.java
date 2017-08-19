

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Vector;


public class Main {

    public static void main(String[] args) {

        final String TEST_CARD = "/resources/TestCard.json";
        final String ALL_CARDS = "/resources/AllCards-x.json";

        try {
            MTG gameMaster = new MTG(new File("").getAbsolutePath() + ALL_CARDS);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
