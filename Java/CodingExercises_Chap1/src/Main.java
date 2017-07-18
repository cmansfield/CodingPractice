
import java.util.Collections;

import static org.junit.Assert.*;


public class Main {


    public static String reverse(final String str) {

        StringBuilder sb = new StringBuilder(str);

        return sb.reverse().toString();
    }

    public static boolean hasUniqueChar(final String str) {

        String mutableStr = str;

        for(char chr : str.toCharArray()) {

            final int len = mutableStr.length();
            mutableStr = mutableStr.replaceAll(Character.toString(chr), "");

            if(len - 1 > mutableStr.length()) return false;
        }

        return true;
    }

    public static void main(String[] args) {

        // 1.1
        assertFalse(hasUniqueChar("hello world"));
        assertTrue(hasUniqueChar("helo wrd"));

        // 1.2
        assertTrue(reverse("Hello World").equals("dlroW olleH"));
        assertFalse(reverse("I love Java").equals("So Should You"));

        // 1.3
    }
}
