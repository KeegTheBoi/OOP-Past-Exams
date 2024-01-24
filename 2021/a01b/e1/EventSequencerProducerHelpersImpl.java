package a01b.e1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventSequencerProducerHelpersImpl implements EventSequenceProducerHelpers {

    @Override
    public <E> EventSequenceProducer<E> fromIterator(Iterator<Pair<Double, E>> iterator) {
        return iterator::next;                  
    }

    peivate <E> Optional<E> extract(EventSequenceProducer<E> seq) {
        try {
            return Optional.of(seq.getNext());
        } catch (NoSuchElementException e) {
            return Optional.empty;
        }

    }

    private <E> Stream<Pair<Double, E>> eventToStream(EventSequenceProducer<E> sequence){
        return Stream.generate(() -> extract(sequence)).takeWhile(Optional::isPresent).map(Optional::get);
    }

    @Override
    public <E> List<E> window(EventSequenceProducer<E> sequence, double fromTime, double toTime) {
        return eventToStream(sequence)
            .dropWhile(c -> c.get1() < fromTime)
            .takeWhile(r -> r.get1() < toTime)
            .map(s -> s.get2())
            .collect(Collectors.toList());
    }

    @Override
    public <E> Iterable<E> asEventContentIterable(EventSequenceProducer<E> sequence) {
        return ()-> eventToStream(sequence).stream().map(v -> v.get2()).iterator();
    }

    @Override
    public <E> Optional<Pair<Double, E>> nextAt(EventSequenceProducer<E> sequence, double time) {
        return eventToStream(sequence).stream().dropWhile(c -> c.get1() < time).findFirst();
    }

    @Override
    public <E> EventSequenceProducer<E> filter(EventSequenceProducer<E> sequence, Predicate<E> predicate) {
        return fromIterator(eventToStream(sequence).stream().filter(x -> predicate.test(x.get2())).iterator());
    }
    
}
