import java.util.HashMap;
import java.util.Map;

abstract class Utils {
    public static String maleInfo = "";
    public static Map<Integer,String> maleMap = new HashMap<>();
    public static String femaleInfo = "";
    public static Map<Integer,String> femaleMap = new HashMap<>();

    // methods

    public static void writeOnMap(
        int posMin,
        String gender,
        String musicStyle
    ) {
        // System.err.println("write: " + gender + " old: " + posMin + " style: " + musicStyle);
        if (gender.equals("female")) {
            if (femaleInfo.equals("")) {
                femaleInfo = posMin + " " + gender + " " + musicStyle;
                femaleMap.put(posMin, musicStyle);
            } else {
                String[] splitedFemaleInfo = femaleInfo.split(" ");
                // fill standard deviation year old if is the same music style
                if (splitedFemaleInfo[2].equals(musicStyle)) {
                    Integer begin = Integer.valueOf(splitedFemaleInfo[0]);
                    for (int i = begin; i <= posMin; i++) {
                        femaleMap.put(begin, musicStyle);
                        begin++;
                    }
                    femaleInfo = posMin + " " + gender + " " + musicStyle;
                } else {
                    femaleInfo = posMin + " " + gender + " " + musicStyle;
                    femaleMap.put(posMin, musicStyle);
                }
            }

        } else if (gender.equals("male")) {
            if (maleInfo.equals("")) {
                maleInfo = posMin + " " + gender + " " + musicStyle;
                maleMap.put(posMin, musicStyle);
            } else {
                String[] splitedMaleInfo = maleInfo.split(" ");
                // fill standard deviation year old if is the same music style
                if (splitedMaleInfo[2].equals(musicStyle)) {
                    Integer begin = Integer.valueOf(splitedMaleInfo[0]);
                    for (int i = begin; i <= posMin; i++) {
                        maleMap.put(begin, musicStyle);
                        begin++;
                    }
                    maleInfo = posMin + " " + gender + " " + musicStyle;
                } else {
                    maleInfo = posMin + " " + gender + " " + musicStyle;
                    maleMap.put(posMin, musicStyle);
                }
            }
        }
    }

    public static String getOnMap(
        int pos,
        String gender
    ) {
        // System.err.println("get " + gender + " " + pos + " year old");
        if (gender.equals("female") && femaleMap.get(pos) != null
        ) {
            return femaleMap.get(pos);
        } else if (gender.equals("male") && maleMap.get(pos) != null) {
            return maleMap.get(pos);
        } else {
            return "None";
        }
    }

    public static void displayFemaleMap() {
        // System.err.println("******** female map ********** ");
        femaleMap.forEach((key, value) -> System.err.println(key + " " + "female " + value));
    }

    public static void displayMaleMap() {
        // System.err.println("******** Male map ********** ");
        maleMap.forEach((key, value) -> System.err.println(key + " " + "male " + value));
    }
}
