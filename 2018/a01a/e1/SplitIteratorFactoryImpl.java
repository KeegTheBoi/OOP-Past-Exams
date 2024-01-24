package a01a.e1;

import java.util.Spliterator;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SplitIteratorFactoryImpl implements SplitIteratorFactory {

    @Override
    public SplitIterator<Integer> fromRange(int start, int stop) {
        return fromStream(() -> IntStream.rangeClosed(start, stop).boxed(), true);
    }

    private <X> SplitIterator<X> fromStream(Supplier<Stream<X>> supply, boolean split) {
        return new SplitIterator<X>() {
            private int count = 0;
            Iterator<X> iter = supply.get().iterator();
            @Override
            public Optional<X> next() {
                return Optional.of(iter).filter(Iterator::hasNext).map(i -> {count++; return i;}).map(Iterator::next);
            }

            @Override
            public SplitIterator<X> split() {
                var list = supply.get().toList();
                Optional.of(split).filter(t -> t).orElseThrow(UnsupportedOperationException::new);
                this.iter = cutHalf(list, false, count).get().iterator();
                return fromStream(cutHalf(list, true, count), split);
            }
            
        };
    }

    private <X> Supplier<Stream<X>> cutHalf(List<X> list, boolean segment, int init) {
        int half = list.size() / 2;
        return () -> IntStream.range(segment ? init : half, segment ? half : list.size())
            .filter(i -> i < list.size())
            .mapToObj(list::get);
    }

    @Override
    public SplitIterator<Integer> fromRangeNoSplit(int start, int stop) {
        return fromStream(() -> IntStream.rangeClosed(start, stop).boxed(), false);
    }

    @Override
    public <X> SplitIterator<X> fromList(List<X> list) {
        return fromStream(() -> list.stream(), true);
    }

    @Override
    public <X> SplitIterator<X> fromListNoSplit(List<X> list) {
        return fromStream(() -> list.stream(), false);
    }

    @Override
    public <X> SplitIterator<X> excludeFirst(SplitIterator<X> si) {
        List<X> listSplit = Stream.iterate(si, UnaryOperator.identity())
            .map(SplitIterator::next)
            .takeWhile(Optional::isPresent)
            .map(Optional::get)
            .toList();
        return fromList(listSplit.subList(1, listSplit.size()));
    }

    @Override
    public <X, Y> SplitIterator<Y> map(SplitIterator<X> si, Function<X, Y> mapper) {
        List<Y> listMapped = Stream.iterate(si, UnaryOperator.identity())
            .map(SplitIterator::next)
            .takeWhile(Optional::isPresent)
            .map(Optional::get)
            .map(mapper).toList();
        return fromList(listMapped);
    }

}
