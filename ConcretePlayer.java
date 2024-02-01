public class ConcretePlayer implements Player{
    boolean isPlayerOne;
    private int wins = 0;

    ConcretePlayer(boolean b){
        this.isPlayerOne = b;
    }
    public void setWins(){
        wins++;
    }
    @Override
    public boolean isPlayerOne() {
        return this.isPlayerOne;
    }   //playerone = defence = true

    @Override
    public int getWins() {
        return wins;
    }
}
