import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract class Moves {

    public static List<Cell> rookMove(char[][] map, Cell pos) {
        List<Cell> moveCells = new ArrayList<>();
        // x+ position cell
        if (pos.x + 1 < 8) {
            for (int x = pos.x + 1 ; x < 8 ; x++) {
                if (map[x][pos.y] == '.') {
                    moveCells.add(new Cell(x,pos.y));
                } else {
                    moveCells.add(new Cell(x,pos.y));
                    break;
                }
            }
        }

        // x- position cell
        if (pos.x - 1 >= 0) {
            for (int x = pos.x -1; x >= 0 ; x--) {
                if (map[x][pos.y] == '.') {
                    moveCells.add(new Cell(x,pos.y));
                } else {
                    moveCells.add(new Cell(x,pos.y));
                    break;
                }
            }
        }

        // Y+ position cell
        if (pos.y + 1 < 8) {
            for (int y = pos.y + 1 ; y < 8 ; y++) {
                if (map[pos.x][y] == '.') {
                    moveCells.add(new Cell(pos.x,y));
                } else {
                    moveCells.add(new Cell(pos.x,y));
                    break;
                }
            }
        }

        // Y- position cell
        if (pos.y - 1 >= 0) {
            for (int y = pos.y - 1 ; y >= 0 ; y--) {
                if (map[pos.x][y] == '.') {
                    moveCells.add(new Cell(pos.x,y));
                } else {
                    moveCells.add(new Cell(pos.x,y));
                    break;
                }
            }
        }
        return moveCells;
    }

    public static List<Cell> bishopMoves(char[][] map, Cell pos) {
        List<Cell> moveCells = new ArrayList<>();

        int range = 1;

        // x+ y- position cell
        if (pos.x + range < 8) {
            for (int x = pos.x + range; x < 8; x++) {
                int y = pos.y + range;
                if (y < 8) {
                    if (map[x][y] == '.') {
                        moveCells.add(new Cell(x, y));
                    } else {
                        moveCells.add(new Cell(x, y));
                        break;
                    }
                    range++;
                }
            }
        }
        range = 1;


        // x- y- position cell
        if (pos.x - range >= 0) {
            for (int x = pos.x - range ; x >= 0 ; x--) {
                int y = pos.y + range;
                if (y < 8) {
                    if (map[x][y] == '.') {
                        moveCells.add(new Cell(x,y));
                    } else {
                        moveCells.add(new Cell(x,y));
                        break;
                    }
                    range++;
                }
            }
        }
        range = 1;

        // x+ y- position cell
        if (pos.x + 1 < 8) {
            for (int x = pos.x + range ; x < 8 ; x++) {
                int y = pos.y - range;
                if (y >= 0) {
                    if (map[x][y] == '.') {
                        moveCells.add(new Cell(x,y));
                    } else {
                        moveCells.add(new Cell(x,y));
                        break;
                    }
                    range++;
                }
            }
        }
        range = 1;

        // x- Y- position cell
        if (pos.x - 1 >= 0) {
            for (int x = pos.x - range ; x >= 0 ; x--) {
                int y = pos.y - range;
                if (y >= 0) {
                    if (map[x][y] == '.') {
                        moveCells.add(new Cell(x,y));
                    } else {
                        moveCells.add(new Cell(x,y));
                        break;
                    }
                    range++;
                }
            }
        }
        return moveCells;
    }

    public static List<Cell> queenMoves(char[][] map, Cell pos) {
        List<Cell> rookMoves = rookMove(map, pos);
        List<Cell> bishopMoves = bishopMoves(map, pos);

        return Stream.concat(rookMoves.stream(), bishopMoves.stream())
            .collect(Collectors.toList());
    }
    
    public static List<Cell> knightMoves(char[][] map, Cell pos) {
        List<Cell> moveCells = new ArrayList<>();

        // x+ y+
        int rangeX = 1;
        int rangeY = 2;

        for (int i = 0; i < 2; i++) {
            if (pos.x + rangeX < 8) {
                if (pos.y + rangeY < 8 && pos.y + rangeY >= 0) {
                    moveCells.add(new Cell(pos.x + rangeX,pos.y + rangeY));
                }
                if (pos.y -rangeY < 8 && pos.y + rangeY >= 0) {
                    moveCells.add(new Cell(pos.x + rangeX,pos.y - rangeY));
                }
            }

            if (pos.x - rangeX >= 0) {
                if (pos.y + rangeY < 8 && pos.y + rangeY >= 0) {
                    moveCells.add(new Cell(pos.x - rangeX,pos.y + rangeY));
                }
                if (pos.y -rangeY < 8 && pos.y + rangeY >= 0) {
                    moveCells.add(new Cell(pos.x - rangeX,pos.y - rangeY));
                }
            }
            rangeX = 2;
            rangeY = 1;
        }
        return moveCells;
    }
}
