package a02b.e2;

import java.util.Set;

public interface Model {
    public boolean hit(int x, int y);
    public Set<Pair<Integer, Integer>> getSelectedPos();
}
