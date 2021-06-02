import static helper.CFMethods.*;

public class CodiceFiscale {

    public static String CalculateCodiceFiscale(String surname, String name, String birthday,String gender, String city) {

        String cf = "";
        cf += GetSurname(surname);
        cf += GetName(name);
        cf += GetBirthdayGender(birthday, gender);
        cf += GetCityCode(city);
        cf += GetControlCode(cf);

        return cf;
    }

}
