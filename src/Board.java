import java.util.List;

class Board {
    private int nbrCellX;
    private int nbrCellY;
    private List<Cell> listCellEarth;
    private List<Cell> listCellAlreadyVisited;
    private List<Sector> listSecteurs;

    // constructor
    public Board() {
    }

    public Board(int nbrCellX, int nbrCellY, List<Cell> listCellEarth, List<Cell> listCellAlreadyVisited, List<Sector> listSecteurs) {
        this.nbrCellX = nbrCellX;
        this.nbrCellY = nbrCellY;
        this.listCellEarth = listCellEarth;
        this.listCellAlreadyVisited = listCellAlreadyVisited;
        this.listSecteurs = listSecteurs;
    }

    // getters setters
    public int getNbrCellX() {
        return nbrCellX;
    }
    public void setNbrCellX(int nbrCellX) {
        this.nbrCellX = nbrCellX;
    }
    public int getNbrCellY() {
        return nbrCellY;
    }
    public void setNbrCellY(int nbrCellY) {
        this.nbrCellY = nbrCellY;
    }
    public List<Cell> getListCellEarth() {
        return listCellEarth;
    }
    public void setListCellEarth(List<Cell> listCellEarth) {
        this.listCellEarth = listCellEarth;
    }
    public List<Cell> getListCellAlreadyVisited() {
        return listCellAlreadyVisited;
    }
    public void setListCellAlreadyVisited(List<Cell> listCellAlreadyVisited) {
        this.listCellAlreadyVisited = listCellAlreadyVisited;
    }
    public List<Sector> getListSecteurs() {
        return listSecteurs;
    }
    public void setListSecteurs(List<Sector> listSecteurs) {
        this.listSecteurs = listSecteurs;
    }

    // to string
    @Override
    public String toString() {
        return "Board{" +
                "nbrCellX=" + nbrCellX +
                ", nbrCellY=" + nbrCellY +
                ", listCellEarth=" + listCellEarth +
                ", listCellAlreadyVisited=" + listCellAlreadyVisited +
                ", listSecteurs=" + listSecteurs +
                '}';
    }

    public void updateBoard(int nbrCellX, int nbrCellY) {
        this.setNbrCellX(nbrCellX);
        this.setNbrCellY(nbrCellY);
    }
}