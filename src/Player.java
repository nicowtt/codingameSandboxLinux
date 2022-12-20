import java.util.*;
import java.util.stream.*;

class Player {

    static final int ME = 1;
    static final int OPP = 0;
    static final int NONE = -1;
    private int myMatter;

    public static void main(String args[]) {
        new Player().run();
    }

        public void run() {
        Scanner in = new Scanner(System.in);
        int width = in.nextInt();
        Utils.width = width;
        int height = in.nextInt();
        Utils.height = height;
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

            this.myMatter = in.nextInt();
            int oppMatter = in.nextInt();
            // ------------------------------------------------- input data -------------------------------------------------
            int tileId = 0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Tile tile = new Tile(tileId, x, y, in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt() == 1,
                        in.nextInt() == 1, in.nextInt() == 1, in.nextInt() == 1);
                    tiles.add(tile);
                    tileId++;
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
            Utils.map = map;
            Utils.myUnitsTile = myUnits;
            Utils.tiles = tiles;
            Utils.myTiles = myTiles;
            Utils.myRecycler = myRecyclers;

            Utils.createOccupationMap();
            Utils.updateOccupationMap();
            //Utils.printMap();
            if (loop == 1) {
                Utils.findUnitIdFarAwayFromMiddle();
                Utils.findMyPos();
            }

            // ------------------------------------------------- input data -------------------------------------------------

            // ------------------------------------------------- action -------------------------------------------------
            //System.err.println("Mymatter: " + this.myMatter);
            List<String> actions = new ArrayList<>();
            for (Tile tile : myTiles) {

                // -------- spawn ----------
                if (tile.isCanSpawn()) {
                    // try to better spawn
                    List<Tile> targetTiles = new ArrayList<>(oppTiles);
                    for (Tile neutralTile: neutralTiles) {
                        if (neutralTile.getScrapAmount() > 0) {
                            targetTiles.add(neutralTile);
                        }
                    }

                    List<Tile> canSpawnTiles = myTiles.stream().filter(Tile::isCanSpawn).collect(Collectors.toList());
                    if (!canSpawnTiles.isEmpty()
                        && myMatter > 10
                    ) {
                        for (Tile canSpawnTile: canSpawnTiles) {
                            List<Integer> distances = new ArrayList<>();
                            for (Tile targetTile: targetTiles) {
                                distances.add(Utils.distBetweenTwoTile(targetTile, canSpawnTile));
                            }
                            canSpawnTile.setSpawnScore(distances.stream().reduce(0, Integer::sum));

                        }
                        canSpawnTiles.sort(Comparator.comparing(Tile::getSpawnScore));

                        Tile target = canSpawnTiles.get(0);
                        //System.err.println("spawn");
                        actions.add(String.format("SPAWN %d %d %d", 1, target.getX(), target.getY()));
                        myMatter -= 10;
                    }

                    // matter + 10 -> spawn on random units
//
                }
                // -------- build ----------
                // try same as spawn
                //System.err.println("Mymatter after spawn: " + myMatter);
                List<Tile> buildedTiles = myTiles.stream().filter(Tile::isRecycler).collect(Collectors.toList());
                List<Tile> targetTiles = new ArrayList<>(oppTiles);
                for (Tile neutralTile: neutralTiles) {
                    if (neutralTile.getScrapAmount() > 0) {
                        targetTiles.add(neutralTile);
                    }
                }

                List<Tile> canBuildTiles = myTiles.stream().filter(Tile::isCanBuild).collect(Collectors.toList());
                //System.err.println("can build tile size: " + canBuildTiles.size());
                if ((!canBuildTiles.isEmpty()
                    && myMatter >= 20
                    && buildedTiles.size() < 3)
                        || loop == 2
                ) {
                    for (Tile canBuildTile: canBuildTiles) {
                        List<Integer> distances = new ArrayList<>();
                        for (Tile targetTile: targetTiles) {
                            distances.add(Utils.distBetweenTwoTile(canBuildTile, targetTile));
                        }
                        canBuildTile.setBuildScore(distances.stream().reduce(0, Integer::sum));

                    }
                    canBuildTiles.sort(Comparator.comparing(Tile::getBuildScore));

                    Tile target = canBuildTiles.get(0);
                    //System.err.println("build");
                    actions.add(String.format("BUILD %d %d", target.getX(), target.getY()));
                    myMatter -= 10;
                    break;
                }
            }

            // -------- move ----------
            List<Tile> targetInProgress = new ArrayList<>();
            for (Tile tile : myUnits) {
            // try to move first closed tile
                Tile closedTile = Utils.findClosedFreeTile(tile, targetInProgress);
                targetInProgress.add(closedTile);
                actions.add(String.format("MOVE %d %d %d %d %d", 1, tile.getX(), tile.getY(), closedTile.getX(), closedTile.getY()));

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