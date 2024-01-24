package a01a.e2;

import java.util.Set;

public interface Logic {
    public void hit(Coord c);
    public boolean isOver();
    public Set<Coord> getSelected();
}
