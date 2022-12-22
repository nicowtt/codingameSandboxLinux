class Tile {
    private int id;
    private int x;
    private int y;
    private int scrapAmount;
    private int owner;
    private int units;
    private boolean recycler;
    private boolean canBuild;
    private boolean canSpawn;
    private boolean inRangeOfRecycler;

    private boolean canMoveAround;
    private int spawnScore;
    private int spawnScoreFulFil;
    private int buildScore;

    public Tile(int id, int x, int y, int scrapAmount, int owner, int units, boolean recycler, boolean canBuild, boolean canSpawn,
        boolean inRangeOfRecycler) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.scrapAmount = scrapAmount;
        this.owner = owner;
        this.units = units;
        this.recycler = recycler;
        this.canBuild = canBuild;
        this.canSpawn = canSpawn;
        this.inRangeOfRecycler = inRangeOfRecycler;
    }

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getScrapAmount() {
        return scrapAmount;
    }

    public void setScrapAmount(int scrapAmount) {
        this.scrapAmount = scrapAmount;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public boolean isRecycler() {
        return recycler;
    }

    public void setRecycler(boolean recycler) {
        this.recycler = recycler;
    }

    public boolean isCanBuild() {
        return canBuild;
    }

    public void setCanBuild(boolean canBuild) {
        this.canBuild = canBuild;
    }

    public boolean isCanSpawn() {
        return canSpawn;
    }

    public void setCanSpawn(boolean canSpawn) {
        this.canSpawn = canSpawn;
    }

    public boolean isInRangeOfRecycler() {
        return inRangeOfRecycler;
    }

    public void setInRangeOfRecycler(boolean inRangeOfRecycler) {
        this.inRangeOfRecycler = inRangeOfRecycler;
    }

    public int getSpawnScore() {
        return spawnScore;
    }

    public void setSpawnScore(int spawnScore) {
        this.spawnScore = spawnScore;
    }

    public int getBuildScore() {
        return buildScore;
    }

    public void setBuildScore(int buildScore) {
        this.buildScore = buildScore;
    }

    public boolean isCanMoveAround() {
        return canMoveAround;
    }

    public void setCanMoveAround(boolean canMoveAround) {
        this.canMoveAround = canMoveAround;
    }

    public int getSpawnScoreFulFil() {
        return spawnScoreFulFil;
    }

    public void setSpawnScoreFulFil(int spawnScoreFulFil) {
        this.spawnScoreFulFil = spawnScoreFulFil;
    }

    @Override
    public String toString() {
        return "Tile{" +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    private boolean isInMap() {
        return x >= 0 && y >= 0;
    }

}
