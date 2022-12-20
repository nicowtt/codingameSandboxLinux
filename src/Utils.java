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

    static void printMap() {
        for (int y = 0; y < height; y++) {
            String lines = "";
            for (int x = 0; x < width; x++) {
                lines = lines + occupationMap[x][y];
            }
            //System.err.println(lines);
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

    static void findMyPos() {
        beginPosLeft = tileFarAwayToCenter.getX() < width/2;
        beginUp = tileFarAwayToCenter.getY() < height/2;
    }

    static int distBetweenTwoTile(Tile one, Tile two) {
        return Math.abs(two.getY() - one.getY()) + Math.abs(two.getX() - one.getX());
    }
}
