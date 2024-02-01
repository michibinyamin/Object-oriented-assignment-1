import java.util.Comparator;

public class Squares_Comp implements Comparator<ConcretePiece> {
    @Override
    public int compare(ConcretePiece p1, ConcretePiece p2) {
        if (p1.getSquares() > p2.getSquares()){
            return -1;
        }
        else if (p1.getSquares() == p2.getSquares()) {
            if (p1.getOwner().isPlayerOne() == p2.getOwner().isPlayerOne()) {
                if (Integer.parseInt(p1.getNumber().substring(1)) > Integer.parseInt(p2.getNumber().substring(1)))
                    return 1;
                else
                    return -1;
            }
            else{
                    if (p1.getOwner().isPlayerOne() != GameLogic.player_two_wins)       //if p1 is the winner and player p2 is looser
                        return 1;
                    else
                        return -1;
                }
            }
        else
            return 1;
    }
}
