import javax.lang.model.type.NullType;
import java.awt.List;
import java.util.*;

public class GameLogic implements PlayableLogic{
    ConcretePlayer attacker = new ConcretePlayer(false);
    ConcretePlayer defender = new ConcretePlayer(true);
    boolean is_second_player;   //declares who's turn it is true = second player , false = first player
    static boolean  player_two_wins;    //checks who wins
    ConcretePiece[][] board;     //the board
    ConcretePiece[] pieces;     //list of all the pieces
    King king;
    Position[][] positions;  //counts for each position amount of pieces stepped
    GameLogic(){             //initialize board and players
        reset();
    }   //when constructor called, rest game
    @Override
    public boolean move(Position a, Position b) {
        if (is_legal_move(a,b)){
            is_second_player = !is_second_player;   //change player's turn
            board[b.getX()][b.getY()] = board[a.getX()][a.getY()];  //copy piece to new position
            board[a.getX()][a.getY()] = null;  //delete peace from old position
            if (!board[b.getX()][b.getY()].get_moves().contains("("+b.getX()+", "+b.getY()+")")) {
                positions[b.getX()][b.getY()].set_Stepped_on();    //add 1 to positions(counting stepped on)
            }
            board[b.getX()][b.getY()].Moves.add(b);     //add a new position to the list
            if (a.getX() == b.getX())
                board[b.getX()][b.getY()].add_squares(Math.abs(a.getY() - b.getY()));   //add amount of squares to piece
            else
                board[b.getX()][b.getY()].add_squares(Math.abs(a.getX() - b.getX()));

            if (board[b.getX()][b.getY()] instanceof Pawn pawn){        //piece moved is pown
                ate(pawn);      //eats if needed
            }
            if(isGameFinished()) {    //checks if king captured or king reaches corner
                print_game();   //print stats
            }
            return  true;
        }
        return false;
    }
    public boolean is_legal_move(Position a, Position b) {   //returns true if move is legal
        boolean correct_turn = board[a.getX()][a.getY()].getOwner().isPlayerOne() == !is_second_player;     //xnor gate
        if (!correct_turn){
            return false;
        }
        //check that a pawn does not land on the corners
        if ((board[a.getX()][a.getY()] instanceof Pawn) && b.is_corner())
            return false;
        //check that piece did not jump over another piece +
        //moves in straight line +
        //position b is clear
        if (a.getX() == b.getX() && a.getY() == b.getY())   //if pressed the same position
            return false;

        if (a.getX() == b.getX()) {      //moves in straight line on x
            if (a.getY() < b.getY()) {
                for (int i = a.getY() + 1; i <= b.getY(); i++) {
                    if (board[a.getX()][i] != null)
                        return false;
                }
            } else {
                for (int i = a.getY() - 1; i >= b.getY(); i--) {
                    if (board[a.getX()][i] != null)
                        return false;
                }
            }
        }

        else if (a.getY() == b.getY()) {    //moves in straight line on y
            if (a.getX() < b.getX()) {
                for (int i = a.getX() + 1; i <= b.getX(); i++) {
                    if (board[i][a.getY()] != null)
                        return false;
                }
            } else {
                for (int i = a.getX() - 1; i >= b.getX(); i--) {
                    if (board[i][a.getY()] != null)
                        return false;
                }
            }
        }
        else {        //if peace not moved in straight line then false
            return false;
        }

        return true;    //if nothing from the above happand then path + position b is clear and the move was straight
    }
    public void ate(Pawn piece){    //check if piece has eaten
        if ((piece.borders() != 1) && piece.get_over() != null){        //no border over && piece over
            if(piece.getOwner().isPlayerOne() != piece.get_over().getOwner().isPlayerOne()) {        //oposite player over
                if(captured(piece.get_over(), 1))        //check and set piece over is captured
                    piece.set_amount_ate();
            }
        }
        if ((piece.borders() != 2) && piece.get_right() != null) {        //no border on its right && piece in right
            if (piece.getOwner().isPlayerOne() != piece.get_right().getOwner().isPlayerOne()) {        //oposite player in right
                if (captured(piece.get_right(), 2))
                    piece.set_amount_ate();
            }
        }
        if ((piece.borders() != 3) && piece.get_under() != null) {        //no border under && piece under
            if (piece.getOwner().isPlayerOne() != piece.get_under().getOwner().isPlayerOne()) {        //oposite player under
                if (captured(piece.get_under(), 3))
                    piece.set_amount_ate();
            }
        }
        if ((piece.borders() != 4) && piece.get_left() != null) {        //no border on its left && piece on left
            if (piece.getOwner().isPlayerOne() != piece.get_left().getOwner().isPlayerOne()) {        //oposite player in left
                if (captured(piece.get_left(), 4))
                    piece.set_amount_ate();
            }
        }
    }
    public boolean captured(ConcretePiece p, int side) {     //this method checkes if this peace is captured, side indicates the side in which it was attacked
        if (p instanceof Pawn) {
            if (p.is_captured(side)) {
                board[p.current_position().getX()][p.current_position().getY()] = null;
                return true;
            }
            return false;
        }
        return false;
    }
    @Override
    public Piece getPieceAtPosition(Position position) {
        return board[position.getX()][position.getY()];
    }
    @Override
    public Player getFirstPlayer() {return defender;}

    @Override
    public Player getSecondPlayer() {return attacker;}

    @Override
    public boolean isGameFinished() {
        //defender wins
        boolean game_fineshed = false;
        if (this.king.current_position().is_corner()) {
            defender.setWins();
            player_two_wins = false;    //for comparator
            return true;
        }
        //attacker wins
        else if (this.king.is_captured(0)) {
            attacker.setWins();
            player_two_wins = true;     //for comparator
            return true;
        }
        //no one wins yet
        else {
            return false;
        }
    }
    public void print_game(){
            Collections.sort(Arrays.asList(pieces), new Position_Comp());  // the comarator of pieces
            for (int i = 0; i < pieces.length; i++) {
                if(pieces[i].Moves.size()>1) {
                    System.out.println(pieces[i].getNumber() + ": " + pieces[i].get_moves());   //print
                }
            }

            for (int i = 0; i < 75; i++) {
                System.out.print("*");
            }
            System.out.println();
            Collections.sort(Arrays.asList(pieces), new Kills_Comp());  // the comarator of pieces
            for (int i = 0; i < pieces.length; i++) {
               if (pieces[i] instanceof Pawn pawn && pawn.getPieces_ate() !=0 )
                    System.out.println(pawn.getNumber()+": "+pawn.getPieces_ate()+" kills");
            }

            for (int i = 0; i < 75; i++) {
                System.out.print("*");
            }
            System.out.println();
            Collections.sort(Arrays.asList(pieces), new Squares_Comp());  // the comparator of pieces
            for (int i = 0; i < pieces.length; i++) {
                if (pieces[i].getSquares() != 0)
                    System.out.println(pieces[i].getNumber()+": "+pieces[i].getSquares()+" squares");
            }

            for (int i = 0; i < 75; i++) {
                System.out.print("*");
            }

            System.out.println();
            Position[] oneDArray = Arrays.stream(positions).flatMap(Arrays::stream).toArray(Position[]::new);  //turnes two dimentional array to one dimentional
            Collections.sort(Arrays.asList(oneDArray), new Stood_Comp());  // the comarator of pieces
            for (int i = 0; i < oneDArray.length; i++) {
                if (oneDArray[i].get_Stepped_on() > 1)
                    System.out.println(oneDArray[i].toString() + oneDArray[i].get_Stepped_on()+" pieces");
            }

            for (int i = 0; i < 75; i++) {
                System.out.print("*");
            }
            System.out.println();
    }

    @Override
    public boolean isSecondPlayerTurn() {
        return is_second_player;
    }

    @Override
    public void reset() {
        //initialize board
        is_second_player = true;   //attacker player starts
        board = new ConcretePiece[getBoardSize()][getBoardSize()];  //resets the board
        ConcretePiece.board = this.board;       //not sure yet
        positions = new Position[getBoardSize()][getBoardSize()];    //resets the positions
        pieces = new ConcretePiece[37];     //resets the list of pieces
        //init attacker positions
        for(int i = 3; i<=7; i++){
            board[i][0] = new Pawn(attacker);
            board[0][i] = new Pawn(attacker);
        }
        board[5][1] = new Pawn(attacker);
        board[1][5] = new Pawn(attacker);

        for (int i = 3; i <= 7; i++) {
            board[i][10] = new Pawn(attacker);
            board[10][i] = new Pawn(attacker);
        }
        board[5][9] = new Pawn(attacker);
        board[9][5] = new Pawn(attacker);

        //init defender positions
        for (int i = 4; i <= 6; i++) {
            for (int j = 4; j <= 6; j++) {
                if( !(i == 5 && j==5) )
                    board[j][i] = new Pawn(defender);
                else{
                    this.king = new King(defender);
                    board[j][i] = this.king;
                }

            }
        }
        board[5][3] = new Pawn(defender);
        board[3][5] = new Pawn(defender);
        board[7][5] = new Pawn(defender);
        board[5][7] = new Pawn(defender);

        //init player's type
        int i = 0;
        int countAttack = 1;
        int countDefence = 1;
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board.length; x++) {
                positions[x][y] = new Position(x,y);  //count the positions
                if(board[x][y]!=null) {                             //make sure there is a player here
                    if (board[x][y].getOwner().isPlayerOne()) {     //set defender type
                        if(board[x][y] instanceof King) {
                            board[x][y].setNumber("K" + countDefence);
                        }else {
                            board[x][y].setNumber("D" + countDefence);
                        }
                        countDefence++;
                    } else {                                        //set attacker type
                        board[x][y].setNumber("A" + countAttack);
                        countAttack++;
                    }
                    pieces[i] = board[x][y];    //piece's list has a pointer to the same pieces as in the board
                    i++;
                    board[x][y].Moves.add(new Position(x,y));   //adds current position to arraylist
                    positions[x][y].set_Stepped_on();
                }
            }
        }
    }
    @Override
    public void undoLastMove() {
        //stack of boards ,deep copy
    }
    @Override
    public int getBoardSize() {
        return 11;
    }
}
