package a02c.e1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListSplitterFactoryImpl implements ListSplitterFactory {

    private static class Splitter<X> implements ListSplitter<X> {

        private int delta;
        private BiPredicate<List<X>, Integer> bipred;

        public Splitter(final BiPredicate<List<X>, Integer> bipred) {
            this.bipred = bipred;
        }
        
        @Override
        public List<List<X>> split (List<X> list) {
            Predicate<Intger> pred = i -> bipred.test(list, i);
            return Stream.iterate(0, d -> d < list.size(), i -> i + delta)
                .map (k -> 
                    Optional.of(sequence(k, list, pred))
                    .filter(h-> !h.isEmpty())
                    .orElse(sequence(k, list, pred.negate()))
                )
                .peek(t -> delta = t.size())
                .collect(Collectors.toList());
        }

        private List<X> sequence(final int offset, final List<X> list) {
            return IntStream.range(0, list.size()).boxed().skip(offset).takeWhile(pred).map(list::get).collect(Collectors.toList());
        }

        private static ListSplitter<X> splitFixed(final int dividend) {
            return list -> IntStream.range(0, list.size() / dividend)
                .mapToObj(y -> list.stream().skip(dividend * y).limit(dividend).collect(Collectors.toList()))
                .collect(Collectors.toList());
        }
        
    
    }


    @Override
    public <X> ListSplitter<X> asPairs() {
        return Splitter.splitFixed(2);
    }

    @Override
    public <X> ListSplitter<X> asTriplets() {
        return Splitter.splitFixed(3);
    }

    @Override
    public <X> ListSplitter<X> asTripletsWithRest() {
        return new Splitter<>((l, i) -> i / 3 % 2 == 0);
    }

    @Override
    public <X> ListSplitter<X> bySeparator(X separator) {
        return byPredicate(c -> c.equals(separator));
    }

    @Override
    public <X> ListSplitter<X> byPredicate(Predicate<X> predicate) {
        return new Splitter<>((l, i)-> predicate.test(l.get(i)));
    }
    
}
