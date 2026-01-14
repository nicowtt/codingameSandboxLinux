import java.util.*;

class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        List<String> stringMap = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            String chessRow = in.nextLine();
            // remove space on line
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < chessRow.length(); j += 2) {
                row.append(chessRow.charAt(j));
            }
            stringMap.add(row.toString());
        }

        Utils.createMap(stringMap);
        Utils.createPieces();
        Utils.displayMap();
        Utils.displayPieces();
        Utils.pieces.forEach(Piece::updateMoveCells);

        if (Utils.isCheck()) {
            System.out.println("Check");
        } else {
            System.out.println("No Check");
        }
    }
}