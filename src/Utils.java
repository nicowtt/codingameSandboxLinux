import java.util.List;

abstract class Utils {

    static boolean beginPosLeft;
    static boolean beginUp;

    static List<Tile> tiles;
    static List<Tile> myUnitsTile;
    static List<Tile> myTiles;
    static List<Tile> myRecycler;

    static int width;
    static int height;
    static char[][] occupationMap;
    static Tile[][] map;

    static Tile tileFarAwayToCenter;

    static int fulfilCount;

    // methods

    static void createOccupationMap() {
        occupationMap = new char[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (map[x][y].getScrapAmount() > 0) {
                    occupationMap[x][y] = '.';
                }
                if (map[x][y].getScrapAmount() == 0) {
                    occupationMap[x][y] = 'X';
                }
            }
        }
    }

    static void updateOccupationMap() {
        for (Tile tile: myUnitsTile) {
            occupationMap[tile.getX()][tile.getY()] = 'm';
        }
    }

    static void printOccupationMap() {
        for (int y = 0; y < height; y++) {
            String lines = "";
            for (int x = 0; x < width; x++) {
                lines = lines + occupationMap[x][y];
            }
            System.err.println(lines);
        }
    }

    static void findUnitIdFarAwayFromMiddle() {
        Tile target = new Tile(width/2, height/2);
        Integer farValue = Integer.MIN_VALUE;

        for (Tile tile: myTiles) {
            if (distBetweenTwoTile(target, tile) > farValue) {
                farValue = distBetweenTwoTile(target, tile);
                tileFarAwayToCenter = tile;
            }
        }
    }

    static Tile findClosedFreeTile(Tile myTile, List<Tile> avoidTiles) {
        Integer closeValue = Integer.MAX_VALUE;
        Tile closedTile = new Tile(0,0);

        for (Tile tile: tiles) {
            if (distBetweenTwoTile(myTile, tile) < closeValue
                && tile.getScrapAmount() > 0
                && tile.getOwner() < 1
                && !avoidTiles.contains(tile)
            ) {
                closeValue = distBetweenTwoTile(myTile, tile);
                closedTile = tile;
            }
        }
        return closedTile;
    }

    static boolean canMoveAround(Tile myTile) {
        int moveScore = 0;
        // south
        if ((myTile.getY() + 1) >= 0 
             && myTile.getY() + 1 < Utils.height
             && map[myTile.getX()][myTile.getY()+1].getScrapAmount() > 0
        ) {
            moveScore += 1;
        }

        // east
        if ((myTile.getX() + 1) >= 0
                && myTile.getX() + 1 < Utils.width
                && map[myTile.getX()+1][myTile.getY()].getScrapAmount() > 0
        ) {
            moveScore += 1;
        }

        // north
        if ((myTile.getY() - 1) >= 0
             && map[myTile.getX()][myTile.getY()-1].getScrapAmount() > 0
        ) {
            moveScore += 1;
        }

        // west
        if ((myTile.getX() - 1) >= 0
                && map[myTile.getX()-1][myTile.getY()].getScrapAmount() > 0
        ) {
            moveScore += 1;
        }

        return moveScore > 1;
    }

    static void findMyPos() {
        beginPosLeft = tileFarAwayToCenter.getX() < width/2;
        beginUp = tileFarAwayToCenter.getY() < height/2;
    }

    static int distBetweenTwoTile(Tile one, Tile two) {
        return Math.abs(two.getY() - one.getY()) + Math.abs(two.getX() - one.getX());
    }

    private static void floodFillUtil(char[][] zone, int x, int y, char newC) {
        // avoid out of map
        if ((x >= 0 && x < width ) && (y > 0 && y < height)
            && zone[x][y] == '.'
        ) {
            fulfilCount += 1;
            //printOccupationMap();
            // Replace by new char '*' at (x, y)
            zone[x][y] = newC;

            // Recur for north, east, south and west
            floodFillUtil(zone, x + 1, y, newC);
            floodFillUtil(zone, x - 1, y, newC);
            floodFillUtil(zone, x, y + 1, newC);
            floodFillUtil(zone, x, y - 1, newC);
        }
    }

    static int floodFill(char[][] zone, int x, int y, char newC) {
        fulfilCount = 0;
        floodFillUtil(zone, x, y, newC);
        return fulfilCount;
    }
}
