import java.util.Comparator;

public class Kills_Comp implements Comparator<ConcretePiece> {  // the comparator of the kills
    @Override
    public int compare(ConcretePiece p1, ConcretePiece p2) {
        if (p1 instanceof King)
            return 1;
        if (p2 instanceof King)
            return -1;

        Pawn pawn1 = (Pawn)p1;
        Pawn pawn2 = (Pawn)p2;
            if (pawn1.getPieces_ate() > pawn2.getPieces_ate())
                return -1;
            else if (pawn1.getPieces_ate() == pawn2.getPieces_ate()) {  // if they eat the same amount
                if (pawn1.getOwner().isPlayerOne() == pawn2.getOwner().isPlayerOne()) {       //if from same team
                    if (Integer.parseInt(p1.getNumber().substring(1)) > Integer.parseInt(p2.getNumber().substring(1)))
                        return -1;
                    else
                        return 1;
                } else {
                    if (pawn1.getOwner().isPlayerOne() != GameLogic.player_two_wins)       //if p1 is the winner and player p2 is looser
                        return -1;
                    else
                        return 1;
                }
            }else{      //if pawn2 is bigger then pawm1
                return 1;
            }
    }

}