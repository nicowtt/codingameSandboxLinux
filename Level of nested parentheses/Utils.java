import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class Utils {

    public static String input;
    public static char[][] map;

    // methods

    public static void createMap() {
        map = new char[input.length()][9];
        for (int i = 0; i < input.length(); i++) {
            map[i][0] = input.charAt(i); // first line
        }
    }

    public static int findMaxLevel() {
        int open = 0;
        int max = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '(') {
                open ++;
                if (max <= open) {
                    max = open;
                }
            } else if (input.charAt(i) == ')') {
                open --;
            }
        }
        return max;
    }

    public static void writeLigne1and2() {
        for (int i = 0; i < map.length; i++) {
            if (map[i][0] == '(' || map[i][0] == ')') {
               map[i][1] = '^';
               map[i][2] = '|';
            } else {
                map[i][1] = ' ';
                map[i][2] = ' ';
            }
        }
    }

    public static void writeLevel(
        int level,
        int writePos
    ) {
        int count = 0;
        boolean open = false;

        for (int i = 0; i < map.length; i++) {
            if (open) {
                map[i][writePos] = '-';

            } else {
                map[i][writePos] = ' ';
            }

            if (map[i][0] == '(' ) {
                count++;
                if (count == level) {
                    open = true;
                    map[i][writePos] = String.valueOf(level).charAt(0);
                } else if ((!Character.isDigit(map[i][writePos - 1]))
                            && (map[i][writePos - 1] != '-')
                ) {
                    map[i][writePos] = '|';
                }
            }

            if (map[i][0] == ')') {
                if (count == level) {
                    open = false;
                    map[i][writePos] = String.valueOf(level).charAt(0);
                    count --;
                } else if ((!Character.isDigit(map[i][writePos - 1]))
                            && (map[i][writePos - 1] != '-')
                ) {
                    map[i][writePos] = '|';
                    count --;
                } else {
                    count --;
                }
            }
        }
    }

    public static List<Cell> getListOfCellfixForUp() {
        boolean open = false;
        Cell begin = new Cell(0,0);
        List<Cell> beginCells = new ArrayList<>();
        List<Cell> cellsCanUp = new ArrayList<>();

        // get list of begin cell
        for (int y = 4; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                if (Character.isDigit(map[x][y])) {
                    open = !open;
                    if (open && begin.getX() == 0 ) {
                        beginCells.add(new Cell(x,y));
                    }
                }
            }
        }

        for (int i = 0; i < beginCells.size(); i++) {
            Cell cell = beginCells.get(i);
            boolean canUp = true;
            for (int j = cell.getX() + 1; j < map.length; j++) {

                if(map[j][cell.getY() -1] != ' '
                    && map[j][cell.getY() -1] != '|'
                ) {
                    canUp = false;
                    break;
                }

                if(map[j][cell.getY() -1] == '|') {
                    break;
                }

            }
            if (canUp) {
                cellsCanUp.add(cell);
            }
        }
        return cellsCanUp;
    }

    public static void cellsUpAndCleanOld(List<Cell> listToUp) {
        // up to line 4
        for (int i = 0; i < listToUp.size(); i++) {
            Cell cell = listToUp.get(i);
            int begin = cell.getX();
            char nbr = map[cell.getX()][cell.getY()];

            for (int x = cell.getX(); x < map.length; x++) {
                if (x != cell.getX()
                    && Character.isDigit(map[x][cell.getY()])
                ) {
                    map[x][3] = nbr;
                    break;
                }
                if ((x == begin)
                ) {
                    map[x][3] = nbr;
                }
                if (x > begin) {
                    map[x][3] = '-';
                }
            }
        }

        // clean
        for (int i = 0; i < listToUp.size(); i++) {
            Cell cell = listToUp.get(i);
            int digit = 0;
            for (int y = 4; y <= cell.getY() ; y++) {
                for (int x = cell.getX(); x < map.length; x++) {
                    if (Character.isDigit(map[x][cell.getY()])) {
                        digit++;
                        if (digit == 2) {
                            map[x][y] = ' ';
                            break;
                        }
                    }
                    map[x][y] = ' ';
                }
                digit = 0;
            }
        }
    }

    public static void displayMap() {
        String line = "";
        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                line += map[x][y];
            }
            System.out.println(line);
            line = "";
        }
    }
}
