import java.util.ArrayList;

public abstract class ConcretePiece implements Piece{
    protected ArrayList<Position> Moves = new ArrayList<>(); //tracks the moves of each player
    public static ConcretePiece[][] board;  //for easy access to board from class
    private int squares;    //counts the number of squares this piece has moved
    protected Player player;
    protected String type;
    private String piece_number;
    public abstract boolean is_captured(int side);  //checks if pawn/king is captured
    public int borders(){
        if (current_position().getY() == 0)
            return 1;   //piece at the top of the board
        if (current_position().getX() == board.length - 1)
            return 2;   //piece at the right of the board
        if (current_position().getY() == board.length-1)
            return 3;   //piece at the bottom of the board
        if (current_position().getX() == 0)
            return 4;   //piece at the left of the board
        return 0;       //piece not in the sides
    }
    public void setNumber(String n){
        this.piece_number = n;
    }
    public String getNumber(){
        return this.piece_number;
    }
    public void add_squares(int a){
        this.squares = this.squares + a;
    }
    public int getSquares(){
        return this.squares;
    }
    public Position current_position(){return Moves.get(Moves.size()-1);}
    public ConcretePiece get_over(){return board[this.current_position().getX()][this.current_position().getY()-1];}
    public ConcretePiece get_right(){return board[this.current_position().getX()+1][this.current_position().getY()];}
    public ConcretePiece get_under(){return board[this.current_position().getX()][this.current_position().getY()+1];}
    public ConcretePiece get_left(){return board[this.current_position().getX()-1][this.current_position().getY()];}

    public String get_moves(){
        return Moves.toString();
    }
    @Override
    public Player getOwner() {
        return this.player;
    }

    @Override
    public String getType() {
        return type;
    }
}
