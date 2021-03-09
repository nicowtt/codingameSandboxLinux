
import java.util.*;
import java.util.stream.Collectors;

class Solution {

    private char[][] map;
    private Bender bender;

    public static void main(String args[]) {
        new Solution().run();
    }

    public void run() {
        int count = 0;
        Scanner in = new Scanner(System.in);
        int L = in.nextInt();
        int C = in.nextInt();
        map = new char[C][L];
        List<String> lines = new ArrayList<>();

        if (in.hasNextLine())
            in.nextLine();

        for (int i = 0; i < L; i++)
            lines.add(in.nextLine());

        this.createMap(lines);
        this.getTeleportCells(map);

        this.displayMap(map);

        while (!bender.isBenderIsOnDeathCabin()) {
            count++;
            bender.findNextDirection(map);

            if (count > 500) {
                System.out.println("LOOP");
                bender.clearDirection();
                break;
            }
        }
        System.out.println(this.formatReponse(bender.getDirections()));
    }

    public void createMap(List<String> lines) {
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                map[x][y] = lines.get(y).charAt(x);
                if (map[x][y] == '@')
                    bender = new Bender(x,y);
            }
        }
    }

    public void displayMap(char[][] map) {
        String line = "";
        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                line += map[x][y];
            }
            System.err.println(line);
            line = "";
        }
    }

    public void getTeleportCells(char[][] map) {
        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                if (map[x][y] == 'T') {
                    Cell teleportCell = new Cell(x, y);
                    bender.addTeleportsCell(teleportCell);
                }
            }
        }
    }

    public String formatReponse(List<String> mvt){
        return mvt.stream().collect(Collectors.joining("\n"));
    }
}
