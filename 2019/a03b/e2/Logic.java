package a03b.e2;

import java.util.*;

public interface Logic {
	
	public enum State {
		FREE, DEPOSITED
	}
	
	public Map<Coord, State> getMap();
	public void move();
	public boolean isOver();
}
