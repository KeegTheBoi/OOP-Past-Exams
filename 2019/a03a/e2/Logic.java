package a03a.e2;

import java.util.*;

public interface Logic {
	public enum Type {
		Moving("X"), Stationary("O");
		
		private String symbol;
		
		public String getSymbol() {
			return this.symbol;
		}
		
		private Type(final String symb) {
			this.symbol = symb;
		}
	}
	
	public void hit(Coord c);
	public boolean isOver();
	public Map<Coord, Type> getMap();
	public void move();
}
