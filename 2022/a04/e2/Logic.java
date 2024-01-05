package a04.e2;

import java.util.*;

public interface Logic {

    public Optional<Coord> hit(Coord c);
    public boolean isOver();
    public Pair<Coord, Coord> getPlayers();
} 
