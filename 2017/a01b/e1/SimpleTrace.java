package a01b.e1;


import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class SimpleTrace<X> implements Trace<X>{

    private Stream<Event<X>> list;
    private Iterator<Event<X>> iterator;

    public SimpleTrace(Stream<Event<X>> list) {
        this.list = list;
        iterator = this.list.iterator();
    }

    @Override
    public Optional<Event<X>> nextEvent() {
        return !this.iterator.hasNext() ? Optional.empty() : Optional.of(iterator.next());
    }

    @Override
    public Iterator<Event<X>> iterator() {
        return this.iterator;
    }

    private Stream<Event<X>> consumeElements(Iterator<Event<X>> iter) {
        return Stream.iterate(iter, i -> i.hasNext(), UnaryOperator.identity()).map(f -> f.next());
    }

    @Override
    public void skipAfter(int time) {
        Stream.iterate(this.iterator, i -> i.hasNext(), UnaryOperator.identity()).map(f -> f.next()).takeWhile(s -> s.getTime() < time).count();
    }

    @Override
    public Trace<X> combineWith(Trace<X> trace) {
        return new SimpleTrace<>(Stream.concat(consumeElements(this.iterator), consumeElements(trace.iterator())).sorted(Comparator.comparingInt(Event::getTime)));
    }

    @Override
    public Trace<X> dropValues(X value) {
        return new SimpleTrace<>(consumeElements(iterator).filter(e -> e.getValue() != value));
    }

}
