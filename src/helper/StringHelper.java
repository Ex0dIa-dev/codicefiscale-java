package helper;

import java.util.ArrayList;


public class StringHelper {

    public static class ResultChar {
        public int n_chars;
        public ArrayList<String> chars;

        ResultChar(int n, ArrayList<String> chars_array) {
            n_chars = n;
            chars = chars_array;
        }
    }

    public static ResultChar GetVowels(String s) {

        String[] vowels = {"A", "E", "I", "O", "U", "a", "e", "i", "o", "u"};

        int n = 0;
        ArrayList<String> chars = new ArrayList<String>();

        for (int i=0;i<s.length();i++) {
            String ch = new String(Character.toString(s.charAt(i)));

            for (String vowel : vowels) {
                if (vowel.equals(ch)) {
                    n++;
                    chars.add(ch);
                }
            }
        }


        return new ResultChar(n,chars);
    }

    //public static ResultChar GetConsonants(String s) {}

}
