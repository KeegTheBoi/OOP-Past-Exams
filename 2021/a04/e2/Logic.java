package a04.e2;

import java.util.Map;


public interface Logic {
    
    void hit(Coord c);

    int getResult();

    Map<Coord, Operator> getMap();
}
