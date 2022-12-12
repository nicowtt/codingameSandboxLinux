class Tile {
    private int x;
    private int y;
    private int scrapAmount;
    private int owner;
    private int units;
    private boolean recycler;
    private boolean canBuild;
    private boolean canSpawn;
    private boolean inRangeOfRecycler;

    public Tile(int x, int y, int scrapAmount, int owner, int units, boolean recycler, boolean canBuild, boolean canSpawn,
        boolean inRangeOfRecycler) {
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
}
