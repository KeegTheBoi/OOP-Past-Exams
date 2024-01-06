package a02a.e1;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class RecursiveIteratorHelpersImpl implements RecursiveIteratorHelpers {

    public <X> RecursiveIterator<X> fromIterator(Iterator<X> iter) {

        return iter.hasNext() ? 
            new RecursiveIterator<X>() {
            private X value = iter.next();

                @Override
                public X getElement() {
                    return value;
                }

                @Override
                public RecursiveIterator<X> next() {
                    return iter.hasNext() ? fromIterator(iter) : null;
                }
                
            } 
        : null;
    }

    @Override
    public <X> RecursiveIterator<X> fromList(List<X> list) {
        return fromIterator(list.iterator());
    }

    @Override
    public <X> List<X> toList(RecursiveIterator<X> input, int max) {
        return Stream.iterate(input, i -> i != null, r -> r.next()).map(RecursiveIterator::getElement).limit(max).collect(Collectors.toList());
    }

    @Override
    public <X, Y> RecursiveIterator<Pair<X, Y>> zip(RecursiveIterator<X> first, RecursiveIterator<Y> second) {
        return fromIterator(
            Stream.iterate(
                new Pair<>(first, second),
                i -> i.getX() != null && i.getY() != null,
                c -> new Pair<>(c.getX().next(), c.getY().next())
            ).map(n -> new Pair<>(n.getX().getElement(), n.getY().getElement()))
            .iterator()
        );
    }

    @Override
    public <X> RecursiveIterator<Pair<X, Integer>> zipWithIndex(RecursiveIterator<X> iterator) {
        return zip(iterator, fromIterator(Stream.iterate(0, i -> i + 1).iterator()));
    }

    private int count = 0;
    @Override
    public <X> RecursiveIterator<X> alternate(RecursiveIterator<X> first, RecursiveIterator<X> second) {
        List<X> firstList = toList(first, 200);
        List<X> secondList = toList(second, 200);
        Iterator<X> firstIterator = firstList.iterator();
        Iterator<X> secondIterator = secondList.iterator();
        return fromIterator(IntStream.range(0, firstList.size() + secondList.size())
        .mapToObj(l -> count++ % 2 == 0 ?
             firstIterator.hasNext() ? firstIterator.next() : secondIterator.next() :
             secondIterator.hasNext() ? secondIterator.next() : firstIterator.next()).iterator());
    }

}
