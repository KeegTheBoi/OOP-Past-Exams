package a01c.e2;

import java.util.*;
import java.util.stream.*;

public class LogicImpl implements Logic {
	private final int size;
	private final Map<Coord, Integer> map;
	private boolean isOV;
	private Coord origin;
	private Coord end;
	
	
	public LogicImpl(final int size) {
		this.size = size;
		this.map = new HashMap<>();
	}
	
	
	
	public void hit(Coord c) {
		if(map.size() == 0) {
			this.map.put(c, 1);
		} else if(map.size() == 1) {
			this.map.put(c, 2);
		}
		else if(map.size() == 2) {
			this.populate(
				map.entrySet().stream().filter(e -> e.getValue() == 1).map(Map.Entry::getKey).findFirst().get(),
				map.entrySet().stream().filter(e -> e.getValue() == 2).map(Map.Entry::getKey).findFirst().get()
			);
		}
		else {
			this.bulk();
		}	
	}
	
	private void populate(final Coord vertix, final Coord oppo) {
		origin = vertix.y() >= oppo.y() ? oppo : vertix;
		end = oppo.y() >= vertix.y() ? oppo : vertix;
		IntStream.rangeClosed(origin.x(), end.x())
			.boxed()
			.sorted()
			.flatMap(i -> 
				IntStream.rangeClosed(origin.y(), end.y())
				.sorted()
				.mapToObj(f -> new Coord(i, f))
				.filter(p -> !p.equals(origin) && !p.equals(end))
			).forEach(k -> map.put(k, 0));
	}
	
	private void bulk() {
		var defCopy = Map.copyOf(map).keySet();
		this.all().stream()
			.filter(c -> defCopy.stream().anyMatch(k -> isAdjax(c, k)))
			.filter(p -> !p.equals(origin) && !p.equals(end))
			.forEach(u -> map.put(u, 0));
	}
	
	private Set<Coord> all() {
		return IntStream.range(0, size).boxed().flatMap(i -> IntStream.range(0, size).mapToObj(j -> new Coord(i, j))).collect(Collectors.toSet());
	}
	
	private boolean isAdjax(Coord c1, Coord c2) {
		return Math.abs(c1.x() - c2.x()) <= 1 && Math.abs(c1.y() - c2.y()) <= 1;
	}

	public boolean isOver() {
		return map.size() > size * size;
	}
	
	public Map<Coord, Integer> getMap() {
		return Collections.unmodifiableMap(this.map);
	}
}
