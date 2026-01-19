import java.util.ArrayList;
import java.util.List;

abstract class Utils {

    public static List<String> lines;
    public static char[][] map;
    public static final String orders = "123456789ABCDEFGHIJKLMNOPQRSTUVWxYZ";

    // methods
    public static char[][] createMap(
        List<String> inputLines,
        int xline,
        int yrow
    ) {
        lines = inputLines;
        map = new char[xline][yrow];
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                map[x][y] = lines.get(y).charAt(x);
            }
        }
        return map;
    }

    public static void displayMap() {
        String line = "";
        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                line += map[x][y];
            }
            line = line.replaceAll("\\s+$", ""); // remove end space
            System.out.println(line);
            line = "";
        }
    }

    public static Cell getCell(char order) {
        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                if (map[x][y] == order) {
                    return new Cell(x, y);
                }
            }
        }
        return null;
    }

    public static void cleanMap() {
        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                if (map[x][y] == '.') {
                    map[x][y] = ' ';
                }
            }
        }
    }
}

