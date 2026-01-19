import java.util.*;

class Solution {

    char[][] map;

    public static void main(String args[]) {
        new Solution().run();
    }

    public void run() {
        Scanner in = new Scanner(System.in);
        List<String> rows = new ArrayList<>();
        int H = in.nextInt();
        int W = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        for (int i = 0; i < H; i++) {
            rows.add(in.nextLine());
        }
        map = Utils.createMap(rows, W, H);

        boolean twoPointFound = false;
        Cell firstPoint = null;
        Cell secondPoint = null;
        for (int i = 0; i < Utils.orders.length(); i++) {
            char order = Utils.orders.charAt(i);
            Cell cell = Utils.getCell(order);
            if (cell != null) {
                if (firstPoint == null) {
                    firstPoint = cell;
                } else {
                    secondPoint = cell;
                    twoPointFound = true;
                }
                if (twoPointFound) {
                    this.traceTwoPointStep(firstPoint, secondPoint);
                    twoPointFound = false;
                    firstPoint = secondPoint;
                }
            }

        }
        writePoint(secondPoint, "horizontal", secondPoint.getX(), secondPoint.getY()); // write ° last point
        Utils.cleanMap();
        Utils.displayMap();
    }

    public void traceTwoPointStep(
        Cell firstPoint,
        Cell secondPoint
    ) {
        String direction;
        // horizontal
        if (firstPoint.getY() == secondPoint.getY()) {
            direction = "h";
            if ((secondPoint.getX() > firstPoint.getX()) ) {
                // right
                for (int x = firstPoint.getX(); x < secondPoint.getX(); x++) {
                    this.writePoint(firstPoint, direction, x, secondPoint.getY());
                }
            } else {
                // left
                for (int x = firstPoint.getX(); x > secondPoint.getX(); x--) {
                    this.writePoint(firstPoint, direction, x, secondPoint.getY());
                }

            }

        }
        // vertical
        if (firstPoint.getX() == secondPoint.getX()) {
            direction = "v";
            if ((secondPoint.getY() > firstPoint.getY()) ) {
                // bottom
                for (int y = firstPoint.getY(); y< secondPoint.getY(); y++) {
                    this.writePoint(firstPoint, direction, secondPoint.getX(), y);
                }
            } else {
                // up
                for (int y = firstPoint.getY(); y > secondPoint.getY(); y--) {
                    this.writePoint(firstPoint, direction, secondPoint.getX(), y);
                }

            }

        }
        // diagonal
        if (firstPoint.getX() != secondPoint.getX()
            && firstPoint.getY() != secondPoint.getY()
        ) {

            // right bottom
            if ((secondPoint.getX() > firstPoint.getX())
                && (secondPoint.getY() > firstPoint.getY())
            ) {
                direction = "dl";
                int x = firstPoint.getX();
                for (int y = firstPoint.getY(); y < secondPoint.getY(); y++) {
                    this.writePoint(firstPoint, direction, x, y);
                    x++;
                }
            }

            // right up
            if ((secondPoint.getX() > firstPoint.getX())
                && (secondPoint.getY() < firstPoint.getY())
            ) {
                direction = "dr";
                int x = firstPoint.getX();
                for (int y = firstPoint.getY(); y > secondPoint.getY(); y--) {
                    this.writePoint(firstPoint, direction, x, y);
                    x++;
                }
            }


            // left up
            if ((secondPoint.getX() < firstPoint.getX())
                && (secondPoint.getY() < firstPoint.getY())
            ){
                direction = "dl";
                int y = firstPoint.getY();
                for (int x = firstPoint.getX(); x > secondPoint.getX(); x--) {
                    this.writePoint(firstPoint, direction, x, y);
                    y--;
                }
            }

            // left bottom
            if ((secondPoint.getX() < firstPoint.getX())
                && (secondPoint.getY() > firstPoint.getY())
            ){
                direction = "dr";
                int y = firstPoint.getY();
                for (int x = firstPoint.getX(); x > secondPoint.getX(); x--) {
                    this.writePoint(firstPoint, direction, x, y);
                    y++;
                }
            }
        }
    }

    public void writePoint(
        Cell cell,
        String direction,
        int x,
        int y
    ) {
        // specials
        if (map[x][y] == '/'
            || map[x][y] == '\\'
        ) {
            map[x][y] = 'X';
            return;
        }

        if (map[x][y] == '-'
            || map[x][y] == '|'
        ) {
            map[x][y] = '+';
            return;
        }

        if (map[x][y] == '+'
            || map[x][y] == 'X'
        ) {
            map[x][y] = '*';
            return;
        }

        if (cell.getX() == x
            && cell.getY() == y
        ) {
            // write o for first point
            map[x][y] = 'o';
        } else {
            // others
            switch (direction) {
                case "h":
                    map[x][y] = '-';
                    break;
                case "v": map[x][y] = '|';
                    break;
                case "dl": map[x][y] = '\\';
                    break;
                case "dr": map[x][y] = '/';
                    break;
                default: map[x][y] = '?';
                    break;
            }
        }
    }
}