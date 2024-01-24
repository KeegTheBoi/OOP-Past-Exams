package a03a.e2;

import java.util.Map;


public interface Logic {

    public Map<Coord, String> getMap();

    public boolean hit(Coord c);

    public boolean isOver();
    
}
