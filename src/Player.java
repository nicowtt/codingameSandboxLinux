import java.util.*;
import java.util.stream.*;

class Player {

    static final int ME = 1;
    static final int OPP = 0;
    static final int NONE = -1;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int width = in.nextInt();
        int height = in.nextInt();
        Tile[][] map = new Tile[width][height];
        int loop = 0;

        // game loop
        while (true) {
            loop++;
            List<Tile> tiles = new ArrayList<>();
            List<Tile> myTiles = new ArrayList<>();
            List<Tile> oppTiles = new ArrayList<>();
            List<Tile> neutralTiles = new ArrayList<>();
            List<Tile> myUnits = new ArrayList<>();
            List<Tile> oppUnits = new ArrayList<>();
            List<Tile> myRecyclers = new ArrayList<>();
            List<Tile> oppRecyclers = new ArrayList<>();

            int myMatter = in.nextInt();
            int oppMatter = in.nextInt();
            // ------------------------------------------------- input data -------------------------------------------------
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Tile tile = new Tile(x, y, in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt() == 1,
                        in.nextInt() == 1, in.nextInt() == 1, in.nextInt() == 1);
                    tiles.add(tile);
                    map[x][y] = tile;

                    if (tile.getOwner() == ME) {
                        myTiles.add(tile);
                        if (tile.getUnits() > 0) {
                            myUnits.add(tile);
                        } else if (tile.isRecycler()) {
                            myRecyclers.add(tile);
                        }
                    } else if (tile.getOwner() == OPP) {
                        oppTiles.add(tile);
                        if (tile.getUnits() > 0) {
                            oppUnits.add(tile);
                        } else if (tile.isRecycler()) {
                            oppRecyclers.add(tile);
                        }
                    } else {
                        neutralTiles.add(tile);
                    }
                }
            }
            // ------------------------------------------------- input data -------------------------------------------------

            // ------------------------------------------------- action -------------------------------------------------
            List<String> actions = new ArrayList<>();

            for (Tile tile : myTiles) {
                // -------- spawn ----------
                if (tile.isCanSpawn()) {
                    // matter + 10 -> spawn
                    if (myMatter > 10) {
                        actions.add(String.format("SPAWN %d %d %d", 1, tile.getX(), tile.getY()));
                    }

                }
                // -------- build ----------
                if (tile.isCanBuild()) {
                    // recycler loop 1 only
                    boolean shouldBuild = false;
                    if (loop == 1) {
                        shouldBuild = true;
                    }
                    if (shouldBuild) {
                        actions.add(String.format("BUILD %d %d", tile.getX(), tile.getY()));
                    }
                }
            }

            // -------- move ----------
            for (Tile tile : myUnits) {
                // move around with scrapAmount and no control
                if ((tile.getX() - 1) >= 0 && map[tile.getX() - 1][tile.getY()].getScrapAmount() > 0 && map[tile.getX() - 1][tile.getY()].getOwner() < 1) {
                    actions.add(String.format("MOVE %d %d %d %d %d", tile.getUnits(), tile.getX(), tile.getY(), tile.getX() - 1, tile.getY()));
                } else if ((tile.getY() - 1) >= 0 && map[tile.getX()][tile.getY() - 1].getScrapAmount() > 0 && map[tile.getX()][tile.getY() - 1].getOwner() < 1) {
                    actions.add(String.format("MOVE %d %d %d %d %d", tile.getUnits(), tile.getX(), tile.getY(), tile.getX(), tile.getY() - 1));
                } else if ((tile.getX() + 1) < width && map[tile.getX() + 1][tile.getY()].getScrapAmount() > 0 && map[tile.getX() + 1][tile.getY()].getOwner() < 1) {
                    actions.add(String.format("MOVE %d %d %d %d %d", tile.getUnits(), tile.getX(), tile.getY(), tile.getX() + 1, tile.getY()));
                } else if ((tile.getY() + 1) < height && map[tile.getX()][tile.getY() + 1].getScrapAmount() > 0 && map[tile.getX()][tile.getY() + 1].getOwner() < 1) {
                    actions.add(String.format("MOVE %d %d %d %d %d", tile.getUnits(), tile.getX(), tile.getY(), tile.getX(), tile.getY() + 1));
                } else {
                    Tile target = new Tile(width/2, height/2);
                    actions.add(String.format("MOVE %d %d %d %d %d", tile.getUnits(), tile.getX(), tile.getY(), target.getX(), target.getY()));
                }
            }

            // ------------------------------------------------- result -------------------------------------------------

            if (actions.isEmpty()) {
                System.out.println("WAIT");
            } else {
                System.out.println(actions.stream().collect(Collectors.joining(";")));
            }
        }
    }
}