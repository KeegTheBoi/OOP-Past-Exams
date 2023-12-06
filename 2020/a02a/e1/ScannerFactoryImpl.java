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
        return new Scanner<X,List<Y>>() {

            @Override
            public List<Y> scan(Iterator<X> input) {
                return toStream(input).filter(filter).map(mapper).collect(Collectors.toList());
            }
            
        };
    }

    private <X> Stream<X> toStream(Iterator<X> input) {
        return input.hasNext() ? Stream.iterate(input.next(), c -> c != null, l -> input.hasNext() ? input.next() : null) : Stream.empty();
    }

    @Override
    public <X> Scanner<X, Optional<X>> findFirst(Predicate<X> filter) {
        return new Scanner<X,Optional<X>>() {

            @Override
            public Optional<X> scan(Iterator<X> input) {
                return toStream(input).filter(filter).findFirst();
            }
        };
    }

    @Override
    public Scanner<Integer, Optional<Integer>> maximalPrefix() {
        return new Scanner<Integer,Optional<Integer>>() {

            @Override
            public Optional<Integer> scan(Iterator<Integer> input) {            
                var prefixVals = toStream(input).collect(Collectors.toList());
                if(prefixVals.isEmpty()) {
                    return Optional.empty();
                }
                Integer max = prefixVals.get(prefixVals.size()-1);
                for (int i = 0; i < prefixVals.size() - 1; i++) {
                    if(prefixVals.get(i) > prefixVals.get(i + 1)) {
                        max = prefixVals.get(i);
                        break;
                    }
                }
                final Integer maxFinal = max;
                return prefixVals.stream().filter(c -> c == maxFinal).findFirst();
            }            
        };
    }

    @Override
    public Scanner<Integer, List<Integer>> cumulativeSum() {
        return new Scanner<Integer,List<Integer>>() {

            @Override
            public List<Integer> scan(Iterator<Integer> input) {
                return toStream(input).collect(ArrayList<Integer>::new, (m, l) -> m.add(m.isEmpty() ? l : m.get(m.size() - 1) + l), ArrayList::addAll);             
            }
            
        };
    }

}
