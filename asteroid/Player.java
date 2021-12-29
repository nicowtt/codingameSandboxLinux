import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.io.*;
import java.math.*;
class Asteroide {

    private char name;

    private int posT1X;
    private int posT1Y;

    private int posT2X;
    private int posT2Y;

    private int moveT2T1X;
    private int moveT2T1Y;

    private int posT3X;
    private int posT3Y;

    public Asteroide(int posT1X, int posT1Y, char name) {
        this.posT1X = posT1X;
        this.posT1Y = posT1Y;
        this.name = name;
    }

    public Asteroide() {}

    public char getName() {
        return name;
    }

    public int getPosT1X() {
        return posT1X;
    }

    public int getPosT1Y() {
        return posT1Y;
    }

    public int getPosT2X() {
        return posT2X;
    }

    public void setPosT2X(int posT2X) {
        this.posT2X = posT2X;
    }

    public int getPosT2Y() {
        return posT2Y;
    }

    public void setPosT2Y(int posT2Y) {
        this.posT2Y = posT2Y;
    }

    public int getPosT3X() {
        return posT3X;
    }

    public void setPosT3X(int posT3X) {
        this.posT3X = posT3X;
    }

    public int getPosT3Y() {
        return posT3Y;
    }

    public void setPosT3Y(int posT3Y) {
        this.posT3Y = posT3Y;
    }

    public int getMoveT2T1X() {
        return moveT2T1X;
    }

    public void setMoveT2T1X(int moveT2T1X) {
        this.moveT2T1X = moveT2T1X;
    }

    public int getMoveT2T1Y() {
        return moveT2T1Y;
    }

    public void setMoveT2T1Y(int moveT2T1Y) {
        this.moveT2T1Y = moveT2T1Y;
    }

    @Override
    public String toString() {
        return "Asteroide{" +
                ", name='" + name + '\'' +
                " posT1X=" + posT1X +
                ", posT1Y=" + posT1Y +
                " posT2X=" + posT2X +
                ", posT2Y=" + posT2Y +
                ", moveT2T1X=" + moveT2T1X +
                ", moveT2T1Y=" + moveT2T1Y +
                ", posT3X=" + posT3X +
                ", posT3Y=" + posT3Y +
                '}';
    }
}

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

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Solution {

    List<Asteroide> asteroides = new ArrayList<>();
    int deltaT2T1;
    int deltaT3T2;

    public static void main(String args[]) {
        new Solution().run();
    }

    public void run() {
        Scanner in = new Scanner(System.in);
        int W = in.nextInt();
        int H = in.nextInt();
        Map t1map = new Map(new char[W][H]);
        List<String> t1lines = new ArrayList<>();
        List<String> t2lines = new ArrayList<>();
        Map t2map = new Map(new char[W][H]);
        Map t3map = new Map(new char[W][H]);
        int T1 = in.nextInt();
        int T2 = in.nextInt();
        int T3 = in.nextInt();
        deltaT2T1 = T2 - T1;
        deltaT3T2 = T3 - T2;
        for (int i = 0; i < H; i++) {
            String firstPictureRow = in.next();
            t1lines.add(firstPictureRow);
            String secondPictureRow = in.next();
            t2lines.add(secondPictureRow);
        }

        t1map.createMap(t1lines, W, H);
        asteroides = t1map.createAsteroides();
        asteroides = t2map.getAsteroideT2Pos(asteroides, t2lines);
        this.findT2T1move(asteroides);
        this.calcT3T2move(asteroides);
        t3map.writeT3asteroideOnMapAndPrint(asteroides, W, H);
    }

    public void findT2T1move(List<Asteroide> asteroides) {
        for (Asteroide asteroide: asteroides) {
            int rangeT2T1X = asteroide.getPosT2X() - asteroide.getPosT1X();
            int rangeT2T1Y = asteroide.getPosT2Y() - asteroide.getPosT1Y();

            int rangeT1T2X = 0;
            int rangeT1T2Y = 0;

            rangeT1T2X = Math.abs(asteroide.getPosT2X() - asteroide.getPosT1X());
            rangeT1T2Y = Math.abs(asteroide.getPosT2Y() - asteroide.getPosT1Y());

            if (rangeT1T2X > 0 && rangeT1T2Y == 0) {
                asteroide.setMoveT2T1X(rangeT2T1X);
                asteroide.setMoveT2T1Y(0);
            }
            if (rangeT1T2Y > 0 && rangeT1T2X == 0) {
                asteroide.setMoveT2T1X(0);
                asteroide.setMoveT2T1Y(rangeT2T1Y);
            }
            if (rangeT1T2Y > 0 && rangeT1T2X > 0) {
                asteroide.setMoveT2T1X(rangeT2T1X);
                asteroide.setMoveT2T1Y(rangeT2T1Y);
            }
        }
    }

    public void calcT3T2move(List<Asteroide> asteroides) {
        int coef = deltaT2T1 / deltaT3T2;

        for (Asteroide asteroide: asteroides) {
            if (coef == 0) { // no move
                asteroide.setPosT3X(asteroide.getPosT1X());
                asteroide.setPosT3Y(asteroide.getPosT1Y());
            } else {
                // X
                if (asteroide.getMoveT2T1X() < 0) { // inv
                    asteroide.setPosT3X(asteroide.getPosT2X() - (int)Math.abs(Math.floor((float)asteroide.getMoveT2T1X() / coef)));
                } else {
                    asteroide.setPosT3X(asteroide.getPosT2X() + (int)Math.floor((float)(asteroide.getMoveT2T1X() / coef)));
                }

                // Y
                if (asteroide.getMoveT2T1Y() < 0) { // inv
                    asteroide.setPosT3Y(asteroide.getPosT2Y() - (int)Math.abs(Math.floor((float)asteroide.getMoveT2T1Y() / coef)));
                } else {
                    asteroide.setPosT3Y(asteroide.getPosT2Y() + (int)Math.floor((float)(asteroide.getMoveT2T1Y()) / coef));
                }
            }
        }
    }
}
