package a04.e1;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class IntegerIteratorsFactoryImpl implements IntegerIteratorsFactory {

    @Override
    public SimpleIterator<Integer> empty() {
        return Optional::empty;
    }

    @Override
    public SimpleIterator<Integer> fromList(List<Integer> list) {
        return frmoIterator(list.iterator());
    }

    @Override
    public SimpleIterator<Integer> random(int size) {
        return fromList(Stream.generate(Random::new).map(u ->u.nextInt(size)).limit(size).toList());
    }

    @Override
    public SimpleIterator<Integer> quadratic() {
        return infinite(v -> Collections.nCopies(v, v).stream());
    }

    public SimpleIterator<Integer> frmoIterator(Iterator<Integer> iter) {
        return () -> Optional.of(iter).filter(Iterator::hasNext).map(Iterator::next);
    }

    @Override
    public SimpleIterator<Integer> recurring() {
        return infinite(v -> IntStream.rangeClosed(0, v).boxed());
    }

    private SimpleIterator<Integer> infinite(Function<Integer, Stream<Integer>> flatter) {
        return frmoIterator(Stream.iterate(0, i -> i + 1).flatMap(flatter).iterator());
    }

}
