import java.util.*;
import java.util.stream.Collectors;

/**
 * Read the constant data of the map before the main loop, then read the state of the fire and give an action at each turn
 **/
class Player {
    public static int turn = 0;
    private static char FIRE = 'f';

    public static <Char> void main(String args[]) {
        // testeux
        Strat strat = new Strat();
        List<String> gridLines = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        int treeTreatmentDuration = in.nextInt(); // cooldown for cutting a "tree" cell
        System.err.println("tree treatment: " + treeTreatmentDuration);
        int treeFireDuration = in.nextInt(); // number of turns for the fire to propagate on adjacent cells from a "tree" cell
        int treeValue = in.nextInt(); // value lost if a "tree" cell is burnt or cut
        System.err.println("tree value: " + treeValue);
        int houseTreatmentDuration = in.nextInt(); // cooldown for cutting a "house" cell
        System.err.println("house treatment: " + houseTreatmentDuration);
        int houseFireDuration = in.nextInt(); // number of turns for the fire to propagate on adjacent cells from a "house" cell
        int houseValue = in.nextInt(); // value lost if a "house" cell is burnt or cut
        System.err.println("house value: " + houseValue);
        int width = in.nextInt(); // number of columns in the grid
        int height = in.nextInt(); // number of rows in the grid
        int fireStartX = in.nextInt(); // column where the fire starts
        int fireStartY = in.nextInt(); // row where the fire starts
        for (int i = 0; i < height; i++) {
            String gridLine = in.next();
            gridLines.add(gridLine);
        }

        Utils.createMap(gridLines, width, height);

        // find all node around house to safe
        List<Node> houses = Utils.allNodes.stream().filter(n -> n.isHouse()).collect(Collectors.toList());

        // take all around houses nodes to securize
        List<Node> allNotSecurizedListNode = new ArrayList<>();

        for (Node house: houses) {
            List<Node> notSecurizedListNode = Utils.findNodeAroundHouseNotSecureHouseOrOnFire(house);
            allNotSecurizedListNode.addAll(notSecurizedListNode);
        }
        Utils.aroundHouseToSafe = allNotSecurizedListNode;

        // game loop
        while (true) {
            turn++;
            MyVar.loop = turn;
            int cooldown = in.nextInt(); // number of turns remaining before you can cut a new cell
            MyVar.cooldown = cooldown;
            System.err.println("cooldown: " + cooldown);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int fireProgress = in.nextInt(); // state of the fire in this cell (-2: safe, -1: no fire, 0<=.<fireDuration: fire, fireDuration: burnt)
                    // update map
                    if (fireProgress >= 0) {
                        Utils.map[j][i] = FIRE;
                        // update fire on allNodes
                        final int x = j;
                        final int y = i;
                        List<Node> fire = Utils.allNodes.stream()
                                .filter(n-> n.getX() == x && n.getY() == y)
                                .collect(Collectors.toList());
                        if (!fire.isEmpty()) {
                            fire.get(0).setOnFire(true);
                            fire.get(0).setTimeOnFire(fireProgress);
                        }
                    }
                }
            }
            //Utils.displayMap();

            if (Utils.isHousesOnMap()) {
                System.out.println(strat.saveHouse());
            } else {
                System.out.println("WAIT");
            }




            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            // todo 1 -> trouver la maison la plus proche du feu
            // todo 2 -> calculer le plus court chemin entre la maison et le depart de feu
            // todo 3 -> couper la case la plus proche de la maison en direction du feu


            // WAIT if your intervention cooldown is not zero, else position [x] [y] of your intervention.
            //System.out.println("WAIT");
        }
    }
}