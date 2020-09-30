
import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    private List<List<String>> simuOrders = new ArrayList<>();
    private char[][] simuMap;
    private char[][] map;
    private SimuUnit simuUnit;
    private int lastRoad;
    private String bestOrder;
    List<Bike> bikes;
    List<Bike> simuBikes;
    private int lastPosx;
    private int minBikeWhoSurvive;

    public static void main(String args[]) {
        new Player().run();
    }
    public void run() {
        List<String> roads = new ArrayList<>();
        this.bikes = new ArrayList<>();
        int loop = 0;
        Scanner in = new Scanner(System.in);
        int M = in.nextInt(); System.err.println("Nbr of bikes: " + M);
        int V = in.nextInt(); System.err.println("Min who must survive: " + V);
        minBikeWhoSurvive = V;
        String L0 = in.next(); roads.add(L0);
        System.err.println("nbr de cellX: " + L0.length());
        System.err.println("max loop: 50");
        String L1 = in.next(); roads.add(L1);
        String L2 = in.next(); roads.add(L2);
        String L3 = in.next(); roads.add(L3);
        this.lastRoad = roads.size();
        System.err.println("last road: " + this.lastRoad);

        // create map
        this.map = new char[L0.length()][roads.size()];
        for (int y = 0; y < roads.size(); y++) {
            for (int x = 0; x < L0.length(); x++) {
                this.map[x][y] = roads.get(y).charAt(x);
            }
        }
        this.lastPosx = L0.length();

        // create motorbikes
        for (int i = 0; i < M; i++) {
            Bike bike = new Bike();
            this.bikes.add(bike);
        }

        // game loop
        while (true) {
            this.simuMap = this.copyMap(this.map);
            int S = in.nextInt(); // the motorbikes' speed
            System.err.println("speed: " + S);
            for (int i = 0; i < M; i++) {
                this.bikes.get(i).setId(i);
                int X = in.nextInt(); bikes.get(i).setX(X);
                int Y = in.nextInt(); bikes.get(i).setY(Y);
                int A = in.nextInt();
                if (A == 1) { this.bikes.get(i).setActive(true);}
                else { this.bikes.get(i).setActive(false);}
                this.bikes.get(i).setSpeed(S);
                // display bike on map
                if (this.bikes.get(i).isActive()) {
                    if (this.bikes.get(i).getX() < this.lastPosx) {
                        this.map[bikes.get(i).getX()][bikes.get(i).getY()] = 'B';
                    }
                }

            }

            // resolving puzzle
            displayMap(map);
            // create all orders combination -> 5 words and 5 turns
            if (loop == 0) {
                this.createCombinationForSimuOrders();
                this.simuUnit = new SimuUnit(this.simuOrders); // creation d'une simuUnit
            }
            // TODO simulation down is NOK -> quand y a du speed il ne doit pas choisir de descendre quand il y a un trou a x+1
            // simulation:
            // je simule un ordre tous les ordres pour trouver la meilleure
            this.simulateAllOrders();
            // order out:
            System.out.println(this.bestOrder);
            loop++;
        }
    }

    public void displayMap(char[][] map) {
        for (int y = 0; y < map[0].length; y++) {
            String line = "";
            for (int x = 0; x < map.length; x++) {
                line += map[x][y];
            }
            System.err.println(line);
            line = "";
        }
    }

    public char[][] copyMap(char[][] map) {
        char[][] copyArray = new char[map.length][map[0].length];

        for (int y = 0; y < copyArray[0].length; y++) {
            for (int x = 0; x < copyArray.length; x++) {
                copyArray[x][y] = map[x][y];
            }
        }
        return copyArray;
    }

    public void createCombinationForSimuOrders() {
        String combination = "";
        String nbrDigitNok;
        String nbrWithGoodNbrOfDigit;
        List<String> listCombinationTotalNbrStr = new ArrayList<>(); // all combination number
        int combTotal = (int) Math.pow(5, 5);

        combTotal -= 1;
        //convert on base 5
        do {
            combination = intToBase(combTotal, 5);
            listCombinationTotalNbrStr.add(combination);
            combTotal--;

        } while (combTotal > 0);
        listCombinationTotalNbrStr.add("0"); // add last combination 0

        // add 0 on combination with nbr of digit < 5
        for (int i = 0; i < listCombinationTotalNbrStr.size(); i++) {
            nbrDigitNok = listCombinationTotalNbrStr.get(i);
            nbrWithGoodNbrOfDigit = completeStringFollowingNbrDigit(nbrDigitNok);
            listCombinationTotalNbrStr.set(i, nbrWithGoodNbrOfDigit);
        }

        // convert combination number on orders words
        for (int i = 0; i < listCombinationTotalNbrStr.size(); i++) {
            List<String> orderList = new ArrayList<>();
            for (int j = 0; j < listCombinationTotalNbrStr.get(i).length(); j++) {
                char c = listCombinationTotalNbrStr.get(i).charAt(j);
                if (c == '0') { orderList.add("SPEED"); }
                if (c == '1') { orderList.add("SLOW"); }
                if (c == '2') { orderList.add("JUMP"); }
                if (c == '3') { orderList.add("UP"); }
                if (c == '4') { orderList.add("DOWN"); }
            }
            this.simuOrders.add(orderList);
        }
        // test:
        System.err.println("simuOrders created: " + this.simuOrders.stream().count());
    }

    public String intToBase(int pEntier, int pBase) {

        int nbr = pEntier;
        int rest;
        String resultTemp = "";
        String resultFinal = "";

        do {
            rest = nbr % pBase;
            resultTemp = resultTemp + rest;
            nbr = nbr / pBase;
        } while (nbr > 0);

        //j'inverse le string resultFinal
        int decompte = resultTemp.length() - 1;
        for (int i = decompte; i >= 0; i--) {
            char digit = resultTemp.charAt(i);
            resultFinal = resultFinal + digit;
        }

        return resultFinal;
    }

    protected String completeStringFollowingNbrDigit(String pIn) {

        int nbrDigit = 5;

        //variables
        String nbrStr = pIn;
        String nbrMaxStr = "1";
        String nbrFinalStr = "";

        // je trouve le chiffre minimum avec le nombre de digit
        for (int i = 0; i < (nbrDigit - 1); i++) {
            nbrMaxStr = nbrMaxStr + "0";
        }
        Integer nbrMax = Integer.parseInt(nbrMaxStr);

        // je complete les String avec pas assez de digit

        // je compte le nombre de digit
        int countNbrDigit = 0;

        for (int i = 0; i < nbrStr.length(); i++) {
            countNbrDigit++;
        }
        // il faut donc rajouter combien de zero
        int nbrzero = nbrDigit - countNbrDigit;

        // je crée les zero manquant
        for (int i = 0; i < nbrzero; i++) {
            nbrFinalStr = nbrFinalStr + "0";
        }

        // je rajoute mon pIn
        nbrFinalStr = nbrFinalStr + nbrStr;

        return nbrFinalStr;
    }

    public void simulateAllOrders()  {
        int countSimuOrder = 0;
        // set position on simuMap
        for (int k = 0; k < this.bikes.size(); k++) {
            if (this.bikes.get(k).getX() < this.lastPosx) {
                this.simuMap[this.bikes.get(k).getX()][this.bikes.get(k).getY()] = 'B';
            }
        }
        for (int i = 0; i < this.simuOrders.size(); i++) { // simulation avec la liste de tous les ordres
            //for (int i = 2031; i < 2032; i++) { // simulation avec la liste de tous les ordres

                // init
            this.simuMap = this.copyMap(map);
            this.simuBikes = new ArrayList<>();
            for (Bike bike : this.bikes) {
                Bike newBike = new Bike();
                newBike.setId(bike.getId());
                newBike.setX(bike.getX());
                newBike.setY(bike.getY());
                newBike.setActive(true);
                newBike.setInvalidOrder(false);
                newBike.setSpeed(bike.getSpeed());
                this.simuBikes.add(newBike);
            }
            //System.err.println("simu bike0 x: " + this.simuBikes.get(0).getX());
            //System.err.println("simu bike0 info: " + this.simuBikes.get(0).toString());

            countSimuOrder++;
            int score = 0;

            // check display on map
            //System.err.println("map before turn");
            //this.displayMap(this.simuMap);

            //System.err.println("simu orders: " + this.simuOrders.get(i).toString());

            for (int j = 0; j < 5; j++) { // simulation avec 5 ordres
                String order = this.simuOrders.get(i).get(j);
                //System.err.println("simu order" + j + " :" + order);


                if (order.equals("SPEED") || order.equals("SLOW")) {
                    this.simuSpeed(order);
                }

                if (order.equals("JUMP")) {
                    this.simuJump();
                }


                if (order.equals("DOWN")) {
                    this.simuDown();
                }

                if (order.equals("UP")) {
                    for (Bike bike: this.simuBikes) {
                        bike.setInvalidOrder(true);
                    }
                }


                // calculate score
                for (Bike bike : this.simuBikes) {
                    if (bike.isInvalidOrder()) {
                        score = -10;
                    }
                    if (bike.isActive() && bike.getSpeed() > 0) {
                        score = score + bike.getX();
                    }

                    if (!bike.isActive()) {
                        score = score - 50;
                    }
                    // TODO check for one comb
                    if (i == 0) {
                        System.err.println("simu order" + j + " bike" + bike.getId() + " is active -> " + bike.isActive());
                        System.err.println(bike.toString());
                    }
                }
                // check display on map
                //System.err.println("map after turn");
                //this.displayMap(this.simuMap);

            }
            this.simuUnit.setScore(i, score);
            //System.err.println("check score for" + i + " :" + this.simuUnit.getScores().get(i));


        }
        // check
        System.err.println("count simu order: " + countSimuOrder);

        // find best score
        int indexOfBestScore = this.findBestScore();
        System.err.println("best order: " + this.simuOrders.get(indexOfBestScore).toString() + " -> N°:" + indexOfBestScore);
        System.err.println("best score: " + this.simuUnit.getScores().get(indexOfBestScore));
        // System.err.println("score for 0: " + this.simuUnit.getScores().get(0));



        this.bestOrder = simuOrders.get(indexOfBestScore).get(0);

    }

    public int findBestScore() {
        int score = -10000;
        int index = 0;
        for (int i = 0; i < this.simuUnit.getScores().size() - 1; i++) {
            if (this.simuUnit.getScores().get(i) > score) {
                score = this.simuUnit.getScores().get(i);
                index = i;
            }
        }
        return index;
    }

    public void simuSpeed(String order) {
        for (int i = 0; i < this.simuBikes.size(); i++) {
            if (this.simuBikes.get(i).isActive()) {
                int speed = this.simuBikes.get(i).getSpeed();
                if (order.equals("SPEED")) {
                    speed++;
                    this.simuBikes.get(i).setSpeed(speed);
                }
                if (order.equals("SLOW")) {
                    if (this.simuBikes.get(i).getSpeed() > 0) {
                        speed--;
                        this.simuBikes.get(i).setSpeed(speed);
                    }
                }
                Cell position = new Cell(this.simuBikes.get(i).getX(), this.simuBikes.get(i).getY());

                // set next position on bike
                if (speed == 0) {
                    this.simuBikes.get(i).setX(position.getX());
                    this.simuBikes.get(i).setY(position.getY());
                } else {
                    this.simuBikes.get(i).setX(this.simuBikes.get(i).getX() + speed );
                    this.simuBikes.get(i).setY(this.simuBikes.get(i).getY());
                }
                // check invalid order -> speed
                if (this.simuBikes.get(i).getSpeed() > 10) { this.simuBikes.get(i).setInvalidOrder(true); }
                if (this.simuBikes.get(i).getSpeed() <= 0) { this.simuBikes.get(i).setInvalidOrder(true); }

                // check if there is a hole
                if (this.simuBikes.get(i).getX() < map.length) {
                    this.simuBikes.get(i).setActive(this.simulationCheckIfBikeIsActive(position,
                            new Cell(this.simuBikes.get(i).getX(), this.simuBikes.get(i).getY())));

                    // test off
                    // set invalid order
                    this.simulationCheckInvalidPos(this.lastRoad, order); // TODO possibility of bug -> see invalid order speed
                    // set next position on simuMap
                    String speedStr = String.valueOf(this.simuBikes.get(i).getSpeed());
                    simuMap[this.simuBikes.get(i).getX()][this.simuBikes.get(i).getY()] = speedStr.charAt(0);
                }

            }
        }
    }

    public void simuJump() {
        for (int i = 0; i < this.simuBikes.size(); i++) {
            if (this.simuBikes.get(i).isActive()) {
                int speed = this.simuBikes.get(i).getSpeed();
                this.simuBikes.get(i).setX(this.simuBikes.get(i).getX() + speed);
                this.simuBikes.get(i).setY(this.simuBikes.get(i).getY());
                if (this.simuBikes.get(i).getX() < map.length) {
                    // check if there is hole on reception
                    if (this.map[this.simuBikes.get(i).getX()][this.simuBikes.get(i).getY()] == '0') {
                        this.simuBikes.get(i).setActive(false);
                    }
                    // set next position on simuMap
                    String speedStr = String.valueOf(this.simuBikes.get(i).getSpeed());
                    simuMap[this.simuBikes.get(i).getX()][this.simuBikes.get(i).getY()] = speedStr.charAt(0);
                }

            }
        }
    }

    public void simuDown() {
        for (int i = 0; i < this.simuBikes.size(); i++) {
            if (this.simuBikes.get(i).isActive() && (this.simuBikes.get(i).getY() + 1 < this.lastRoad - 1) ) { //active and not on last road
                int speed = this.simuBikes.get(i).getSpeed();
                // check if there is hole on simuMap during this move
                // y check
                if (speed > 0) {
                    for (int j = 1; j <= speed; j++) {
                        if (this.simuBikes.get(i).getX() + j < this.lastPosx) {
                            // on x
                            if (this.simuMap[this.simuBikes.get(i).getX() + j][this.simuBikes.get(i).getY()] == 'O') {
                                this.simuBikes.get(i).setActive(false);
                            }
                            // on y
                            if (this.simuBikes.get(i).getY() + 1 < this.lastRoad) {
                                if (this.simuMap[this.simuBikes.get(i).getX() + j][this.simuBikes.get(i).getY() + 1] == '0') {
                                    this.simuBikes.get(i).setActive(false);
                                }
                            }
                        } else {
                            this.simuBikes.get(i).setInvalidOrder(true);
                        }

                    }
                }
                // set new simu pos
                this.simuBikes.get(i).setY(this.simuBikes.get(i).getY() + 1);
                this.simuBikes.get(i).setX(this.simuBikes.get(i).getX() + speed);
            } else {
                this.simuBikes.get(i).setInvalidOrder(true);
            }
        }
    }

    public boolean simulationCheckIfBikeIsActive(Cell beginPos, Cell endPos) {
        int diffOnCellX = endPos.getX() - beginPos.getX();

        for (int i = 0; i <= diffOnCellX; i++) {
            if (this.map[beginPos.getX() + i][beginPos.getY()] == '0') {
                return false;
            }
        }
        return true;
    }

    public void simulationCheckInvalidPos(int lastRoad, String order) {
        boolean bikeOnFirstLine = false;
        for (int i = 0; i < this.simuBikes.size(); i++) {
            if (this.simuBikes.get(i).getY() == 0 && order.equals("UP")) { bikeOnFirstLine = true; }
        }
        if (bikeOnFirstLine) {
            for (Bike bike : this.simuBikes) {
                bike.setInvalidOrder(true);
            }
        }

        boolean bikeOnLastLine = false;
        for (int i = 0; i < this.simuBikes.size(); i++) {
            if (this.simuBikes.get(i).getY() == lastRoad && order.equals("DOWN")) { bikeOnLastLine = true; }
        }
        if (bikeOnLastLine) {
            for (Bike bike : this.simuBikes) {
                bike.setInvalidOrder(true);
            }
        }
    }
}

