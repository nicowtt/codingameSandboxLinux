import java.util.*;

class Solution {

    public static void main(String args[]) {
        // for information, coding game local test 100% but 80% after soumission (1 test is wrong for them) - need wrong test condition to fix my code
        Scanner in = new Scanner(System.in);
        Utils.input = in.nextLine();

        Utils.createMap();
        int maxlevel = Utils.findMaxLevel();
        if (maxlevel == 0) {
            Utils.displayMap(); // no '(' found
        } else {
            Utils.writeLigne1and2();
            int writePos = 3;
            for (int j = maxlevel; j >= 1; j--) {
                Utils.writeLevel(j, writePos);
                writePos++;
            }
            Utils.cellsUpAndCleanOld(Utils.getListOfCellfixForUp());
            Utils.displayMap();
        }
    }
}