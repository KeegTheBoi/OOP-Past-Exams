package a01a.e2;

import java.util.Map;

public interface Logic {
    enum Component {
        FISRT, SECOND, ANY
    }

    void hit(Coord c);
    boolean isOver();
    Map<Coord, Component> getMap();
}
