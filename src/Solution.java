import java.util.*;

class Solution {

    // private boolean displayMaps = true;

    public static void main(String args[]) {
        new Solution().run();
    }

        public void run() {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        for (int i = 0; i < n; i++) {
            String answer = in.nextLine();

            String[] splited = answer.split(" ");
            if (splited.length == 3) {
                // record information
                Utils.writeOnMap(Integer.valueOf(splited[0]), splited[1], splited[2]);

            } else {
//                if (displayMaps) {
//                    // display all map only once
//                    Utils.displayFemaleMap();
//                    Utils.displayMaleMap();
//                    displayMaps = false;
//                }

                // get and display result
                System.out.println(Utils.getOnMap(Integer.valueOf(splited[0]), splited[1]));
            }
        }
    }
}