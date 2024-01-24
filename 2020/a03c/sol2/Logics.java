package a03c.sol2;

import java.util.List;

public interface Logics{
	
	List<Pair<Integer,Integer>> getPositions();
	
	boolean hit(Pair<Integer,Integer> position);
	
	boolean next();
	
}
