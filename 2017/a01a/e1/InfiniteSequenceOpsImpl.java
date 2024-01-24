package a01a.e1;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class InfiniteSequenceOpsImpl implements InfiniteSequenceOps {

    public <X> InfiniteSequence<X> fromIteratetor(Iterator<X> iter) {
        return () -> iter.next();       
    }

    @Override
    public <X> InfiniteSequence<X> ofValue(X x) {
        return fromIteratetor(Stream.generate(() -> x).iterator());
    }

    @Override
    public <X> InfiniteSequence<X> ofValues(List<X> l) {
        return fromIteratetor(Stream.iterate(0, i -> i + 1).flatMap(k -> l.stream()).iterator());
    }

    @Override
    public InfiniteSequence<Double> averageOnInterval(InfiniteSequence<Double> iseq, int intervalSize) {
        return infiniteTrasformer(l -> toStream(iseq).limit(intervalSize).mapToDouble(Double::doubleValue).average().getAsDouble());
    }

    @Override
    public <X> InfiniteSequence<X> oneEachInterval(InfiniteSequence<X> iseq, int intervalSize) {
        return infiniteTrasformer(l -> toStream(iseq).skip(intervalSize - 1).findFirst().get());
    }

    @Override
    public <X> InfiniteSequence<Boolean> equalsTwoByTwo(InfiniteSequence<X> iseq) {
        return equalsOnEachElement(iseq, iseq);
    }

    @Override
    public <X, Y extends X> InfiniteSequence<Boolean> equalsOnEachElement(InfiniteSequence<X> isx,
            InfiniteSequence<Y> isy) {
        return infiniteTrasformer(l -> toIterator(isx).next() == toIterator(isy).next());
    }

    @Override
    public <X> Iterator<X> toIterator(InfiniteSequence<X> iseq) {
        return toStream(iseq).iterator();
    }

    private <X> Stream<X> toStream(InfiniteSequence<X> iseq) {
        return Stream.generate(iseq::nextElement);
    }

    private <X> InfiniteSequence<X> infiniteTrasformer(Function<Integer, X> mapper) {
        return fromIteratetor(Stream.iterate(0, i -> i + 1).map(mapper).iterator());
    }




}
