package a02a.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScannerFactoryImpl implements ScannerFactory {

    @Override
    public <X, Y> Scanner<X, List<Y>> collect(Predicate<X> filter, Function<X, Y> mapper) {
        return iter -> toStream(iter).filter(filter).map(mapper).collect(Collectors.toList());
    }

    private <X> Stream<X> toStream(Iterator<X> input) {
        return Stream.iterate(input, c -> c.hasNext(), UnaryOperator.identity()).map(Iterator::next);
    }

    @Override
    public <X> Scanner<X, Optional<X>> findFirst(Predicate<X> filter) {
        return input -> toStream(input).filter(filter).findFirst();
    }

    @Override
    public Scanner<Integer, Optional<Integer>> maximalPrefix() {
        return input -> {
            var list = toStream(input).collect(Collectors.toList());
            IntStream.range(0, list.size()).dropWhile(i-> list.toStream().limit(i).allMatch(y-> y <= list.get(i))).map(List::get).findFirst();
        }
    }

    @Override
    public Scanner<Integer, List<Integer>> cumulativeSum() {
        return input -> toStream(input).collect(ArrayList<Integer>::new, (m, n) -> m.add(m.isEmpty() ? n : m.get(m.size() - 1) + n), ArrayList::addAll);             
    }

}
