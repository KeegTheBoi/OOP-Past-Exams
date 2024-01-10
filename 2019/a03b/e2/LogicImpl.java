package a03b.e2;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class LogicImpl implements Logic {
	private final int size;
	private final Map<Coord, State> map;
	private final Random rand = new Random();
	private Coord free;
	
	public LogicImpl(final int size) {
		this.size = size;
		this.map = new HashMap<>();
	}
	
	public boolean isOver() {
		return this.contains(k -> k.y() == 0);
	}
	
	public void move() {
		map.put(this.free = map.isEmpty() ? Coord.randomCoord(size) : getNext(), State.FREE);
	}
	
	public Map<Coord, State> getMap() {
		return this.map;
	}
	
	private Coord getNext() {
		map.remove(this.free);
		return Optional.of(free).map(Coord::moveDown)
				.filter(f -> f.y() == size || isOnTop(f))
				.map(Coord::moveUp).map(peek(k -> map.put(k, State.DEPOSITED)))
				.map(s -> Coord.randomCoord(size))
			.orElse(Coord.moveDown(this.free));
	}
	
	public boolean isOnTop(Coord c) {
		return this.contains(l -> l.equals(c));
	}
	
	private boolean contains(Predicate<Coord> pre) {
		return map.entrySet().stream().filter(e -> e.getValue() == State.DEPOSITED).map(Map.Entry::getKey).anyMatch(pre);
	}
	
	private <T> UnaryOperator<T> peek(Consumer<T> action) {
		return x -> {
			action.accept(x);
			return x;
		};
	}	 
}
