package a05.e2;

import java.util.Map;

public interface Logic {
    public void hit(Coord c);
    public boolean isOver();
    public Map<Coord, Object> getMap();
}
