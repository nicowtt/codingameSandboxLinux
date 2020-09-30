import java.util.HashMap;
import java.util.List;

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
