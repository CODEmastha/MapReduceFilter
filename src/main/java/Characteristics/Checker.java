package Characteristics;

import java.util.Arrays;
import java.util.List;

public class Checker {
    private final static List<String> years = Arrays.asList("2006", "2013", "2018");
    private final static List<String> ages = Arrays.asList("1", "2", "3", "4", "999999");
    private final static List<String> genders = Arrays.asList("1", "2", "9");
    private final static List<String> ethnicities = Arrays.asList("1", "2", "3", "4", "9999");
    private final static List<String> regions = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "12", "13", "14", "15", "16", "17", "18", "99");

    public Checker() {
    }

    public boolean checkKey(String year, String area, String age, String ethnicity, String gender) {
        if (!years.contains(year) || !ages.contains(age) || !genders.contains(gender) || !ethnicities.contains(ethnicity) || !regions.contains(area))
            return false;
        if (age.equals("999999") && ethnicity.equals("9999") && genders.contains(gender))
            return true;
        if (!age.equals("999999") && ethnicity.equals("9999") && gender.equals("9"))
            return true;
        return age.equals("999999") && !ethnicity.equals("9999") && gender.equals("9");
    }
}
