package a03b.e2;

import java.util.Map;

public interface Modello {
    public static enum Player {COMPUTER, HUMAN, EMPTY}
    public void initMap();
    public boolean hit(int x, int y);
    public Map<Pair<Integer, Integer>, Player> getMapper();
}
