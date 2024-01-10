package a03b.e2;
import java.util.*;

public record Coord (int x, int y) {
	
	private static Random rand = new Random();
		
	public static Coord moveDown(final Coord c) {
		return new Coord(c.x(), c.y() + 1);
	}
	
	public static Coord randomCoord(final int bound) {
		return new Coord(rand.nextInt(bound), 0);
	}
}
