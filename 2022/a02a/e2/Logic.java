package a02a.e2;

import java.util.Set;

public interface Logic {
    public Set<Coord> hit(Coord c);
    public boolean isOVer();
    public Set<Coord> getSelected();
}
