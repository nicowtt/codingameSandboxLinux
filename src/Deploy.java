import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * to deploy in real water!
 */
class Deploy {
    /**
     * test for analyse map
     */
    public void displayEarthAnalysis(HashMap<Integer, String> earthMap) {
        for (int i = 0; i < 15; i++) {
            String value = earthMap.get(i);
            // check
//            System.err.println(value);
        }
    }

    /**
     * Deploy corner bottom left or bottom Middle
     */
    public String deploy1(HashMap<Integer, String> earthMap) {
        // 1 deploy corner bottom left or middle bottom if there is earth
        String line14 = earthMap.get(14);
        char deployPoint = line14.charAt(0);
        if (deployPoint == '.') { return "0 14"; }
        else { return "7 14"; }
    }

    /**
     * Deploy corner up left or Middle up
     */
    public String deploy2(HashMap<Integer, String> earthMap) {
        String line0 = earthMap.get(0);
        char deployPoint = line0.charAt(0);
        if (deployPoint == '.') { return "0 0"; }
        else { return "7 0"; }
    }

    public Cell recordDeploiementCell(String deploy) {
        Cell deployCell = new Cell();
        String space = " ";
        String positions[] = deploy.split(space);
        String posXstring = positions[0];
        String posYstring =  positions[1];
        deployCell.setX(Integer.parseInt(posXstring));
        deployCell.setY(Integer.parseInt(posYstring));
        return deployCell;
    }

    public void recordEarthCellOnBoard(HashMap<Integer, String> earthMap, Board board) {
        List<Cell> listCellContainsEarth = new ArrayList<>();
        char oneChar;
        for (int i = 0; i < earthMap.size(); i++) {
            String line = earthMap.get(i);
            for (int j = 0; j < line.length(); j++) {
                oneChar = line.charAt(j);
                if (oneChar == 'x') {
                    Cell earthCell = new Cell(j,i, null,null);
                    // add earthCell on list of earth on board object
                    listCellContainsEarth.add(earthCell);
                    board.setListCellEarth(listCellContainsEarth);
                }
            }
        }
        // check
//         System.err.println("list earth on board " + listCellContainsEarth.toString());
    }
}