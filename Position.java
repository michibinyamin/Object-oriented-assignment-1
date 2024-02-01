public class Position {
    private int stepped_on; //for counting times stepped on
    private int x;
    private int y;
    public Position(int x, int y){
        this.x = x;
        this.y = y;
        stepped_on = 0;
    }
    public int getX(){return this.x;}
    public int getY(){return this.y;}
    public boolean is_corner(){
        return ((x==0 || x==10)&&(y==0 || y==10));
    }   //true if this current piece a corner
    public void set_Stepped_on(){
        stepped_on++;
    }
    public int get_Stepped_on(){
        return stepped_on;
    }
    @Override
    public String toString(){
        return "("+x+", "+y+")";
    }
}