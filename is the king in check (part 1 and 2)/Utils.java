import java.util.ArrayList;
import java.util.List;

abstract class Utils {

    public static char[][] map = new char[8][8];
    public static List<Piece> pieces = new ArrayList<>();

    // methods
    public static void createMap(List<String> lines) {
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(y).length(); x++) {
                if (lines.get(y).charAt(x) == '_') {
                    map[x][y] = '.';
                } else {
                    map[x][y] = lines.get(y).charAt(x);
                }
            }
        }
    }

    public static void createPieces() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (map[x][y] != '.') {
                    char type = map[x][y];
                    switch (type) {
                        case 'R': {
                            pieces.add(new Rook(type, new Cell(x,y)));
                            break;
                        }
                        case 'K':
                        case 'k': {
                            pieces.add(new King(type, new Cell(x,y)));
                            break;
                        }
                        case 'B': {
                            pieces.add(new Bishop(type, new Cell(x,y)));
                            break;
                        }
                        case 'Q': {
                            pieces.add(new Queen(type, new Cell(x,y)));
                            break;
                        }
                        case 'N': {
                            pieces.add(new Knight(type, new Cell(x,y)));
                            break;
                        }
                    }
                }
            }
        }
    }

    public static void displayMap() {
        String line = "";
        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                line += map[x][y];
            }
            System.err.println(line);
            line = "";
        }
    }

    public static void displayPieces() {
        pieces.forEach(p -> {
            System.err.println(p.type + " " + p.pos.x + " " + p.pos.y);
        });
    }

    public static boolean isCheck() {
        Piece king = pieces.stream().filter(p -> p.type == 'K' || p.type == 'k').findFirst().orElseThrow();

        return pieces.stream()
            .flatMap(p -> p.moveCells.stream())
            .anyMatch(cell -> cell.equals(king.pos));
    }



    public static void printListCell(List<Cell> cells) {
        cells.forEach(c -> {
            System.err.println(c.x + " " + c.y);
        });
    }
}
