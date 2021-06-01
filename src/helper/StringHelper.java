package helper;

import java.util.ArrayList;

public class StringHelper {

    //class for returning values from GetVowels() and GetConsonants()
    public static class ResultChar {
        public int n_chars;
        public ArrayList<String> chars;

        ResultChar(int n, ArrayList<String> chars_array) {
            n_chars = n;
            chars = chars_array;
        }
    }

    //return num of vowels and an ArrayList of them
    public static ResultChar GetVowels(String s) {

        String vowels = "aeiouAEIOU";

        int n = 0;
        ArrayList<String> chars = new ArrayList<>();

        for (int i=0;i<s.length();i++) {
            String ch = Character.toString(s.charAt(i));

            if (vowels.contains(ch)) {
                chars.add(ch.toUpperCase());
                n++;
            }
        }
        return new ResultChar(n,chars);
    }

    //return num of consonants and an ArrayList of them
    public static ResultChar GetConsonants(String s) {

        String vowels = "aeiouAEIOU";

        int n = 0;
        ArrayList<String> chars = new ArrayList<>();

        for (int i=0;i<s.length();i++) {
            String ch = Character.toString(s.charAt(i));

            if (!vowels.contains(ch)) {
                chars.add(ch.toUpperCase());
                n++;
            }
        }
        return new ResultChar(n,chars);

    }

}
