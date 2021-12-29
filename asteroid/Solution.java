import java.util.*;
import java.io.*;
import java.math.*;

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