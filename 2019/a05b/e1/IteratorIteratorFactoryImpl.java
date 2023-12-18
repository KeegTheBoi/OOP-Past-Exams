package a05b.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IteratorIteratorFactoryImpl implements IteratorIteratorFactory {

    @Override
    public <E> Iterator<Iterator<E>> constantWithSingleton(E e) {
        return Stream.generate(() -> Stream.generate(() -> e).iterator()).iterator();
    }

    @Override
    public <E> Iterator<Iterator<E>> increasingSizeWithSingleton(E e) {
        return Stream.generate(() -> Stream.iterate(new ArrayList<>(List.of(e)), n -> {n.add(e); return n;}).flatMap(List::stream).iterator()).iterator();
    }

    @Override
    public Iterator<Iterator<Integer>> squares() {

        return Stream.iterate(0, i -> i + 1)
            .map(i -> 
                Stream.concat(
                    Stream.iterate(0,n -> ((n * n) + 1) * 2).limit(i),
                    Stream.of(i * i)
                ).iterator()
            ).iterator();
    }

    @Override
    public Iterator<Iterator<Integer>> evens() {
        var t = Stream.generate(() -> 
            Stream.iterate(0, i -> i + 1)
            .flatMap(i -> 
                Stream.iterate(new ArrayList<Integer>(List.of(i)), 
                n -> {n.add(i + 2); return n;}).flatMap(List::stream)
            ).iterator()).iterator();
        return t;
    }

}
