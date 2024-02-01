import java.util.Comparator;

public class Stood_Comp implements Comparator<Position> {
    @Override
    public int compare(Position p1, Position p2) {
        if(p1.get_Stepped_on() > p2.get_Stepped_on())
            return -1;
        else if (p1.get_Stepped_on() == p2.get_Stepped_on()){
            if (p1.getX() > p2.getX()){
                return 1;
            } else if (p1.getX() < p2.getX()) {
                return -1;
            }
            else{       // x is equal, check y
                if (p1.getY() > p2.getY()){
                    return 1;
                }
                else
                    return -1;
            }
        }
        else
            return 1;
    }
}
