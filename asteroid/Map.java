import java.util.ArrayList;
import java.util.List;

class Map {

    private final char[][] map;
    private int limitX;
    private int limitY;
    private final List<Asteroide> asteroides;

    public Map(char[][] map) {
        this.map = map;
        asteroides = new ArrayList<>();
    }

    // methods
    public void createMap(List<String> lines, int limitX, int limitY) {
        this.limitX = limitX;
        this.limitY = limitY;

        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                map[x][y] = lines.get(y).charAt(x);
            }
        }
    }

    public List<Asteroide> createAsteroides() {
        for (int y = 0; y < limitY; y++) {
            for (int x = 0; x < limitX; x++) {
                if (map[x][y] != '.' ) {
                    Asteroide asteroide = new Asteroide(x, y, map[x][y]);
                    asteroides.add(asteroide);
                }
            }
        }
        return this.asteroides;
    }

    public List<Asteroide> getAsteroideT2Pos(List<Asteroide> asteroides, List<String> lines) {
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {

                for (Asteroide asteroide: asteroides) {
                    if (lines.get(y).charAt(x) != '.'
                            && asteroide.getName() == lines.get(y).charAt(x)) {
                        asteroide.setPosT2X(x);
                        asteroide.setPosT2Y(y);
                    }
                }
            }
        }
        return asteroides;
    }

    public void printMap() {
        for (int y = 0; y < limitY; y++) {
            String lines = "";
            for (int x = 0; x < limitX; x++) {
                lines = lines + map[x][y];
            }
            System.err.println(lines);
        }
    }

    public void writeT3asteroideOnMapAndPrint(
            List<Asteroide> asteroides,
            int limitX,
            int limitY
    ) {
        // init map
        for (int y = 0; y < limitY; y++) {
            for (int x = 0; x < limitX; x++) {
                this.map[x][y] = '.';
            }
        }

        // write closed asteroid first
        for (Asteroide asteroide: asteroides) {
            for (int y = 0; y < limitY; y++) {
                for (int x = 0; x < limitX; x++) {
                    if (x == asteroide.getPosT3X()) {
                        if (y == asteroide.getPosT3Y()) {
                            int onMapIndex = this.getIndex(this.map[x][y]);
                            int asteroidIndex = this.getIndex(asteroide.getName());
                            if (onMapIndex == -1) {
                                map[x][y] = asteroide.getName();
                            } else if (asteroidIndex < onMapIndex) {
                                map[x][y] = asteroide.getName();
                            }
                        }
                    }
                }
            }
        }

        // print
        for (int y = 0; y < limitY; y++) {
            String lines = "";
            for (int x = 0; x < limitX; x++) {
                lines = lines + map[x][y];
            }
            System.out.println(lines);
        }
    }

    private int getIndex(char letter) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < alphabet.length(); i++) {
            char alphabetLetter = alphabet.charAt(i);
            if (letter == alphabetLetter) {
                return i;
            }
        }
        return -1;
    }
}