import java.util.*;
import java.util.HashMap;
import java.util.List;

class Bike extends Cell{
    private int id;
    private int speed;
    private boolean accelerate;
    private boolean active;
    private boolean invalidOrder;
    private Cell nextCell;

    public Bike() {

        this.invalidOrder = false;
        this.accelerate = false;
    }

    public void setId(int id) { this.id = id; }
    public void setSpeed(int speed) { this.speed = speed; }
    public void setAccelerate(boolean accelerate) { this.accelerate = accelerate; }
    public void setActive(boolean active) { this.active = active; }
    public void setInvalidOrder(boolean invalidOrder) { this.invalidOrder = invalidOrder; }
    public void setNextCell(Cell nextCell) { this.nextCell = nextCell; }

    public int getId() { return id; }
    public int getSpeed() { return speed; }
    public boolean isAccelerate() { return accelerate; }
    public boolean isActive() { return active; }
    public boolean isInvalidOrder() { return invalidOrder; }
    public Cell getNextCell() { return nextCell; }

    // to string
    @Override
    public String toString() {
        return "Bike{" +
                "id=" + id +
                ", speed=" + speed +
                ", active=" + active +
                ", invalidOrder=" + invalidOrder +
                ", nextCell=" + nextCell +
                '}';
    }
}

class Cell {
    private int x;
    private int y;

    public Cell() {}

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    public int getX() { return x; }
    public int getY() { return y; }

    public String toString() {
        return "{" +
                "x: " + x +
                " y: " + y +
                "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cell other = (Cell) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        return true;
    }
}


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

    public static void main(String args[]) {
        new Player().run();
    }
    public void run() {
        List<String> roads = new ArrayList<>();
        this.bikes = new ArrayList<>();
        int loop = 0;
        String increaseRoad = "................................................................................";
        Scanner in = new Scanner(System.in);
        int M = in.nextInt(); System.err.println("Nbr of bikes: " + M);
        int V = in.nextInt(); System.err.println("Min who must survive: " + V);
        String L0 = in.next(); L0 += increaseRoad; roads.add(L0);
        String L1 = in.next(); L1 += increaseRoad; roads.add(L1);
        String L2 = in.next(); L2 += increaseRoad; roads.add(L2);
        String L3 = in.next(); L3 += increaseRoad; roads.add(L3);
        this.lastRoad = roads.size();

        // create map
        this.map = new char[roads.get(0).length()][roads.size()];
        for (int y = 0; y < roads.size(); y++) {
            for (int x = 0; x < roads.get(0).length(); x++) {
                this.map[x][y] = roads.get(y).charAt(x);
            }
        }
        this.lastPosx = roads.get(0).length();

        // create motorbikes
        for (int i = 0; i < M; i++) {
            Bike bike = new Bike();
            this.bikes.add(bike);
        }

        // game loop
        while (true) {
            this.simuMap = this.copyMap(this.map);
            int S = in.nextInt();
            for (int i = 0; i < M; i++) {
                this.bikes.get(i).setId(i);
                int X = in.nextInt(); this.bikes.get(i).setX(X);
                int Y = in.nextInt(); this.bikes.get(i).setY(Y);
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
            // 1/ create all orders combination on first turn -> 5 words and 5 turns -> 3125 combinations
            if (loop == 0) {
                this.createCombinationForSimuOrders();
                this.simuUnit = new SimuUnit(this.simuOrders); // creation d'une simuUnit
            }
            // 2/ simulate all orders, find best scored:
            this.simulateAllOrders();

            // 3/ give best order
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
        // 1/ display position on simuMap
        for (int k = 0; k < this.bikes.size(); k++) {
            if (this.bikes.get(k).getX() < this.lastPosx) {
                this.simuMap[this.bikes.get(k).getX()][this.bikes.get(k).getY()] = 'B';
            }
        }
        for (int i = 0; i < this.simuOrders.size(); i++) { // begin simulation with all orders
            boolean bikeOnFirstRoad = false;
            boolean bikeOnLastRoad = false;
            int score = 0;

            // ***************** initialisation *********************************
            this.simuMap = this.copyMap(map);
            this.simuBikes = new ArrayList<>();
            for (int j = 0; j < this.bikes.size(); j++) {
                Bike newBike = new Bike();
                newBike.setId(this.bikes.get(j).getId());
                newBike.setX(this.bikes.get(j).getX());
                newBike.setY(this.bikes.get(j).getY());
                newBike.setActive(this.bikes.get(j).isActive());
                newBike.setInvalidOrder(false);
                newBike.setSpeed(this.bikes.get(j).getSpeed());
                this.simuBikes.add(newBike);

            }
            // 2/ simulate 5 orders
            for (int j = 0; j < 5; j++) {
                String order = this.simuOrders.get(i).get(j);
                for (Bike bike: this.simuBikes ) { if (bike.getY() + 1 > this.lastRoad - 1) { bikeOnLastRoad = true; } }
                for (Bike bike: this.simuBikes ) { if (bike.getY() - 1 < 0) { bikeOnFirstRoad = true; } }
                if (order.equals("SPEED") || order.equals("SLOW")) { this.simuSpeed(order); }
                if (order.equals("JUMP")) { this.simuJump();}
                if (order.equals("DOWN")) {
                    if (bikeOnLastRoad) {
                        for (Bike bike: this.simuBikes ) { bike.setInvalidOrder(true); } // invalid order if bike is on last road
                    } else {
                        this.simuDown(); }
                }
                if (order.equals("UP")) {
                    if (bikeOnFirstRoad) {
                        for (Bike bike: this.simuBikes ) { bike.setInvalidOrder(true); } // invalid order if bike is on first road
                    } else {
                        this.simuUp();
                    }
                }
                // 3 /calculate score
                for (Bike bike : this.simuBikes) {
                    if (bike.isInvalidOrder()) { score = -10000; }
                    if (bike.isActive() && bike.getSpeed() > 0) { score = score + bike.getX(); }
                    if (!bike.isActive()) { score -= 50; }
                }
            }
            this.simuUnit.setScore(i, score);
        }

        // 4/ find best score
        int indexOfBestScore = this.findBestScore();
        System.err.println("Best order: " + this.simuOrders.get(indexOfBestScore).toString() + " -> N°:" + indexOfBestScore + "/3125");
        System.err.println("Score: " + this.simuUnit.getScores().get(indexOfBestScore));
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
            if (this.simuBikes.get(i).isActive() && !this.simuBikes.get(i).isInvalidOrder()) {
                int speed = this.simuBikes.get(i).getSpeed();
                if (order.equals("SPEED")) {
                    speed++;
                    this.simuBikes.get(i).setAccelerate(true);
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
                    if (position.getX() + this.simuBikes.get(i).getSpeed() < this.lastPosx) {
                        this.simuBikes.get(i).setX(this.simuBikes.get(i).getX() + speed);
                    }
                    this.simuBikes.get(i).setY(this.simuBikes.get(i).getY());
                }
                // check invalid order -> speed
                if (this.simuBikes.get(i).getSpeed() > 10) { this.simuBikes.get(i).setInvalidOrder(true); }
                if (this.simuBikes.get(i).getSpeed() <= 0) { this.simuBikes.get(i).setInvalidOrder(true); }

                // check if there is a hole
                if ((this.simuBikes.get(i).getX() < map.length) && (this.simuBikes.get(i).getY() < this.lastRoad )
                        && (this.simuBikes.get(i).getY() >= 0)) {
                    this.simuBikes.get(i).setActive(this.simulationCheckIfBikeIsActive(position,
                            new Cell(this.simuBikes.get(i).getX(), this.simuBikes.get(i).getY())));
                    // set invalid order
                    this.simulationCheckInvalidPos(this.lastRoad, order);
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
                Cell position = new Cell(this.simuBikes.get(i).getX(), this.simuBikes.get(i).getY());
                if (position.getX() + this.simuBikes.get(i).getSpeed() < this.lastPosx) {
                    this.simuBikes.get(i).setX(position.getX() + speed);
                }
                this.simuBikes.get(i).setY(position.getY());
                if ((this.simuBikes.get(i).getX() < map.length) && (this.simuBikes.get(i).getY() < this.lastRoad)
                        && (this.simuBikes.get(i).getY() >= 0)) {
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
            if (this.simuBikes.get(i).isActive()) {
                int speed = this.simuBikes.get(i).getSpeed();
                if (speed > 0) {
                    for (int j = 1; j <= speed; j++) {
                        // for x
                        if (j != speed) { // on x only under speed
                            if (this.simuMap[this.simuBikes.get(i).getX() + j][this.simuBikes.get(i).getY()] == '0') {
                                this.simuBikes.get(i).setActive(false);
                            }
                        }
                        // for y
                        if (this.simuBikes.get(i).getY() + 1 < this.lastRoad) {
                            if (this.simuMap[this.simuBikes.get(i).getX() + j][this.simuBikes.get(i).getY() + 1] == '0') {
                                this.simuBikes.get(i).setActive(false);
                            }
                        }
                    }
                    Cell position = new Cell(this.simuBikes.get(i).getX(), this.simuBikes.get(i).getY());
                    this.simuBikes.get(i).setX(position.getX() + this.simuBikes.get(i).getSpeed());
                    this.simuBikes.get(i).setY(position.getY() + 1);
                    // set next position on simuMap
                    String speedStr = String.valueOf(this.simuBikes.get(i).getSpeed());
                    simuMap[this.simuBikes.get(i).getX()][this.simuBikes.get(i).getY()] = speedStr.charAt(0);
                }
            }
        }
    }

    public void simuUp() {
        for (int i = 0; i < this.simuBikes.size(); i++) {
            int speed = this.simuBikes.get(i).getSpeed();
            if (speed > 0) {
                for (int j = 1; j <= speed; j++) {
                    // for x
                    if (j != speed) {
                        if (this.map[this.simuBikes.get(i).getX() + j][this.simuBikes.get(i).getY()] == '0') {
                            this.simuBikes.get(i).setActive(false);
                        }
                    }
                    // for y
                    if (this.simuBikes.get(i).getY() - 1 >= 0) {
                        if (this.map[this.simuBikes.get(i).getX() + j][this.simuBikes.get(i).getY() - 1] == '0') {
                            this.simuBikes.get(i).setActive(false);
                        }
                    }
                }
                Cell position = new Cell(this.simuBikes.get(i).getX(), this.simuBikes.get(i).getY());
                this.simuBikes.get(i).setX(position.getX() + this.simuBikes.get(i).getSpeed());
                this.simuBikes.get(i).setY(position.getY() - 1);
                // set next position on simuMap
                String speedStr = String.valueOf(this.simuBikes.get(i).getSpeed());
                simuMap[this.simuBikes.get(i).getX()][this.simuBikes.get(i).getY()] = speedStr.charAt(0);
            }
        }
    }

    public boolean simulationCheckIfBikeIsActive(Cell beginPos, Cell endPos) {
        int diffOnCellX = endPos.getX() - beginPos.getX();

        for (int i = 0; i <= diffOnCellX; i++) {
            if ((beginPos.getY() < this.lastRoad) && (beginPos.getY() >= 0)) {
                if (this.map[beginPos.getX() + i][beginPos.getY()] == '0') {
                    return false;
                }
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

class SimuUnit {

    private List<List<String>> orders;
    private HashMap<Integer, Integer> scores;

    public SimuUnit(List<List<String>> orders) {
        this.orders = orders;
        this.scores = new HashMap<>();
    }

    public List<List<String>> getOrders() { return orders; }
    public HashMap<Integer, Integer> getScores() { return scores; }

    // methods
    public void setScore(int indexOnList, int score) {
        this.scores.put(indexOnList, score);
    }

}
