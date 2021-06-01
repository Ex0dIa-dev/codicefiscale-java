package helper;

import static helper.StringHelper.*;
import helper.StringHelper.ResultChar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CFMethods {

    //return the surname code composed by 3 chars like XXX
    public static String GetSurname(String surname) {

        ResultChar cons = GetConsonants(surname);
        ResultChar vows = GetVowels(surname);

        if (cons.n_chars >= 3) {
            return cons.chars.get(0) + cons.chars.get(1) + cons.chars.get(2);
        } else if (cons.n_chars == 2 && vows.n_chars >= 1) {
            return cons.chars.get(0) + cons.chars.get(1) + vows.chars.get(0);
        } else if (cons.n_chars == 1 && vows.n_chars >= 2) {
            return cons.chars.get(0) + vows.chars.get(0) + vows.chars.get(1);
        }

        return "error";
    }

    //return the name code composed by 3 chars like XXX
    public static String GetName(String name) {

        ResultChar cons = GetConsonants(name);
        ResultChar vows = GetVowels(name);

        if (cons.n_chars >= 4) {
            return cons.chars.get(0) + cons.chars.get(2) + cons.chars.get(3);
        } else if (cons.n_chars == 3) {
            return cons.chars.get(0) + cons.chars.get(1) + cons.chars.get(2);
        } else if (cons.n_chars == 2 && vows.n_chars >= 1) {
            return cons.chars.get(0) + cons.chars.get(1) + vows.chars.get(0);
        } else if (cons.n_chars == 1 && vows.n_chars >= 2) {
            return cons.chars.get(0) + vows.chars.get(0) + vows.chars.get(1);
        }

        return "error";
    }

    public static void GetBirthdayGender(String birthday, String gender)  {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate bdate = LocalDate.parse(birthday,formatter);
    }

}
