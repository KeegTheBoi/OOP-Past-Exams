package a04.e2;

import java.util.*;
import java.util.stream.*;

public class LogicImpl implements Logic{

    private final int size;
    private Coord computer;
	private Coord human;
    private Random rand = new Random();

    public LogicImpl(int size) {
        this.size = size;
        init();
    }
    
    @Override
    public Pair<Coord, Coord> getPlayers() {
		return new Pair<>(human, computer);
	}

    private void init() {
        human = randomPosition();
        computer = Optional.of(randomPosition()).filter(r -> !r.equals(human)).orElse(randomPosition());
        System.out.println(human);
        System.out.println(computer);
    }
    
    private Coord randomPosition() {
		return new Coord(rand.nextInt(this.size), rand.nextInt(this.size)); 
	}

    @Override
    public Optional<Coord> hit(Coord c) {
		return Optional.of(c).filter(this::isPlayerValid).map(this::newPosition);
    }
    
    private Coord newPosition(Coord c) {
		human = c;
		return computer = Optional.of(computer)
			.filter(m -> !this.adjaxSet(m).contains(human) && !c.equals(m))
			.map(k -> this.adjaxSet(k).stream().findAny().get())
			.orElse(human);
	}
    
    private boolean isPlayerValid(Coord c) {
		return c.x() == human.x() || c.y() == human.y();
	}
    
    private Set<Coord> adjaxSet(Coord c) {
		return IntStream.rangeClosed(c.x() - 1, c.x() + 1)
				.boxed()
				.flatMap(i -> 
					IntStream.rangeClosed(c.y() - 1, c.y() + 1)
					.mapToObj(j -> new Coord(i, j))
					.filter(this::inBound)
					.filter(d -> !d.equals(c))
				)
				.collect(Collectors.toSet());
	}
	
	private boolean inBound(Coord c) {
		return c.x() >= 0 && c.y() < size && c.x() < size && c.y() >= 0;
	}

    @Override
    public boolean isOver() {
        return human.equals(computer);
    }

}
