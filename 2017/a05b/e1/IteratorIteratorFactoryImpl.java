package a05b.e1;

import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class IteratorIteratorFactoryImpl implements IteratorIteratorFactory {

    private <E> Iterator<Iterator<E>> iteratorIncreasing(Function<IntStream, Stream<E>> func, boolean even) {
        return Stream.iterate(1, i -> i + (even ? 2 : 1))
            .map(i -> 
                func.apply(IntStream.range(0, i))
                .iterator()
            ).iterator();
    }

    @Override
    public <E> Iterator<Iterator<E>> constantWithSingleton(E e) {
        return Stream.generate(() -> Stream.of(e).iterator()).iterator();
    }

    @Override
    public <E> Iterator<Iterator<E>> increasingSizeWithSingleton(E e) {
        return iteratorIncreasing(s -> s.mapToObj(o -> e), false);
    }

    @Override
    public Iterator<Iterator<Integer>> squares() {
        return iteratorIncreasing(s -> s.mapToObj(k -> k * k), false);
    }

    @Override
    public Iterator<Iterator<Integer>> evens() {
        return iteratorIncreasing(s -> s.boxed().filter(k -> k % 2 == 0), true);
    }

}
