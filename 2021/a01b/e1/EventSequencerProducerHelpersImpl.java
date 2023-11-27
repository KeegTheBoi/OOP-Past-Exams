package a01b.e1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventSequencerProducerHelpersImpl implements EventSequenceProducerHelpers {

    @Override
    public <E> EventSequenceProducer<E> fromIterator(Iterator<Pair<Double, E>> iterator) {
        return new EventSequenceProducer<E>() {

            @Override
            public Pair<Double, E> getNext() throws NoSuchElementException {
                return iterator.next();                  
            }            
        };
    }

    private <E> List<Pair<Double, E>> eventToStream(EventSequenceProducer<E> sequence){
        List<Pair<Double, E>> listOuter = new ArrayList<>();
        var iterEvents = Stream.generate(() -> sequence.getNext()).iterator();
        try {
            while(iterEvents.hasNext()){
            listOuter.add(iterEvents.next());
        }
        } catch (NoSuchElementException e) {
            return Collections.unmodifiableList(listOuter);
        }
        return Collections.unmodifiableList(listOuter);
        

    }

    @Override
    public <E> List<E> window(EventSequenceProducer<E> sequence, double fromTime, double toTime) {
        return eventToStream(sequence).stream().
            dropWhile(c -> c.get1() < fromTime).takeWhile(r -> r.get1() < toTime).map(s -> s.get2()).
            collect(Collectors.toList());
    }

    @Override
    public <E> Iterable<E> asEventContentIterable(EventSequenceProducer<E> sequence) {
        return new Iterable<E>() {

            @Override
            public Iterator<E> iterator() {
                return eventToStream(sequence).stream().map(v -> v.get2()).iterator();
            }
            
        };
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
