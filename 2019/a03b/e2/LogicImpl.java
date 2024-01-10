package a03b.e2;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class LogicImpl implements Logic {
	private final int size;
	private final Map<Coord, State> map = new HashMap<>();
	private final Random rand = new Random();
	private Coord free;
	
	public LogicImpl(final int size) {
		this.size = size;
	}
	
	public boolean isOver() {
		return this.contains(k -> k.y() == 0);
	}
	
	public void move() {
		map.put(this.free = this.map.isEmpty() ? Coord.randomCoord(size) : this.getNext(), State.FREE);
	}
	
	public Map<Coord, State> getMap() {
		return this.map;
	}
	
	private Coord getNext() {
		this.map.remove(this.free);
		return Optional.of(this.free).map(Coord::moveDown)
				.filter(f -> !(f.y() == size || this.contains(l -> l.equals(f))))
			.orElseGet(() -> { this.map.put(this.free, State.DEPOSITED); return Coord.randomCoord(this.size);});
	}
	
	private boolean contains(Predicate<Coord> pre) {
		return this.map.entrySet().stream().filter(e -> e.getValue() == State.DEPOSITED).map(Map.Entry::getKey).anyMatch(pre);
	}

}
