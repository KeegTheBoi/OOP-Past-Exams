package a03a.e2;

import java.util.*;

public interface Model {

    public enum Movement {HUMAN, COMPUTER}
    public void initTable();
    public void computermove();
    public boolean humanmove(int x, int y);
    public Map<Movement, Pair<Integer, Integer>> getMapperTable();
    public boolean isOver();
    
}
