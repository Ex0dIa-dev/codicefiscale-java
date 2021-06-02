package helper;

import static helper.StringHelper.*;
import helper.StringHelper.ResultChar;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

// Contains all methods for generating Codice Fiscale(Italiano)
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

    //return year code + month code + day code (birthday and gender)
    public static String GetBirthdayGender(String birthday, String gender) {

        //parsing birthday date for extract day,month and year
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate bdate = LocalDate.parse(birthday, formatter);

        //reading and parsing json file
        JSONParser parser = new JSONParser();
        String month_code = "";
        try {
            Object obj = parser.parse(new FileReader("./data/month.json"));
            JSONObject jobj = (JSONObject) obj;

            month_code = (String) jobj.get(String.valueOf(bdate.getMonthValue()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (gender.equalsIgnoreCase("F")) {
            return (String.valueOf(bdate.getYear())).substring(2) + month_code + (bdate.getDayOfMonth() + 40);
        } else if (gender.equalsIgnoreCase("M")) {
            if (bdate.getDayOfMonth() >= 10) {
                return (String.valueOf(bdate.getYear())).substring(2) + month_code + bdate.getDayOfMonth();
            } else {
                return (String.valueOf(bdate.getYear())).substring(2) + month_code + "0" + bdate.getDayOfMonth();
            }
        }

        return "error";
    }

    //return city code by parsing ./data/comuni.json
    public static String GetCityCode(String city) {

        JSONParser parser = new JSONParser();
        String city_code = "";

        try {

            //parsing json file to get city code
            Object obj = parser.parse(new FileReader("./data/comuni.json"));
            JSONObject jobj = (JSONObject) obj;

            city_code = (String) jobj.get(city.toUpperCase());

        } catch (Exception e) {e.printStackTrace();}

        if (!city_code.equals("")) {
            return city_code;
        } else {
            return "error";
        }
    }

    //take in input an incomplete Codice Fiscale, return the final Control Code
    public static String GetControlCode(String cf) {

        //reading and parsing Json file for calculating Control Code
        JSONParser parser = new JSONParser();
        JSONObject control_code = new JSONObject();
        JSONObject even_char_value = new JSONObject();
        JSONObject odd_char_value = new JSONObject();

        try {
            Object obj_cc = parser.parse(new FileReader("./data/controlcode/controlcode.json"));
            Object obj_ech = parser.parse(new FileReader("./data/controlcode/even_char_value.json"));
            Object obj_och = parser.parse(new FileReader("./data/controlcode/odd_char_value.json"));

            control_code = (JSONObject) obj_cc;
            even_char_value = (JSONObject) obj_ech;
            odd_char_value = (JSONObject) obj_och;
        } catch (Exception e) {e.printStackTrace();}

        //checking if Codice Fiscale length is 15
        if (cf.length() != 15) {return "cf-length-error";}

        int n = 0;
        ArrayList<String> even_ch = new ArrayList<>();
        ArrayList<String> odd_ch = new ArrayList<>();

        //extracting chars in even or odd position
        for (int ch=0;ch<cf.length();ch++) {
            if (n%2==0) {even_ch.add(Character.toString(cf.charAt(ch)));} else {odd_ch.add(Character.toString(cf.charAt(ch)));}
            n++;
        }

        //converting to Integer the chars
        int tot = 0;
        for (String ch : odd_ch) {
            if("0123456789".contains(ch)) {tot += Integer.parseInt(ch);} else {tot += Integer.parseInt(String.valueOf(odd_char_value.get(ch)));}
        }
        for (String ch: even_ch) {
            tot += Integer.parseInt(String.valueOf(even_char_value.get(ch)));
        }

        return (String) control_code.get(String.valueOf(tot%26));
    }

}
