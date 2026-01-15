import jdk.jshell.execution.Util;

import java.util.*;

class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int W = in.nextInt();
        int H = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        String T = in.nextLine();
        char[][] map = new char[W][H];
        String[] codes = T.split(" ");
        boolean isStars = true;
        int codePos = 0;
        Integer code = Integer.valueOf(codes[codePos]);
        // fill table
        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                if (code == 0) {
                    isStars = !isStars;
                    codePos ++;
                    if (codePos < codes.length) {
                        code = Integer.valueOf(codes[codePos]);
                    }
                }
                if (isStars) {
                    map[x][y] = '*';
                } else {
                    map[x][y] = ' ';
                }
                code --;
            }
        }

        // display code
        String line = "";
        for (int y = 0; y < map[0].length; y++) {
            line += "|";
            for (int x = 0; x < map.length; x++) {
                line += map[x][y];
            }
            line += "|";
            System.out.println(line);
            line = "";
        }
    }
}