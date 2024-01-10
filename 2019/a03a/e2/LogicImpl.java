package a03a.e2;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public class LogicImpl implements Logic {
	private enum Direction {
		UP(c -> new Coord(c.x(), c.y() -1)), 
		RIGHT(c -> new Coord(c.x() + 1, c.y())),
		DOWN(c -> new Coord(c.x(), c.y() + 1)),
		LEFT(c -> new Coord(c.x() - 1, c.y()));
		
		private final UnaryOperator<Coord> oper;
		
		private Direction(final UnaryOperator<Coord> oper) {
			this.oper = oper;
		}
		
		public Coord move(Coord c) {
			return this.oper.apply(c);
		}
		
		public Direction next() {
			return Direction.values()[(this.ordinal() + 1) % values().length];
		}
	}
	private final int size;
	private final Map<Coord, Type> map;
	private Direction dire = Direction.UP;
	private Coord moving = new Coord(0, 0);
	
	public LogicImpl(final int size) {
		this.size = size;
		this.map = new HashMap<>();
		map.put(this.moving, Type.Moving);
	}
	
	private boolean isLimit(Coord c) {
		return (c.x() == 0 && c.y() == 0) || (c.x() == size - 1 && c.y() == size - 1)
			|| (c.x() == 0 && c.y() == size - 1) || (c.x() == size - 1  && c.y() == 0);
	}
	
	public void hit(Coord c) {
		this.map.put(c, Type.Stationary);
	}
	
	public void move() {
		this.map.remove(this.moving);
		this.moving = getNext();
		Optional.of(this.map).filter(g -> !this.isOver()).ifPresent(m -> m.put(this.moving, Type.Moving));
	}
	
	private Coord getNext() {
		return Optional.of(this.moving).filter(Predicate.not(this::isLimit)).or(() -> {dire = dire.next(); return Optional.of(this.moving);}).map(dire::move).get();
	}
	
	public boolean isOver() {
		return this.map.entrySet().stream().filter(e -> e.getValue().equals(Type.Stationary)).anyMatch(k -> k.getKey().equals(this.moving));
	}
	
	public Map<Coord, Type> getMap() {
		return this.map;
	}
}
