
import java.util.Collections;
import java.util.function.Function;
import static org.junit.Assert.*;


public class Main {


    private static boolean isPermutation(final String a, final String b) {

        Function<String,String> strSort = str -> {
            char[] arr = str.toCharArray();
            java.util.Arrays.sort(arr);
            return new String(arr);
        };

        if(a.equals(b)) return true;

        return strSort.apply(a).equals(strSort.apply(b));
    }

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
        assertTrue(isPermutation("jobs", "sojb"));
        assertFalse(isPermutation("hello", "world"));
    }
}
