
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import static org.junit.Assert.*;


//iter    Iterate (for each..in)
//itin    Iterate (for..in)
//itli    Iterate over a List
//itar    Iterate elements of array
//ritar   Iterate elements of array in reverse order


public class Main {

    private static String compressStr(final String str) {

        final Map<Character,Integer> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();

        for (char c : str.toCharArray()) {

            if(map.containsKey(c)) {
                map.replace(c, map.get(c) + 1);
            }
            else {
                map.put(c, 1);
            }
        }

        for (char c : str.toCharArray()) {

            if(sb.indexOf(Character.toString(c)) == -1) {

                sb.append(c);
                sb.append(map.get(c));
            }
        }

        return sb.toString();
    }

    // Note: this exercise shouldn't use the String library
    private static String replaceAllSpaceWith(char[] chars, final char replaceWith) {

        for (int i=0; i < chars.length; ++i) {

            if (chars[i] == ' ') chars[i] = replaceWith;
        }

        return new String(chars);
    }

    private static boolean isPermutation(final String a, final String b) {

        Function<String,String> strSort = str -> {
            char[] arr = str.toCharArray();
            java.util.Arrays.sort(arr);
            return new String(arr);
        };

        if(a.equals(b)) return true;

        return strSort.apply(a).equals(strSort.apply(b));
    }

    private static String reverse(final String str) {

        StringBuilder sb = new StringBuilder(str);

        return sb.reverse().toString();
    }

    private static boolean hasUniqueChar(final String str) {

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

        // 1.4
        assertTrue(
                replaceAllSpaceWith("Hello my name is John".toCharArray(), '5')
                        .equals("Hello5my5name5is5John"));

        // 1.5
        assertTrue(compressStr("Hello World").equals("H1e1l3o2 1W1r1d1"));
    }
}
