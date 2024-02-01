import java.util.ArrayList;

public class King extends ConcretePiece{
    public King(Player p){
        this.player = p;
        if (p.isPlayerOne())
            type = "♛";
        else
            type = "♕";
        Moves = new ArrayList<>();
    }
    //count the pieces or board limits sarounding this current player and returns true if it is sarounded all around(if sarround = 4)
    public boolean is_captured(int side){
        int sarrounded = 0;
        if (this.borders() != 0)
            sarrounded++;

        Position check_corner;
        if (this.borders() != 1){       //not a border above
            check_corner = new Position(this.current_position().getX(),this.current_position().getY()-1);
            if (check_corner.is_corner())       //corner above
                sarrounded++;
            if (this.get_over() instanceof Pawn && this.getOwner().isPlayerOne() != this.get_over().getOwner().isPlayerOne())   //enemy pawn above
                sarrounded++;
        }
        if (this.borders() != 2){
            check_corner = new Position(this.current_position().getX()+1,this.current_position().getY());
            if (check_corner.is_corner())       //corner on its right
                sarrounded++;
            if (this.get_right() instanceof Pawn && this.getOwner().isPlayerOne() != this.get_right().getOwner().isPlayerOne()) //pawn above
                sarrounded++;
        }
        if (this.borders() != 3){
            check_corner = new Position(this.current_position().getX(),this.current_position().getY()+1);
            if (check_corner.is_corner())       //corner under
                sarrounded++;
            if (this.get_under() instanceof Pawn && this.getOwner().isPlayerOne() != this.get_under().getOwner().isPlayerOne()) //pawn above
                sarrounded++;
        }
        if (this.borders() != 4){
            check_corner = new Position(this.current_position().getX()-1,this.current_position().getY());
            if (check_corner.is_corner())       //corner on its left
                sarrounded++;
            if (this.get_left() instanceof Pawn && this.getOwner().isPlayerOne() != this.get_left().getOwner().isPlayerOne())
                sarrounded++;
        }
        return sarrounded == 4;     //if captured then will return true
    }
}
