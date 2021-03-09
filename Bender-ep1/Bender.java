import java.util.ArrayList;
import java.util.List;

class Bender extends Cell {

    private String actualDirection;
    private boolean drunk;
    private Boolean sens;
    private List<String> directions;
    private List<Move> moves;
    private List<Cell> teleports;
    private boolean benderIsOnDeathCabin;
    private Cell nextCell;


    public Bender(int x, int y) {
        super(x, y);
        this.actualDirection = "SOUTH";
        this.sens = true;
        this.benderIsOnDeathCabin = false;
        this.drunk = false;
        teleports = new ArrayList<>();
        directions = new ArrayList<>();
    }

    public List<String> getDirections() {
        return directions;
    }

    public boolean isBenderIsOnDeathCabin() {
        return benderIsOnDeathCabin;
    }

    public void addTeleportsCell(Cell teleport) {
        this.teleports.add(teleport);
    }

    public void clearDirection() {
        this.directions.clear();
    }

    public void findNextDirection(char[][] map) {
        this.calcMoves();

        if (benderIsGuidedForMove(map))
            return;

        this.nextCell = this.findNextCell();

        if (!benderTryToMoveOnSameDirection(map))
            benderMoveToNextDirection(map);
    }

    private boolean benderTryToMoveOnSameDirection(char[][] map) {
        if (map[nextCell.getX()][nextCell.getY()] == ' ') {
            Move move = new Move(nextCell.getX(), nextCell.getY(), actualDirection);
            move(move);
            return true;
        }
        return false;
    }

    private void benderMoveToNextDirection(char[][] map) {
        this.actualDirection = sens ? "SOUTH" : "WEST";
        nextCell = this.findNextCell();
        while (map[nextCell.getX()][nextCell.getY()] != ' ') {

            if (benderIsGuidedForMove(map))
                return;

            this.actualDirection = this.nextDirection();
            nextCell = this.findNextCell();

        }
        Move move = new Move(nextCell.getX(), nextCell.getY(), actualDirection);
        move(move);
    }

    private boolean benderIsGuidedForMove(char[][] map) {
        for (Move move : moves) {
            char nextMove = map[move.getX()][move.getY()];

            if (nextMove == '$' && actualDirection.equals(move.getDirection())) {
                this.setX(move.getX());
                this.setY(move.getY());
                this.actualDirection = move.getDirection();
                move(move);
                this.benderIsOnDeathCabin = true;
                return true;
            }
            else if (nextMove == 'B' && actualDirection.equals(move.getDirection())) {
                move(move);
                drunk = !this.drunk;
                return true;
            }
            else if (nextMove == 'T' && actualDirection.equals(move.getDirection())) {
                Cell nextTeleportCell = this.getOtherTeleportCell(move);
                this.setX(nextTeleportCell.getX());
                this.setY(nextTeleportCell.getY());
                directions.add(move.getDirection());
                return true;
            }
            else if (nextMove == 'I' && actualDirection.equals(move.getDirection())) {
                move(move);
                sens = !sens;
                return true;
            }
            else if (nextMove == 'X' && drunk && actualDirection.equals(move.getDirection())) {
                move(move);
                map[move.getX()][move.getY()] = ' ';
                return true;
            }
            else if (nextMove == 'E' && actualDirection.equals(move.getDirection())) {
                move(move);
                this.actualDirection = "EAST";
                return true;
            }
            else if (nextMove == 'W' && actualDirection.equals(move.getDirection())) {
                move(move);
                this.actualDirection = "WEST";
                return true;
            }
            else if (nextMove == 'N' && actualDirection.equals(move.getDirection())) {
                move(move);
                this.actualDirection = "NORTH";
                return true;
            }
            else if (nextMove == 'S' && actualDirection.equals(move.getDirection())) {
                move(move);
                this.actualDirection = "SOUTH";
                return true;
            }
        }
        return false;
    }

    private void move(Move move) {
        this.setX(move.getX());
        this.setY(move.getY());
        directions.add(move.getDirection());
        this.actualDirection = move.getDirection();
    }

    private Cell getOtherTeleportCell(Move move) {
        Cell moveCell = new Cell(move.getX(), move.getY());
        for (Cell cell : this.teleports) {
            if (!cell.equals(moveCell))
                return cell;
        }
        return null;
    }

    private void calcMoves() {
        moves = new ArrayList<>();
        if (sens) {
            moves.add(new Move(this.getX(), this.getY() + 1, "SOUTH"));
            moves.add(new Move(this.getX() + 1, this.getY(), "EAST"));
            moves.add(new Move(this.getX(), this.getY() - 1, "NORTH"));
            moves.add(new Move(this.getX() - 1, this.getY(), "WEST"));
        } else {
            moves.add(new Move(this.getX() - 1, this.getY(), "WEST"));
            moves.add(new Move(this.getX(), this.getY() - 1, "NORTH"));
            moves.add(new Move(this.getX() + 1, this.getY(), "EAST"));
            moves.add(new Move(this.getX(), this.getY() + 1, "SOUTH"));
        }
    }

    private String nextDirection() {
        if (this.actualDirection.equals("SOUTH"))
            return sens ? "EAST" : "WEST";

        if (this.actualDirection.equals("EAST"))
            return sens ? "NORTH" : "SOUTH";

        if (this.actualDirection.equals("NORTH"))
            return sens ? "WEST" : "EAST";

        // west
        return sens ? "SOUTH" : "NORTH";
    }

    private Cell findNextCell() {
        if (actualDirection.equals("SOUTH"))
            return new Cell(this.getX(), this.getY() + 1);

        if (actualDirection.equals("EAST"))
            return new Cell(this.getX() + 1, this.getY());

        if (actualDirection.equals("NORTH"))
            return new Cell(this.getX(), this.getY() - 1);

        // west
        return new Cell(this.getX() - 1, this.getY());
    }
}
