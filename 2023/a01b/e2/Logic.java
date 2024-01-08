package a01b.e2;

import java.util.*;

public interface Logic {
    public void hit(Coord c);
    public boolean isOver();
    public Map<Coord, Integer> selected();
}
