package a01b.e2;

import java.util.Set;

public interface Logic{
    Set<Coord> hit(Coord c);
    boolean isOver();
}