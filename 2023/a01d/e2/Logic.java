package a01d.e2;
import java.util.*;

public interface Logic {
    public void hit(Coord c);
    public boolean isOver();
    public Set<Coord> getSquare();
}
