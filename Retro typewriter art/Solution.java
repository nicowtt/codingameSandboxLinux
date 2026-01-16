import java.util.*;

class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        String T = in.nextLine();

        String[] parts = T.split(" ");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            String withoutLastLetter = part.substring(0, part.length() - 1);
            if (!part.isBlank() && !part.equals("nl")) {
                int number = Integer.parseInt(withoutLastLetter.replaceAll("\\D", "")); // keep only number
                for (int j = 0; j < number; j++) {
                    if (isAbbreviation(part)) {
                        result.append(convertAbbreviation(part));
                    } else {
                        char letter = part.charAt(part.length() - 1);
                        result.append(letter);
                    }
                }
            } else {
                System.out.println(result);
                result = new StringBuilder("");
            }
        }
        System.out.println(result);
    }

    public static String convertAbbreviation(String abbreviation) {
        String lastTwo = abbreviation.substring(abbreviation.length() - 2);
        switch (lastTwo) {
            case "sp": return " ";
            case "bS": return "\\";
            case "sQ": return "'";
            default: return "CONVERT ERROR";
        }
    }

    public static boolean isAbbreviation(String abbreviation) {
        if (abbreviation.length() > 2) {
            String lastTwo = abbreviation.substring(abbreviation.length() - 2);
            switch (lastTwo) {
                case "sp":
                case "bS":
                case "sQ": return true;
                default: return false;
            }
        }
        return false;
    }
}