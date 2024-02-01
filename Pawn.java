import java.util.ArrayList;
public class Pawn extends ConcretePiece{
    private int killes;
    public Pawn(Player p){
        this.player = p;
        killes = 0;
        if (p.isPlayerOne())
            type = "♟";
        else
            type = "♙";
        Moves = new ArrayList<>();
    }
    //count the pieces or board limits sarounding this current player and returns true if it is sarounded by
    //the player which attacked him(side) and the other side
    @Override
    public boolean is_captured(int side){   //side is the side in which this player was attacked
        Position check_corner;
        if (side == this.borders()){
            return true;
        }
        if (side == 1){
            check_corner = new Position(this.current_position().getX(),this.current_position().getY()-1);
            if ((this.get_over() instanceof Pawn && this.getOwner().isPlayerOne() != this.get_over().getOwner().isPlayerOne()) || check_corner.is_corner())
                 return true;       //captured from above
        }
        if(side == 2){
            check_corner = new Position(this.current_position().getX()+1,this.current_position().getY());
            if ((this.get_right() instanceof Pawn && this.getOwner().isPlayerOne() != this.get_right().getOwner().isPlayerOne()) || check_corner.is_corner())
                return true;
        }
        if(side == 3) {
            check_corner = new Position(this.current_position().getX() , this.current_position().getY()+1);
            if ((this.get_under() instanceof Pawn && this.getOwner().isPlayerOne() != this.get_under().getOwner().isPlayerOne()) || check_corner.is_corner())
                return true;
        }
        if (side == 4) {
            check_corner = new Position(this.current_position().getX()-1, this.current_position().getY());
            if ((this.get_left() instanceof Pawn && this.getOwner().isPlayerOne() != this.get_left().getOwner().isPlayerOne()) || check_corner.is_corner())
                return true;
        }
        return false;
    }
    public void set_amount_ate(){
        this.killes++;
    }
    public int getPieces_ate(){
        return this.killes;
    }
}
